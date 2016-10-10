package com.livvy.mvpdemo.tasks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.livvy.mvpdemo.R;
import com.livvy.mvpdemo.Utils.ActivityUtils;
import com.livvy.mvpdemo.data.TasksRepository;
import com.livvy.mvpdemo.data.local.TasksLocalDataSource;

/**
 * Created by Finch on 2016/10/10 0010.
 */
public class TasksActivity extends AppCompatActivity
{
    private static final String CURRENT_FILTERING_KEY = "CURRENT_FILTERING_KEY";

    private DrawerLayout mDrawerLayout;

    private TasksPresenter mTasksPresenter;

    @Override
    protected void onCreate(
            @Nullable
            Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks_act);
        initView();
    }

    private void initView()
    {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        // Set up the navigation drawer.
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        setupDrawerContent(navigationView);

        TasksFragment tasksFragment = (TasksFragment)getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (tasksFragment == null)
        {
            // Create the fragment
            tasksFragment = TasksFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), tasksFragment, R.id.contentFrame);
        }

        // Create the presenter
        mTasksPresenter = new TasksPresenter(TasksRepository.getInstance(TasksLocalDataSource.getInstance(this)), tasksFragment);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                // Open the navigation drawer when the home icon is selected from the toolbar.
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView)
    {
        if (navigationView == null)
        {
            return;
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.list_navigation_menu_item:
                        // Do nothing, we're already on that screen
                        break;
                    case R.id.statistics_navigation_menu_item:
                        //                                Intent intent =
                        //                                        new Intent(TasksActivity.this, StatisticsActivity.class);
                        //                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        //                                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        //                                startActivity(intent);
                        break;
                    default:
                        break;
                }
                // Close the navigation drawer when an item is selected.
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }


}
