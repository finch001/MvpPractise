package com.livvy.mvpdemo.tasks;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.livvy.mvpdemo.addtask.AddTaskActivity;
import com.livvy.mvpdemo.data.Task;
import com.livvy.mvpdemo.data.TasksDataSource;
import com.livvy.mvpdemo.data.TasksRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Finch on 2016/10/10 0010.
 */
public class TasksPresenter implements TasksContract.Presenter
{
    private final TasksRepository mTasksRepository;

    private final TasksContract.View mTasksView;

    private TaskFilterType mCurrentFiltering = TaskFilterType.ALL_TASKS;

    private boolean mFirstLoad = true;

    public TasksPresenter(
            @NonNull
            TasksRepository tasksRepository,
            @NonNull
            TasksContract.View tasksView)
    {
        mTasksRepository = tasksRepository;
        mTasksView = tasksView;

        mTasksView.setPresenter(this);
    }

    @Override
    public void result(int requestCode, int resultCode)
    {
        // If a task was successfully added, show snackbar
        if (AddTaskActivity.REQUEST_ADD_TASK == requestCode && Activity.RESULT_OK == resultCode)
        {
            mTasksView.showSuccessfullySavedMessage();
        }
    }

    @Override
    public void loadTasks(boolean forceUpdate)
    {
        // Simplification for sample: a network reload will be forced on first load.
        loadTasks(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    /**
     * @param forceUpdate Pass in true to refresh the data in the {@link TasksDataSource}
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     */
    private void loadTasks(boolean forceUpdate, final boolean showLoadingUI)
    {
        if (showLoadingUI)
        {
            mTasksView.setLoadingIndicator(true);
        }
        if (forceUpdate)
        {
            mTasksRepository.refreshTasks();
        }

        // The network request might be handled in a different thread so make sure Espresso knows
        // that the app is busy until the response is handled.
        //EspressoIdlingResource.increment(); // App is busy until further notice

        mTasksRepository.getTasks(new TasksDataSource.LoadTasksCallback()
        {
            @Override
            public void onTasksLoaded(List<Task> tasks)
            {
                List<Task> tasksToShow = new ArrayList<Task>();

                // This callback may be called twice, once for the cache and once for loading
                // the data from the server API, so we check before decrementing, otherwise
                // it throws "Counter has been corrupted!" exception.
                //                if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                //                    EspressoIdlingResource.decrement(); // Set app as idle.
                //                }

                // We filter the tasks based on the requestType
                for (Task task : tasks)
                {
                    switch (mCurrentFiltering)
                    {
                        case ALL_TASKS:
                            tasksToShow.add(task);
                            break;
                        case ACTIVE_TASKS:
                            //                            if (task.isActive()) {
                            //                                tasksToShow.add(task);
                            //                            }
                            break;
                        case COMPLETED_TASKS:
                            if (task.isCompleted())
                            {
                                tasksToShow.add(task);
                            }
                            break;
                        default:
                            tasksToShow.add(task);
                            break;
                    }
                }
                // The view may not be able to handle UI updates anymore
                if (!mTasksView.isActive())
                {
                    return;
                }
                if (showLoadingUI)
                {
                    mTasksView.setLoadingIndicator(false);
                }

                processTasks(tasksToShow);
            }

            @Override
            public void onDataNotAvailable()
            {
                // The view may not be able to handle UI updates anymore
                if (!mTasksView.isActive())
                {
                    return;
                }
                mTasksView.showLoadingTasksError();
            }
        });
    }

    private void processTasks(List<Task> tasks)
    {
        if (tasks.isEmpty())
        {
            // Show a message indicating there are no tasks for that filter type.
            processEmptyTasks();
        }
        else
        {
            // Show the list of tasks
            mTasksView.showTasks(tasks);
            // Set the filter label's text.
            showFilterLabel();
        }
    }

    private void processEmptyTasks()
    {
        switch (mCurrentFiltering)
        {
            case ACTIVE_TASKS:
                mTasksView.showNoActiveTasks();
                break;
            case COMPLETED_TASKS:
                mTasksView.showNoCompletedTasks();
                break;
            default:
                mTasksView.showNoTasks();
                break;
        }
    }

    private void showFilterLabel()
    {
        switch (mCurrentFiltering)
        {
            case ACTIVE_TASKS:
                mTasksView.showActiveFilterLabel();
                break;
            case COMPLETED_TASKS:
                mTasksView.showCompletedFilterLabel();
                break;
            default:
                mTasksView.showAllFilterLabel();
                break;
        }
    }

    @Override
    public void addNewTask()
    {

    }

    @Override
    public void openTaskDetails(
            @NonNull
            Task requestedTask)
    {

    }

    @Override
    public void completeTask(
            @NonNull
            Task completedTask)
    {

    }

    @Override
    public void activateTask(
            @NonNull
            Task activeTask)
    {

    }

    @Override
    public void clearCompletedTasks()
    {

    }

    @Override
    public void setFiltering(TaskFilterType requestType)
    {

    }

    @Override
    public TaskFilterType getFiltering()
    {
        return null;
    }

    @Override
    public void start()
    {
        loadTasks(false);
    }
}
