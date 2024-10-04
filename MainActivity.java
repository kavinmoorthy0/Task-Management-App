package com.example.taskmanagerapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TaskDatabaseHelper taskDatabaseHelper;
    private ListView listView;
    private ArrayAdapter<String> taskAdapter;
    private List<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText taskEditText = findViewById(R.id.etTaskName);
        Spinner prioritySpinner = findViewById(R.id.spPriority);
        Button addTaskButton = findViewById(R.id.btnAddTask);
        listView = findViewById(R.id.lvTasks);

        taskDatabaseHelper = new TaskDatabaseHelper(this);
        taskList = new ArrayList<>();

        // Set up the priority spinner
        String[] priorities = {"Low", "Medium", "High"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, priorities);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySpinner.setAdapter(spinnerAdapter);

        // Set up the ListView adapter
        taskAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        listView.setAdapter(taskAdapter);

        // Load tasks into ListView
        loadTasks();

        // Add task on button click
        addTaskButton.setOnClickListener(v -> {
            String taskText = taskEditText.getText().toString();
            String priority = prioritySpinner.getSelectedItem().toString();

            if (!taskText.isEmpty()) {
                taskDatabaseHelper.addTask(taskText, priority);
                loadTasks();
                taskEditText.getText().clear();
                Toast.makeText(MainActivity.this, "Task added", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Please enter a task", Toast.LENGTH_SHORT).show();
            }
        });

        // Delete task on long click
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            Task taskToDelete = taskList.get(position);
            taskDatabaseHelper.deleteTask(taskToDelete.getId());
            loadTasks();
            Toast.makeText(MainActivity.this, "Task deleted", Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void loadTasks() {
        taskList = taskDatabaseHelper.getAllTasks();
        List<String> taskDisplayList = new ArrayList<>();
        for (Task task : taskList) {
            taskDisplayList.add(task.getTask() + " (" + task.getPriority() + ")");
        }
        taskAdapter.clear();
        taskAdapter.addAll(taskDisplayList);
        taskAdapter.notifyDataSetChanged();
    }
}
