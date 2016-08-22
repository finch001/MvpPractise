package com.livvy.mvpdemo.View;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.livvy.mvpdemo.Model.User.Db.UserDao;
import com.livvy.mvpdemo.Presenter.MainPresenter;
import com.livvy.mvpdemo.R;
import com.livvy.mvpdemo.View.Fragment.LoginDialogFragment;
import com.livvy.mvpdemo.View.Interface.ILogin;
import com.livvy.mvpdemo.View.Interface.ImainView;

public class MainActivity extends AppCompatActivity implements ImainView, ILogin {
    private MainPresenter mainPresenter;
    private TextView tvContent;
    private Button dilogBtn;
    private Button addSQLite;
    private ContentResolver contentResolver;
    private Button querySQLite;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contentResolver = this.getContentResolver();
        initView();
        initListener();
        loadData();
    }

    public void initView() {
        tvContent = (TextView) findViewById(R.id.tv_content);
        dilogBtn = (Button) findViewById(R.id.dialogBtn);
        addSQLite = (Button) findViewById(R.id.addSQLite);
        querySQLite = (Button) findViewById(R.id.querySQLite);
    }

    public void initListener() {
        dilogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginFragment();
            }
        });

        addSQLite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues value = new ContentValues();
                value.put(UserDao.User.COLUMN_NAME_SHOWNAME, "finch");
                value.put(UserDao.User.COLUMN_NAME_ISMANUALLOGOUT, 0);
                value.put(UserDao.User.COLUMN_NAME_LOGINNAME, "zhengyan");
                value.put(UserDao.User.COLUMN_NAME_LASTLOGINTIME, System.currentTimeMillis());
                value.put(UserDao.User.COLUMN_NAME_PASSWORD, "zy1992928");
                Uri newUri = contentResolver.insert(UserDao.User.CONTENT_URI, value);
                Toast.makeText(MainActivity.this, "" + newUri, Toast.LENGTH_SHORT).show();
            }
        });

        querySQLite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = contentResolver.query(UserDao.User.CONTENT_URI, new String[]{UserDao.User.COLUMN_NAME_LOGINNAME, UserDao.User.COLUMN_NAME_SHOWNAME, UserDao.User.COLUMN_NAME_PASSWORD}, null, null, null);

                if (cursor == null) {
                    Toast.makeText(MainActivity.this, "datasqlite is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                String msg = "";
                if (cursor.moveToFirst()) {
                    do {
                        msg += "ShowName: " + cursor.getString(cursor.getColumnIndex(UserDao.User.COLUMN_NAME_SHOWNAME)) + ",";
                        msg += "loginName: " + cursor.getString(cursor.getColumnIndex(UserDao.User.COLUMN_NAME_LOGINNAME)) + ",";
                        msg += "password: " + cursor.getInt(cursor.getColumnIndex(UserDao.User.COLUMN_NAME_PASSWORD)) + ",";
                    } while (cursor.moveToNext());
                }

                Toast.makeText(MainActivity.this, "" + msg, Toast.LENGTH_SHORT).show();
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
