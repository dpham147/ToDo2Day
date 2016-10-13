package edu.orangecoastcollege.cs273.dpham147.todo2day;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DBHelper database;
    private List<Task> taskList;
    private TaskListAdapter taskListAdapter;

    private EditText taskEditText;
    private ListView taskListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the DBHelper reference
        database = new DBHelper(this);

        // Fill list with task from DB
        taskList = database.getAllTasks();

        // Create custom task list adapter
        // (We want to associate the adapter with the context, layout, and the List
        taskListAdapter = new TaskListAdapter(this, R.layout.task_item, taskList);

        // Connect the listview to the layout
        taskListView = (ListView) findViewById(R.id.taskListView);

        // Associate the adapter with the ListView
        taskListView.setAdapter(taskListAdapter);

        // Connect EditText with layout
        taskEditText = (EditText) findViewById(R.id.taskEditText);

    }

    protected void addTask(View view) {
        String description = taskEditText.getText().toString();
        if (description.isEmpty()) {
            Toast.makeText(this, "Description is blank.", Toast.LENGTH_SHORT).show();
        }
        else {
            // Make a new task
            Task newTask = new Task(description, 0);
            // Add it to the list adapter
            taskListAdapter.add(newTask);
            // Add it to the DB
            database.addTask(newTask);
            // Clear the EditText
            taskEditText.setText("");
        }
    }

    protected void changeTaskStatus(View view) {
        if (view instanceof CheckBox) {
            CheckBox selectedCheck = (CheckBox) view;
            Task selectedTask = (Task) selectedCheck.getTag();
            selectedTask.setIsDone(selectedCheck.isChecked() ? 1 : 0);
            // Update it in the DB
            database.updateTask(selectedTask);
        }
    }

    protected void clearAllTasks(View view) {
        // Clear the list
        taskList.clear();
        // Clear the records in the DB
        database.deleteAllTasks();
        // Update TaskListAdapter
        taskListAdapter.notifyDataSetChanged();
    }
}
