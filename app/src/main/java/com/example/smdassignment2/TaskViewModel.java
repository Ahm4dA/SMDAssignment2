package com.example.smdassignment2;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class TaskViewModel extends ViewModel {
    private final ArrayList<String> taskNames = new ArrayList<>();
    private final ArrayList<String> taskDescriptions = new ArrayList<>();

    public ArrayList<String> getTaskNames() {
        return taskNames;
    }

    public ArrayList<String> getTaskDescriptions() {
        return taskDescriptions;
    }

    public void addTask(String taskName, String taskDescription) {
        taskNames.add(taskName);
        taskDescriptions.add(taskDescription);
    }
}
