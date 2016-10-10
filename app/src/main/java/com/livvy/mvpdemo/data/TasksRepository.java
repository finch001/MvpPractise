package com.livvy.mvpdemo.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Finch on 2016/10/9 0009.
 */
public class TasksRepository implements TasksDataSource
{

    private static TasksRepository INSTANCE = null;

    //private final TasksDataSource mTasksRemoteDataSource;

    private final TasksDataSource mTasksLocalDataSource;

    Map<String, Task> mCachedTasks;

    boolean mCacheIsDirty = false;

    // Prevent direct instantiation.
    private TasksRepository(

            @NonNull
            TasksDataSource tasksLocalDataSource)
    {
        //mTasksRemoteDataSource = tasksRemoteDataSource;
        mTasksLocalDataSource = tasksLocalDataSource;
    }

    public static TasksRepository getInstance(TasksDataSource tasksLocalDataSource)
    {
        if (INSTANCE == null)
        {
            INSTANCE = new TasksRepository(tasksLocalDataSource);
        }
        return INSTANCE;
    }

    public static void destoryInstace()
    {
        INSTANCE = null;
    }


    @Override
    public void getTasks(
            @NonNull
            final LoadTasksCallback callback)
    {
        // Respond immediately with cache if available and not dirty
        if (mCachedTasks != null && !mCacheIsDirty)
        {
            callback.onTasksLoaded(new ArrayList<>(mCachedTasks.values()));
            return;
        }

        if (mCacheIsDirty)
        {
            // If the cache is dirty we need to fetch new data from the network.
            getTasksFromRemoteDataSource(callback);
        }
        else
        {
            // Query the local storage if available. If not, query the network.
            mTasksLocalDataSource.getTasks(new LoadTasksCallback()
            {
                @Override
                public void onTasksLoaded(List<Task> tasks)
                {
                    refreshCache(tasks);
                    callback.onTasksLoaded(new ArrayList<>(mCachedTasks.values()));
                }

                @Override
                public void onDataNotAvailable()
                {
                    getTasksFromRemoteDataSource(callback);
                }
            });
        }
    }

    @Override
    public void getTask(
            @NonNull
            String taskId,
            @NonNull
            GetTaskCallback callback)
    {
        Task cachedTask = getTaskWithId(taskId);

        // Respond immediately with cache if available
        if (cachedTask != null)
        {
            callback.onTaskLoaded(cachedTask);
            return;
        }

    }

    @Override
    public void saveTask(
            @NonNull
            Task task)
    {
        //mTasksRemoteDataSource.saveTask(task);
        mTasksLocalDataSource.saveTask(task);

        // Do in memory cache update to keep the app UI up to date
        if (mCachedTasks == null)
        {
            mCachedTasks = new LinkedHashMap<>();
        }
        mCachedTasks.put(task.mId, task);

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
        mCacheIsDirty = true;
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

    private void refreshCache(List<Task> tasks)
    {
        if (mCachedTasks == null)
        {
            mCachedTasks = new LinkedHashMap<>();
        }
        mCachedTasks.clear();
        for (Task task : tasks)
        {
            mCachedTasks.put(task.mId, task);
        }
        mCacheIsDirty = false;
    }

    private void refreshLocalDataSource(List<Task> tasks)
    {
        mTasksLocalDataSource.deleteAllTasks();
        for (Task task : tasks)
        {
            mTasksLocalDataSource.saveTask(task);
        }
    }

    private void getTasksFromRemoteDataSource(
            @NonNull
            final LoadTasksCallback callback)
    {
        //        mTasksRemoteDataSource.getTasks(new LoadTasksCallback()
        //        {
        //            @Override
        //            public void onTasksLoaded(List<Task> tasks)
        //            {
        //                refreshCache(tasks);
        //                refreshLocalDataSource(tasks);
        //                callback.onTasksLoaded(new ArrayList<>(mCachedTasks.values()));
        //            }
        //
        //            @Override
        //            public void onDataNotAvailable()
        //            {
        //                callback.onDataNotAvailable();
        //            }
        //        });
    }

    @Nullable
    private Task getTaskWithId(
            @NonNull
            String id)
    {
        if (mCachedTasks == null || mCachedTasks.isEmpty())
        {
            return null;
        }
        else
        {
            return mCachedTasks.get(id);
        }
    }

}
