package com.livvy.mvpdemo.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.livvy.mvpdemo.Presenter.MainPresenter;
import com.livvy.mvpdemo.R;
import com.livvy.mvpdemo.View.Fragment.LoginDialogFragment;
import com.livvy.mvpdemo.View.Interface.ILogin;
import com.livvy.mvpdemo.View.Interface.ImainView;

public class MainActivity extends AppCompatActivity implements ImainView, ILogin {
    private MainPresenter mainPresenter;
    private TextView tvContent;
    private Button dilogBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        loadData();
    }

    public void initView() {
        tvContent = (TextView) findViewById(R.id.tv_content);
        dilogBtn = (Button) findViewById(R.id.dialogBtn);
    }

    public void initListener() {
        dilogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginFragment();
            }
        });

    }

    private void showLoginFragment() {
        LoginDialogFragment loginDialog = new LoginDialogFragment();
        loginDialog.show(getSupportFragmentManager(), "loginDialog");
    }

    public void loadData() {
        mainPresenter = new MainPresenter().Test();
        mainPresenter.addTaskListener(this);
        mainPresenter.getString();
    }

    @Override
    public void getString(String content) {
        tvContent.setText(content);
    }


    @Override
    public void getUserInfo(String userName, String password) {
        Toast.makeText(MainActivity.this, userName + "\n" + password, Toast.LENGTH_SHORT).show();
    }
}
