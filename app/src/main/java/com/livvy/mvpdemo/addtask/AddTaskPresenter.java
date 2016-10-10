package com.livvy.mvpdemo.addtask;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.livvy.mvpdemo.data.Task;
import com.livvy.mvpdemo.data.TasksDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Finch on 2016/10/8 0008.
 */
public class AddTaskPresenter implements AddTaskContract.Presenter
{
    @NonNull
    private final AddTaskContract.View mAddTaskView;

    private final TasksDataSource mTasksRepository;

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
            mTasksRepository.saveTask(newTask);
            mAddTaskView.showTasksList(title, description);
        }
    }

    public AddTaskPresenter(
            @NonNull
            AddTaskContract.View mAddTaskView,
            @Nullable
            String mTaskId,
            @NonNull
            TasksDataSource tasksRepository)
    {
        this.mAddTaskView = mAddTaskView;
        this.mTasksRepository = tasksRepository;
        this.mTaskId = mTaskId;
        this.mAddTaskView.setPresenter(this);
    }

    @Override
    public void getAllTask()
    {
        mTasksRepository.getTasks(new TasksDataSource.LoadTasksCallback()
        {
            @Override
            public void onTasksLoaded(List<Task> tasks)
            {
                mAddTaskView.showTasksLists(tasks);
            }

            @Override
            public void onDataNotAvailable()
            {
                List<Task> tasks = new ArrayList<Task>();
                mAddTaskView.showTasksLists(tasks);
            }
        });
    }
}
