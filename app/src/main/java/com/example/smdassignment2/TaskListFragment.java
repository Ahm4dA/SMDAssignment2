package com.example.smdassignment2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TaskListFragment extends Fragment {

    private ArrayList<String> taskNames = new ArrayList<>();
    private ArrayList<String> taskDescriptions = new ArrayList<>();
    private ArrayAdapter<String> taskAdapter;
    private ListView taskListView;

    private TaskListFragmentListener listener;

    public interface TaskListFragmentListener {
        void onTaskSelected(String taskName, String taskDescription);
    }

    public void setTaskListFragmentListener(TaskListFragmentListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        taskListView = view.findViewById(R.id.taskList);

        taskAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, taskNames);
        taskListView.setAdapter(taskAdapter);

        taskListView.setOnItemClickListener((parent, v, position, id) -> {
            String taskName = taskNames.get(position);
            String taskDescription = taskDescriptions.get(position);

            if (listener != null) {
                listener.onTaskSelected(taskName, taskDescription);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Update the UI if needed
        taskAdapter.notifyDataSetChanged();
    }

    public void setTaskNames(ArrayList<String> taskNames) {
        this.taskNames = taskNames;
        if (taskAdapter != null) {
            taskAdapter.notifyDataSetChanged();
        }
    }

    public void setTaskDescriptions(ArrayList<String> taskDescriptions) {
        this.taskDescriptions = taskDescriptions;
    }

    public void addTask(String taskName, String taskDescription) {
        taskNames.add(taskName);
        taskDescriptions.add(taskDescription);
        if (taskAdapter != null) {
            taskAdapter.notifyDataSetChanged();
        }
    }
}