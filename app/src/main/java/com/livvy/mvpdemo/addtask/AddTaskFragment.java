package com.livvy.mvpdemo.addtask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.livvy.mvpdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Finch on 2016/10/8 0008.
 */
public class AddTaskFragment extends Fragment implements AddTaskContract.View
{
    private AddTaskContract.Presenter presenter;
    public static final String ARGUMENT_EDIT_TASK_ID = "EDIT_TASK_ID";
    @BindView(R.id.add_task_title)
    TextView titleTv;
    @BindView(R.id.add_task_description)
    TextView descriptionTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.addtask_frag, container, false);
        ButterKnife.bind(this, root);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        FloatingActionButton fab = (FloatingActionButton)getActivity().findViewById(R.id.fab_edit_task_done);
        fab.setImageResource(R.drawable.ic_done);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                presenter.saveTask(titleTv.getText().toString(), descriptionTv.getText().toString());
            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
        presenter.start();
    }

    public static AddTaskFragment newInstance()
    {
        return new AddTaskFragment();
    }

    public AddTaskFragment()
    {
    }

    @Override
    public void showEmptyTaskError()
    {
        Snackbar.make(titleTv, getString(R.string.task_error), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showTasksList(String title, String description)
    {
        Snackbar.make(titleTv, title + " " + description, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setTitle(String title)
    {
        titleTv.setText(title);
    }

    @Override
    public void setDescription(String description)
    {
        descriptionTv.setText(description);
    }

    @Override
    public boolean isActive()
    {
        return false;
    }

    @Override
    public void setPresenter(AddTaskContract.Presenter presenter)
    {
        this.presenter = presenter;
    }

}
