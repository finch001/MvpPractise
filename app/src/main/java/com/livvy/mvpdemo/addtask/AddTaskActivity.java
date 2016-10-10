package com.livvy.mvpdemo.addtask;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.livvy.mvpdemo.R;
import com.livvy.mvpdemo.Utils.ActivityUtils;
import com.livvy.mvpdemo.data.TasksRepository;
import com.livvy.mvpdemo.data.local.TasksLocalDataSource;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Finch on 2016/10/8 0008.
 */
public class AddTaskActivity extends AppCompatActivity
{
    public static final int REQUEST_ADD_TASK = 1;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindString(R.string.edit_task)
    String editTaskStr;
    @BindString(R.string.add_task)
    String addTaskStr;

    private ActionBar actionBar;

    private String taskId;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtask_act);
        ButterKnife.bind(this);
        initToolBar();
        initFragment();
    }

    private void initFragment()
    {
        taskId = null;
        AddTaskFragment addEditTaskFragment = (AddTaskFragment)getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (addEditTaskFragment == null)
        {
            addEditTaskFragment = AddTaskFragment.newInstance();
            if (getIntent().hasExtra(AddTaskFragment.ARGUMENT_EDIT_TASK_ID))
            {
                taskId = getIntent().getStringExtra(AddTaskFragment.ARGUMENT_EDIT_TASK_ID);
                actionBar.setTitle(R.string.edit_task);
                Bundle bundle = new Bundle();
                bundle.putString(AddTaskFragment.ARGUMENT_EDIT_TASK_ID, taskId);
                addEditTaskFragment.setArguments(bundle);
            }
            else
            {
                actionBar.setTitle(R.string.add_task);
            }
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), addEditTaskFragment, R.id.contentFrame);
        }
        // Create the presenter
        new AddTaskPresenter(addEditTaskFragment, taskId, TasksRepository.getInstance(TasksLocalDataSource.getInstance(this)));
    }

    private void initToolBar()
    {
        // Set up the toolbar.
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }


    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }
}
