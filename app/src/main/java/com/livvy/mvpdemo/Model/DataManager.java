package com.livvy.mvpdemo.Model;

import com.livvy.mvpdemo.Model.Interface.TaskDataSource;

/**
 * Created by livvy on 2016/8/2 0002.
 */
public class DataManager {
    TaskDataSource dataSource;

    public DataManager(TaskDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String getShowContent(){
        return dataSource.getStringFromCache() + " " + dataSource.getStringFromRemote();
    }
}
