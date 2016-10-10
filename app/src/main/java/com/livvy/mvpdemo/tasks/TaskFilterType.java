package com.livvy.mvpdemo.tasks;

/**
 * Created by Finch on 2016/10/10 0010.
 */
public enum TaskFilterType
{
    /**
     * Do not filter tasks.
     */
    ALL_TASKS,

    /**
     * Filters only the active (not completed yet) tasks.
     */
    ACTIVE_TASKS,

    /**
     * Filters only the completed tasks.
     */
    COMPLETED_TASKS
}
