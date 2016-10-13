package edu.orangecoastcollege.cs273.dpham147.todo2day;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ListView;

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

    }
}
