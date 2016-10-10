package com.livvy.mvpdemo.tasks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.livvy.mvpdemo.R;
import com.livvy.mvpdemo.data.Task;

import java.util.List;

/**
 * Created by Finch on 2016/10/10 0010.
 */
public class TasksFragment extends Fragment implements TasksContract.View
{
    private TasksContract.Presenter mPresenter;

    private TasksAdapter mListAdapter;

    private View mNoTasksView;

    private ImageView mNoTaskIcon;

    private TextView mNoTaskMainView;

    private TextView mNoTaskAddView;

    private LinearLayout mTasksView;

    private TextView mFilteringLabelView;

    public TasksFragment()
    {
        // Requires empty public constructor
    }

    public static TasksFragment newInstance()
    {
        return new TasksFragment();
    }


    @Override
    public void setLoadingIndicator(boolean active)
    {

    }

    @Override
    public void showTasks(List<Task> tasks)
    {
        mListAdapter.replaceData(tasks);
        mTasksView.setVisibility(View.VISIBLE);
        mNoTasksView.setVisibility(View.GONE);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void showAddTask()
    {

    }

    @Override
    public void showTaskDetailsUi(String taskId)
    {

    }

    @Override
    public void showTaskMarkedComplete()
    {

    }

    @Override
    public void showTaskMarkedActive()
    {

    }

    @Override
    public void showCompletedTasksCleared()
    {

    }

    @Override
    public void showLoadingTasksError()
    {

    }

    @Override
    public void showNoTasks()
    {

    }

    @Override
    public void showActiveFilterLabel()
    {

    }

    @Override
    public void showCompletedFilterLabel()
    {

    }

    @Override
    public void showAllFilterLabel()
    {

    }

    @Override
    public void showNoActiveTasks()
    {

    }

    @Override
    public void showNoCompletedTasks()
    {

    }

    @Override
    public void showSuccessfullySavedMessage()
    {

    }

    @Override
    public boolean isActive()
    {
        return isAdded();
    }

    @Override
    public void showFilteringPopUpMenu()
    {

    }

    @Override
    public void setPresenter(TasksContract.Presenter presenter)
    {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
            @Nullable
            ViewGroup container,
            @Nullable
            Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.tasks_frag, container, false);

        // Set up tasks view
        ListView listView = (ListView) root.findViewById(R.id.tasks_list);
        listView.setAdapter(mListAdapter);
        mFilteringLabelView = (TextView) root.findViewById(R.id.filteringLabel);
        mTasksView = (LinearLayout) root.findViewById(R.id.tasksLL);

        // Set up  no tasks view
        mNoTasksView = root.findViewById(R.id.noTasks);
        mNoTaskIcon = (ImageView) root.findViewById(R.id.noTasksIcon);
        mNoTaskMainView = (TextView) root.findViewById(R.id.noTasksMain);
        mNoTaskAddView = (TextView) root.findViewById(R.id.noTasksAdd);
        mNoTaskAddView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddTask();
            }
        });

        // Set up floating action button
        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_add_task);

        fab.setImageResource(R.drawable.ic_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.addNewTask();
            }
        });

//        // Set up progress indicator
//        final ScrollChildSwipeRefreshLayout swipeRefreshLayout =
//                (ScrollChildSwipeRefreshLayout) root.findViewById(R.id.refresh_layout);
//        swipeRefreshLayout.setColorSchemeColors(
//                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
//                ContextCompat.getColor(getActivity(), R.color.colorAccent),
//                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
//        );
//        // Set the scrolling view in the custom SwipeRefreshLayout.
//        swipeRefreshLayout.setScrollUpChild(listView);
//
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                mPresenter.loadTasks(false);
//            }
//        });

        setHasOptionsMenu(true);

        return root;
    }

    private static class TasksAdapter extends BaseAdapter
    {
        private List<Task> mTasks;
        private TaskItemListener mItemListener;

        public TasksAdapter(List<Task> tasks, TaskItemListener itemListener)
        {
            setList(tasks);
            mItemListener = itemListener;
        }

        public void replaceData(List<Task> tasks)
        {
            setList(tasks);
            notifyDataSetChanged();
        }

        private void setList(List<Task> tasks)
        {
            mTasks = tasks;
        }

        @Override
        public int getCount()
        {
            return mTasks.size();
        }

        @Override
        public Task getItem(int i)
        {
            return mTasks.get(i);
        }

        @Override
        public long getItemId(int i)
        {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup)
        {
            View rowView = view;
            if (rowView == null)
            {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                rowView = inflater.inflate(R.layout.task_item, viewGroup, false);
            }

            final Task task = getItem(i);

            TextView titleTV = (TextView)rowView.findViewById(R.id.title);
            titleTV.setText(task.getTitleForList());

            CheckBox completeCB = (CheckBox)rowView.findViewById(R.id.complete);

            // Active/completed task UI
            completeCB.setChecked(task.isCompleted());
            if (task.isCompleted())
            {
                rowView.setBackgroundDrawable(viewGroup.getContext().getResources().getDrawable(R.drawable.list_completed_touch_feedback));
            }
            else
            {
                rowView.setBackgroundDrawable(viewGroup.getContext().getResources().getDrawable(R.drawable.touch_feedback));
            }

            completeCB.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (!task.isCompleted())
                    {
                        mItemListener.onCompleteTaskClick(task);
                    }
                    else
                    {
                        mItemListener.onActivateTaskClick(task);
                    }
                }
            });

            rowView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    mItemListener.onTaskClick(task);
                }
            });

            return rowView;
        }
    }

    public interface TaskItemListener {

        void onTaskClick(Task clickedTask);

        void onCompleteTaskClick(Task completedTask);

        void onActivateTaskClick(Task activatedTask);
    }
}
