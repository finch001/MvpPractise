package com.livvy.mvpdemo.Model;

import com.livvy.mvpdemo.Model.Interface.TaskDataSource;

/**
 * Created by livvy on 2016/8/2 0002.
 */
public class TaskDataSourceImpl implements TaskDataSource {


    @Override
    public String getStringFromCache() {
        return "hello";
    }

    @Override
    public String getStringFromRemote() {
        return "world";
    }
}
