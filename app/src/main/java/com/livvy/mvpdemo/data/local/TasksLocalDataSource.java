package com.livvy.mvpdemo.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.livvy.mvpdemo.data.Task;
import com.livvy.mvpdemo.data.TasksDataSource;
import com.livvy.mvpdemo.data.local.TasksPersistenceContract.TaskEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Finch on 2016/10/9 0009.
 */
public class TasksLocalDataSource implements TasksDataSource
{
    private static TasksLocalDataSource INSTANCE;

    private TaskDBhelper mDbHelper;

    private TasksLocalDataSource(Context context)
    {
        mDbHelper = new TaskDBhelper(context);
    }

    public static TasksLocalDataSource getInstance(
            @NonNull
            Context context)
    {
        if (INSTANCE == null)
        {
            INSTANCE = new TasksLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getTasks(
            @NonNull
            LoadTasksCallback callback)
    {
        List<Task> tasks = new ArrayList<Task>();

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                TaskEntry.COLUMN_NAME_TITLE, TaskEntry.COLUMN_NAME_DESCRIPTION};
        Cursor c = db.query(TaskEntry.TABLE_NAME, projection, null, null, null, null, null);
        if (c != null && c.getCount() > 0)
        {
            while (c.moveToNext())
            {
                //String itemId = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_ENTRY_ID));
                String title = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_TITLE));
                String description = c.getString(c.getColumnIndexOrThrow(TaskEntry.COLUMN_NAME_DESCRIPTION));
                Task task = new Task(title, description);
                tasks.add(task);
            }

            if (c != null)
            {
                c.close();
            }
            db.close();
            if (tasks.isEmpty())
            {
                // This will be called if the table is new or just empty.
                callback.onDataNotAvailable();
            }
            else
            {
                callback.onTasksLoaded(tasks);
            }
        }
    }

    @Override
    public void getTask(
            @NonNull
            String taskId,
            @NonNull
            GetTaskCallback callback)
    {

    }

    @Override
    public void saveTask(
            @NonNull
            Task task)
    {
        if (task == null)
        {
            return;
        }
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(TaskEntry.COLUMN_NAME_ENTRY_ID, task.mId);
        values.put(TaskEntry.COLUMN_NAME_TITLE, task.title);
        values.put(TaskEntry.COLUMN_NAME_DESCRIPTION, task.description);

        db.insert(TaskEntry.TABLE_NAME, null, values);
        db.close();
    }

    @Override
    public void completeTask(
            @NonNull
            Task task)
    {

    }

    @Override
    public void completeTask(
            @NonNull
            String taskId)
    {

    }

    @Override
    public void activateTask(
            @NonNull
            Task task)
    {

    }

    @Override
    public void activateTask(
            @NonNull
            String taskId)
    {

    }

    @Override
    public void clearCompletedTasks()
    {

    }

    @Override
    public void refreshTasks()
    {

    }

    @Override
    public void deleteAllTasks()
    {

    }

    @Override
    public void deleteTask(
            @NonNull
            String taskId)
    {

    }
}
