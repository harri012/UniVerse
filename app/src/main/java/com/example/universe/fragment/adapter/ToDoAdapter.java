package com.example.universe.fragment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.universe.R;
import com.example.universe.fragment.TasksNewAdd;
import com.example.universe.fragment.model.ToDoList;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.TaskViewHolder> {

    private List<ToDoList> toDoList;
    private FragmentActivity taskFragment;
    private FirebaseFirestore firestore;

    public ToDoAdapter(FragmentActivity fragmentActivity, List<ToDoList> toDoLists){
        this.toDoList = toDoLists;
        taskFragment = fragmentActivity;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //infalte view
        View view = LayoutInflater.from(taskFragment).inflate(R.layout.tasks_individual, parent, false);

        //setup firestore
        firestore = FirebaseFirestore.getInstance();

        return new TaskViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {

        ToDoList toDo = toDoList.get(position);
        holder.checkBox.setText(toDo.getTask());
        holder.dueDate.setText("Due Date: " + toDo.getDue());

        holder.checkBox.setChecked(toBool(toDo.getStatus()));

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    firestore.collection("taskList").document(toDo.taskID).update("status", 1);
                } else {
                    firestore.collection("taskList").document(toDo.taskID).update("status", 0);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return toDoList.size();
    }

    //retrieve the information format where we have checkbox, textview and edit text where adapter decides format view
    public class TaskViewHolder extends RecyclerView.ViewHolder{

        TextView dueDate;
        CheckBox checkBox;


        public TaskViewHolder(@NonNull View view){
            super(view);

            dueDate = itemView.findViewById(R.id.task_deadline_textView);
            checkBox = itemView.findViewById(R.id.task_checkBox);
        }

    }

    //convert to bool and true or false
    private boolean toBool (int status){
        return status != 0;
    }

    public void deleteTask(int position){
        ToDoList toDo = toDoList.get(position);
        firestore.collection("taskList").document(toDo.taskID).delete();
        toDoList.remove(position);
        notifyItemRemoved(position);
    }

    public void editTask(int position){
        ToDoList toDo = toDoList.get(position);

        Bundle bundle = new Bundle();
        bundle.putString("task", toDo.getTask());
        bundle.putString("due", toDo.getDue());
        bundle.putString("id", toDo.taskID);

        TasksNewAdd addNewTask = new TasksNewAdd();
        addNewTask.setArguments(bundle);
        addNewTask.show(taskFragment.getSupportFragmentManager(), addNewTask.getTag());

    }

    public Context getContext(){
        return taskFragment;
    }

}
