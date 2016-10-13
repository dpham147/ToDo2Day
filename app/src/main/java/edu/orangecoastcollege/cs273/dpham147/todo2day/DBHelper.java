package edu.orangecoastcollege.cs273.dpham147.todo2day;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

class DBHelper extends SQLiteOpenHelper {

    //TASK 1: DEFINE THE DATABASE VERSION, NAME AND TABLE NAME
    private static final String DATABASE_NAME = "ToDo2Day";
    static final String DATABASE_TABLE = "Tasks";
    private static final int DATABASE_VERSION = 1;


    //TASK 2: DEFINE THE FIELDS (COLUMN NAMES) FOR THE TABLE
    private static final String KEY_FIELD_ID = "id";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_IS_DONE = "is_done";


    public DBHelper (Context context){
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase database){
        String table = "CREATE TABLE " + DATABASE_TABLE + "("
                + KEY_FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FIELD_DESCRIPTION + " TEXT, "
                + FIELD_IS_DONE + " INTEGER" + ")";
        database.execSQL (table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database,
                          int oldVersion,
                          int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(database);
    }

    // Create a method to add a brand new task to DB
    public void addTask(Task newTask) {
        // Step 1) Create a reference to the DB
        SQLiteDatabase db = this.getWritableDatabase();

        // Step 2) Make a key-value pair for each value you want to insert
        ContentValues values = new ContentValues();
        // values.put(KEY_FIELD_ID, newTask.getId());
        values.put(FIELD_DESCRIPTION, newTask.getDescription());
        values.put(FIELD_IS_DONE, newTask.getIsDone());

        // Step 3) Insert values into db
        db.insert(DATABASE_TABLE, null, values);

        // Step 4) Close the DB
        db.close();
    }

    // Create a method to get all the tasks
    public ArrayList<Task> getAllTasks() {
        // Step 1) Create reference to DB
        SQLiteDatabase db = this.getReadableDatabase();

        // Step 2) Create new empty ArrayList
        ArrayList<Task> allTask = new ArrayList<>();

        // Step 3) Query the DB for all records (all rows) and all fields (all col)
        // The return type of a query is a Cursor
        Cursor results = db.query(DATABASE_TABLE, null, null, null, null, null, null);

        // Step 4) Loop through results and create Task objects to add to ArrayList
        if (results.moveToFirst()) {
            do {
                int id = results.getInt(0);
                String description = results.getString(1);
                int isDone = results.getInt(2);

                allTask.add(new Task(id, description, isDone));
            } while (results.moveToNext());
        }

        db.close();
        return allTask;
    }

    public void updateTask(Task existingTask) {
        // Step 1) Create a reference to the DB
        SQLiteDatabase db = this.getWritableDatabase();

        // Step 2) Make a key-value pair for each value you want to insert
        ContentValues values = new ContentValues();
        // values.put(KEY_FIELD_ID, newTask.getId());
        values.put(FIELD_DESCRIPTION, existingTask.getDescription());
        values.put(FIELD_IS_DONE, existingTask.getIsDone());

        // Step 3) Insert values into db
        db.update(DATABASE_TABLE,
                values,
                KEY_FIELD_ID + "=?",
                new String[] {String.valueOf(existingTask.getId())});

        // Step 4) Close the DB
        db.close();
    }

    public void deleteAllTasks() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE, null, null);
        db.close();
    }
}
