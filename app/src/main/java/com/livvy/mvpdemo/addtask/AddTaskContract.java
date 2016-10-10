package com.livvy.mvpdemo.addtask;

import com.livvy.mvpdemo.BasePresenter;
import com.livvy.mvpdemo.BaseView;
import com.livvy.mvpdemo.data.Task;

import java.util.List;

/**
 * Created by Finch on 2016/10/8 0008.
 */
public interface AddTaskContract
{
    interface View extends BaseView<Presenter>
    {
        void showEmptyTaskError();

        void showTasksList(String title, String description);

        void showTasksLists(List<Task> tasks);

        void setTitle(String title);

        void setDescription(String description);

        boolean isActive();
    }

    interface Presenter extends BasePresenter
    {
        void saveTask(String title, String description);

        void populateTask();

        void getAllTask();
    }

}
