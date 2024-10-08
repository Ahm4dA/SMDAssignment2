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
    private ArrayList<String> taskNames;      // Store task names
    private ArrayList<String> taskDescriptions; // Store task descriptions
    private boolean isTwoPane;  // To check if we are in landscape mode

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize lists
        taskNames = new ArrayList<>();
        taskDescriptions = new ArrayList<>();

        // Restore saved state if available
        if (savedInstanceState != null) {
            taskNames = savedInstanceState.getStringArrayList("taskNames");
            taskDescriptions = savedInstanceState.getStringArrayList("taskDescriptions");
        }

        isTwoPane = findViewById(R.id.detailsFragmentContainer) != null;

        taskListFragment = new TaskListFragment();
        taskListFragment.setTaskListFragmentListener(this);

        // Pass the lists to the fragment
        taskListFragment.setTaskNames(taskNames); // Use the setter to pass task names
        taskListFragment.setTaskDescriptions(taskDescriptions); // Use the setter to pass task descriptions

        // In portrait mode, show only the task list fragment
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

    // Handle task selection from TaskListFragment
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
                    .addToBackStack(null)  // Add to back stack for navigation
                    .commit();
        }
    }

    // Add task to the list from TaskInputFragment
    public void addTask(String taskName, String taskDescription) {
        // Check if the task already exists before adding it to avoid duplicates
        if (!taskNames.contains(taskName)) {
            if (taskListFragment != null) {
                taskListFragment.addTask(taskName, taskDescription); // Only add to TaskListFragment
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