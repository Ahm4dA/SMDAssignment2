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
import android.widget.TextView;


public class TaskDetailsFragment extends Fragment {

    private static final String ARG_TASK_NAME = "task_name";
    private static final String ARG_TASK_DESCRIPTION = "task_description";

    private String taskName;
    private String taskDescription;

    public static TaskDetailsFragment newInstance(String taskName, String taskDescription) {
        TaskDetailsFragment fragment = new TaskDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TASK_NAME, taskName);
        args.putString(ARG_TASK_DESCRIPTION, taskDescription);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_details, container, false);

        TextView taskNameTextView = view.findViewById(R.id.taskNameDetails);
        TextView taskDescriptionTextView = view.findViewById(R.id.taskDescriptionDetails);

        if (getArguments() != null) {
            taskName = getArguments().getString(ARG_TASK_NAME);
            taskDescription = getArguments().getString(ARG_TASK_DESCRIPTION);
        }

        // Set the task details in TextViews
        taskNameTextView.setText(taskName);
        taskDescriptionTextView.setText(taskDescription);

        return view;
    }
}