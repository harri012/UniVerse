//adapted from https://github.com/Sanath14/to-do/tree/master
package com.example.universe.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.universe.R;
import com.example.universe.fragment.adapter.ToDoAdapter;
import com.example.universe.fragment.model.ToDoList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TasksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TasksFragment extends Fragment implements OnDialogCloseListener {

    private RecyclerView recyclerView;
    private FloatingActionButton addTasksButton;

    private FirebaseFirestore firestore;
    private ToDoAdapter adapter;
    private List<ToDoList> list;

    private ListenerRegistration listenerRegistration;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TasksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CallFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TasksFragment newInstance(String param1, String param2) {
        TasksFragment fragment = new TasksFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_tasks, container, false);

        //create the views
        recyclerView = fragmentView.findViewById(R.id.task_recycler_view);

        recyclerView.setHasFixedSize(true);

        //retrieve layout of current fragment
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //create button
        addTasksButton = fragmentView.findViewById(R.id.fab_addTasks);

        addTasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TasksNewAdd.newInstance().show(getChildFragmentManager(), TasksNewAdd.TAG);
            }
        });

        //initialize firestore
        firestore = FirebaseFirestore.getInstance();

        //adapter create
        list = new ArrayList<>();
        adapter = new ToDoAdapter(requireActivity(), list);

        recyclerView.setAdapter(adapter);

        //load data
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new TouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        showData();

        return fragmentView;
    }

    private void showData(){

        //show in order
        Query query = firestore.collection("taskList").orderBy("due" , Query.Direction.ASCENDING);

        //listener registration for not double
        listenerRegistration = query.addSnapshotListener(new EventListener<QuerySnapshot>(){
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentChange documentChange : value.getDocumentChanges()){
                    if (documentChange.getType() == DocumentChange.Type.ADDED){
                        String id = documentChange.getDocument().getId();
                        ToDoList toDoModel = documentChange.getDocument().toObject(ToDoList.class).withId(id);
                        list.add(toDoModel);
                    }
                }
                //for not double
                adapter.notifyDataSetChanged();
                listenerRegistration.remove();
            }
        });

    }
    @Override
    public void onDialogClose(DialogInterface dialogInterface){
        //for showing everytime
        list.clear();
        showData();
    }
}