package com.livvy.mvpdemo.addtask;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.livvy.mvpdemo.data.Task;

/**
 * Created by Finch on 2016/10/8 0008.
 */
public class AddTaskPresenter implements AddTaskContract.Presenter
{
    @NonNull
    private final AddTaskContract.View mAddTaskView;

    @Nullable
    private String mTaskId;

    @Override
    public void saveTask(String title, String description)
    {
        createTask(title, description);
    }

    @Override
    public void populateTask()
    {

    }

    @Override
    public void start()
    {

    }

    private void createTask(String title, String description)
    {
        Task newTask = new Task(title, description);
        if (newTask.isEmpty())
        {
            mAddTaskView.showEmptyTaskError();
        }
        else
        {
            mAddTaskView.showTasksList(title, description);
        }
    }

    public AddTaskPresenter(
            @NonNull
            AddTaskContract.View mAddTaskView,
            @Nullable
            String mTaskId)
    {
        this.mAddTaskView = mAddTaskView;
        this.mTaskId = mTaskId;
        this.mAddTaskView.setPresenter(this);
    }
}
