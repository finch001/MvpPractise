package com.livvy.mvpdemo.data;

import android.support.annotation.Nullable;

import java.util.Objects;
import java.util.UUID;

/**
 * Created by Finch on 2016/10/8 0008.
 */
public class Task
{
    public String mId;
    public String title;
    public String description;
    public boolean isCompleted;

    public Task(String title, String description)
    {
        mId = UUID.randomUUID().toString();
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

    @Nullable
    public String getTitleForList()
    {
        if (title != null && !title.equals(""))
        {
            return title;
        }
        else
        {
            return description;
        }
    }

    public boolean isCompleted()
    {
        return isCompleted;
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
