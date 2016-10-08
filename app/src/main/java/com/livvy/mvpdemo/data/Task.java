package com.livvy.mvpdemo.data;

import java.util.Objects;

/**
 * Created by Finch on 2016/10/8 0008.
 */
public class Task
{
    public String title;
    public String description;

    public Task(String title, String description)
    {
        this.title = title;
        this.description = description;
    }

    public boolean isEmpty()
    {
        return (title == null || "".equals(title)) && (description == null || "".equals(description));
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        Task task = (Task)o;
        return Objects.equals(title, task.title) && Objects.equals(description, task.description);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(title, description);
    }

    @Override
    public String toString()
    {
        return "Task with title " + title;
    }
}
