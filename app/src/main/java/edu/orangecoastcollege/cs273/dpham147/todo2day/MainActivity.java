package edu.orangecoastcollege.cs273.dpham147.todo2day;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // FOR NOW - Delete old DB, then create new
        this.deleteDatabase(DBHelper.DATABASE_TABLE);
        // Create the DBHelper reference
        DBHelper db = new DBHelper(this);

        // Let's make a new task and add to DB
        db.addTask(new Task(1, "Study for CS273 Midterm", 0));
        db.addTask(new Task(2, "Play FGO", 0));
        db.addTask(new Task(3, "Do Homework", 0));
        db.addTask(new Task(4, "Make Dinner", 0));
        db.addTask(new Task(5, "Do stuff", 0));

        // Let's get all tasks from DB and print with Log.i()
        ArrayList<Task> allTask = db.getAllTasks();
        // Loop through each task, print to Log.i();
        for (Task singleTask : allTask) {
            Log.i("DATABASE TASK", singleTask.toString());
        }
    }
}
