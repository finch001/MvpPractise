package com.livvy.mvpdemo.tasks;

import android.support.annotation.NonNull;

import com.livvy.mvpdemo.BasePresenter;
import com.livvy.mvpdemo.BaseView;
import com.livvy.mvpdemo.data.Task;

import java.util.List;

/**
 * Created by Finch on 2016/10/10 0010.
 */
public interface TasksContract
{
    interface View extends BaseView<Presenter>
    {
        void setLoadingIndicator(boolean active);

        void showTasks(List<Task> tasks);

        void showAddTask();

        void showTaskDetailsUi(String taskId);

        void showTaskMarkedComplete();

        void showTaskMarkedActive();

        void showCompletedTasksCleared();

        void showLoadingTasksError();

        void showNoTasks();

        void showActiveFilterLabel();

        void showCompletedFilterLabel();

        void showAllFilterLabel();

        void showNoActiveTasks();

        void showNoCompletedTasks();

        void showSuccessfullySavedMessage();

        boolean isActive();

        void showFilteringPopUpMenu();

    }

    interface Presenter extends BasePresenter
    {
        void result(int requestCode, int resultCode);

        void loadTasks(boolean forceUpdate);

        void addNewTask();

        void openTaskDetails(
                @NonNull
                Task requestedTask);

        void completeTask(
                @NonNull
                Task completedTask);

        void activateTask(
                @NonNull
                Task activeTask);

        void clearCompletedTasks();

        void setFiltering(TaskFilterType requestType);

        TaskFilterType getFiltering();
    }
}
