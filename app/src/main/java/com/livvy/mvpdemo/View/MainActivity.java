package com.livvy.mvpdemo.View;

import android.content.ContentResolver;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.livvy.mvpdemo.Model.User.Db.UserDao;
import com.livvy.mvpdemo.Model.User.Model.User;
import com.livvy.mvpdemo.Presenter.MainPresenter;
import com.livvy.mvpdemo.R;
import com.livvy.mvpdemo.View.Fragment.LoginDialogFragment;
import com.livvy.mvpdemo.View.Interface.ILogin;
import com.livvy.mvpdemo.View.Interface.ImainView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ImainView, ILogin
{
    private MainPresenter mainPresenter;
    private TextView tvContent;
    private Button dilogBtn;
    private Button addSQLite;
    private ContentResolver contentResolver;
    private Button querySQLite;
    private UserDao userDao;
    private List<User> users;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contentResolver = this.getContentResolver();
        initView();
        initListener();
        loadData();
    }

    public void initView()
    {
        tvContent = (TextView)findViewById(R.id.tv_content);
        dilogBtn = (Button)findViewById(R.id.dialogBtn);
        addSQLite = (Button)findViewById(R.id.addSQLite);
        querySQLite = (Button)findViewById(R.id.querySQLite);
    }

    public void initListener()
    {
        dilogBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showLoginFragment();
            }
        });

        addSQLite.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                User user = new User("finch", "wukong", "zy1992928", "" + System.currentTimeMillis(), false);
                userDao.insertUser(user);
                Toast.makeText(MainActivity.this, "insert successFul", Toast.LENGTH_SHORT).show();
            }
        });

        querySQLite.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                users = userDao.getAllUser();
                StringBuilder sb = new StringBuilder();
                for (User user : users)
                {
                    sb.append(user.toString() + "\n");
                }

                Toast.makeText(MainActivity.this, sb.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showLoginFragment()
    {
        LoginDialogFragment loginDialog = new LoginDialogFragment();
        loginDialog.show(getSupportFragmentManager(), "loginDialog");
    }

    public void loadData()
    {
        mainPresenter = new MainPresenter().Test();
        mainPresenter.addTaskListener(this);
        mainPresenter.getString();

        userDao = new UserDao(this);
    }

    @Override
    public void getString(String content)
    {
        tvContent.setText(content);
    }


    @Override
    public void getUserInfo(String userName, String password)
    {
        Toast.makeText(MainActivity.this, userName + "\n" + password, Toast.LENGTH_SHORT).show();
    }
}
