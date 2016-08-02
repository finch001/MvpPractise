package com.livvy.mvpdemo.Presenter;

import com.livvy.mvpdemo.Model.DataManager;
import com.livvy.mvpdemo.Model.TaskDataSourceImpl;
import com.livvy.mvpdemo.Model.TaskDataSourceTestImpl;
import com.livvy.mvpdemo.View.Interface.ImainView;

/**
 * Created by livvy on 2016/8/2 0002.
 */
public class MainPresenter {
    private ImainView mainView;
    private DataManager dataManager;

    public MainPresenter() {
        this.dataManager = new DataManager(new TaskDataSourceImpl());
    }

    public MainPresenter Test() {
        this.dataManager = new DataManager((new TaskDataSourceTestImpl()));
        return this;
    }

    public MainPresenter addTaskListener(ImainView viewListener) {
        this.mainView = viewListener;
        return this;
    }

    public void getString() {
        String str = dataManager.getShowContent();
        mainView.getString(str);
    }

}
