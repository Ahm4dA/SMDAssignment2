package com.example.smdassignment2;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TaskListFragment.TaskListFragmentListener {

    private TaskListFragment taskListFragment;
    private ArrayList<String> taskNames;
    private ArrayList<String> taskDescriptions;
    private boolean isTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskNames = new ArrayList<>();
        taskDescriptions = new ArrayList<>();

        if (savedInstanceState != null) {
            taskNames = savedInstanceState.getStringArrayList("taskNames");
            taskDescriptions = savedInstanceState.getStringArrayList("taskDescriptions");
        }

        isTwoPane = findViewById(R.id.detailsFragmentContainer) != null;

        taskListFragment = new TaskListFragment();
        taskListFragment.setTaskListFragmentListener(this);


        taskListFragment.setTaskNames(taskNames);
        taskListFragment.setTaskDescriptions(taskDescriptions);

        if (!isTwoPane) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, taskListFragment);
            fragmentTransaction.commit();
        } else {
            showTaskListFragment();
            showEmptyTaskDetailsFragment();
        }

        FloatingActionButton addTaskButton = findViewById(R.id.addTaskButton);
        addTaskButton.setOnClickListener(v -> {
            TaskInputFragment taskInputFragment = new TaskInputFragment();
            taskInputFragment.show(getSupportFragmentManager(), "taskInput");
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the task names and descriptions
        outState.putStringArrayList("taskNames", taskNames);
        outState.putStringArrayList("taskDescriptions", taskDescriptions);
    }


    @Override
    public void onTaskSelected(String taskName, String taskDescription) {
        TaskDetailsFragment taskDetailsFragment = TaskDetailsFragment.newInstance(taskName, taskDescription);

        if (isTwoPane) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detailsFragmentContainer, taskDetailsFragment)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, taskDetailsFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }


    public void addTask(String taskName, String taskDescription) {
        if (!taskNames.contains(taskName)) {
            if (taskListFragment != null) {
                taskListFragment.addTask(taskName, taskDescription);
            }
        }
    }

    private void showTaskListFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.listFragmentContainer, taskListFragment)
                .commit();
    }

    private void showEmptyTaskDetailsFragment() {
        TaskDetailsFragment taskDetailsFragment = TaskDetailsFragment.newInstance("", "Select a task to view details");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.detailsFragmentContainer, taskDetailsFragment)
                .commit();
    }
}