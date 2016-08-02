package com.livvy.mvpdemo.View.Fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.livvy.mvpdemo.R;
import com.livvy.mvpdemo.View.Interface.ILogin;

/**
 * Created by livvy on 2016/8/2 0002.
 */
public class LoginDialogFragment extends DialogFragment {
    private EditText userEdt;
    private EditText passwordEdt;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = View.inflate(getActivity(), R.layout.fragment_login, null);
        initView(view);
        builder.setView(view).setPositiveButton("Login in", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkData();
            }
        }).setNegativeButton("Cancel", null);
        return builder.create();
    }

    private void initView(View view) {
        userEdt = (EditText) view.findViewById(R.id.loginname_edt);
        passwordEdt = (EditText) view.findViewById(R.id.loginpassword_edt);
    }

    private void checkData() {
        String userName = userEdt.getText().toString().trim();
        String password = passwordEdt.getText().toString().trim();
        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)) {
            ((ILogin) getActivity()).getUserInfo(userName, password);
        }
    }
}
