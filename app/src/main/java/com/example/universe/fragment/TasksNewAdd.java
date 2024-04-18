package com.example.universe.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.universe.R;
import com.example.universe.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TasksNewAdd extends BottomSheetDialogFragment {

    public static final String TAG = "TasksNewAdd";
    private TextView setDueDate;
    private EditText editTask;
    private Button saveButton;
    private String dueDate;
    private String id;
    private String dueDateUpdate;



    private FirebaseFirestore firestore;

    private Context context;

    public static TasksNewAdd newInstance(){

        return new TasksNewAdd();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tasks_new_add,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setDueDate = view.findViewById(R.id.setDueDate_textView);
        editTask = view.findViewById(R.id.tasks_editText);
        saveButton = view.findViewById(R.id.save_button);

        //databases
        firestore = FirebaseFirestore.getInstance();

        //create
        boolean isUpdating = false;

        final Bundle bundle = getArguments();
        if(bundle != null){
            isUpdating = true;
            String task = bundle.getString("task");
            id = bundle.getString("id");
            dueDateUpdate = bundle.getString("due");

            editTask.setText(task);
            setDueDate.setText(dueDateUpdate);

            //disable text if not edited
            if (task.length() > 0){
                saveButton.setEnabled(false);
                saveButton.setBackgroundColor(Color.GRAY);
            }
        }

        editTask.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            //checks for changed text
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("")) {
                    saveButton.setEnabled(false);
                    saveButton.setBackgroundColor(Color.GRAY);

                } else {
                    saveButton.setEnabled(true);
                    saveButton.setBackgroundColor(Color.GREEN);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        setDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int date = calendar.get(Calendar.DATE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker datePicker, int yr, int mth, int day) {
                        mth = month + 1;
                        setDueDate.setText(day + "/" + mth + "/" + yr);
                        dueDate = day + "/" + mth + "/" + yr;
                    }
                }, year, month, date);

                //show the day
                datePickerDialog.show();
            }
        });

        boolean finalIsUpdating = isUpdating;
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String task = editTask.getText().toString();

                if (finalIsUpdating) {
                    firestore.collection("taskList").document(id).update("task", task, "due", dueDate);
                    Toast.makeText(context, "Task Updated in Firebase", Toast.LENGTH_SHORT).show();
                } else {
                    //task first time
                    if (task.isEmpty()) {
                        saveButton.setBackgroundColor(Color.GRAY);
                        Toast.makeText(requireActivity(), "Save Failed! Field cannot be empty.", Toast.LENGTH_SHORT).show();

                    } else {
                        Map<String, Object> taskMap = new HashMap<>();

                        saveButton.setBackgroundColor(Color.GREEN);

                        taskMap.put("task", task);
                        taskMap.put("due", dueDate);
                        taskMap.put("status", 0);
                        taskMap.put("timestamp", FieldValue.serverTimestamp());

                        firestore.collection("taskList").add(taskMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(context, "Save Success in Firebase!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                dismiss();
            }

        });
    }
    //context attached needed?
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    //handle dismiss
    public void onDismiss(@NonNull DialogInterface dialogInterface){
        super.onDismiss(dialogInterface);
        Activity activity = getActivity();
        if(activity instanceof OnDialogCloseListener) {
            ((OnDialogCloseListener)activity).onDialogClose(dialogInterface);
        }
    }

}
