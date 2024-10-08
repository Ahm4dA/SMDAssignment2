package com.example.smdassignment2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;


public class TaskInputFragment extends DialogFragment {

    private EditText taskNameInput;
    private EditText taskDescriptionInput;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_input, container, false);

        taskNameInput = view.findViewById(R.id.taskNameInput);
        taskDescriptionInput = view.findViewById(R.id.taskDescriptionInput);
        Button saveButton = view.findViewById(R.id.saveButton);

        saveButton.setOnClickListener(view1 -> {
            String taskName = taskNameInput.getText().toString();
            String taskDescription = taskDescriptionInput.getText().toString();

            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).addTask(taskName, taskDescription);
            }

            dismiss();
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(params);
        }
    }
}