package com.learnice.sharesdemo;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.learnice.sharesdemo.MyServerHttp.IMyServerDataResult;
import com.learnice.sharesdemo.MyServerHttp.MyServerHttpRequestImpl;
import com.learnice.sharesdemo.MyServerHttp.MyServerHttpResponseStatus;
import com.learnice.sharesdemo.Other.MD5Secret;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.data;
import static android.R.attr.name;

public class RegisterActivity extends AppCompatActivity implements IMyServerDataResult {

    @BindView(R.id.register_tv_account)
    ImageView registerTvAccount;
    @BindView(R.id.register_et_username)
    EditText registerEtUsername;
    @BindView(R.id.register_usernamelayout)
    RelativeLayout registerUsernamelayout;
    @BindView(R.id.register_tv_secret)
    ImageView registerTvSecret;
    @BindView(R.id.register_et_password)
    EditText registerEtPassword;
    @BindView(R.id.register_passwordlayout)
    RelativeLayout registerPasswordlayout;
    @BindView(R.id.register_tv_secret2)
    ImageView registerTvSecret2;
    @BindView(R.id.register_et_password2)
    EditText registerEtPassword2;
    @BindView(R.id.register_passwordlayout2)
    RelativeLayout registerPasswordlayout2;
    @BindView(R.id.main_btn_register)
    Button mainBtnRegister;
    @BindView(R.id.register_progress)
    ProgressBar registerProgress;
    @BindView(R.id.register_progress_layout)
    RelativeLayout registerProgressLayout;
    netReceiver netReceiver;
    AlertDialog alertDialog;
    @BindView(R.id.login_progress_register)
    ProgressBar loginProgressRegister;
    @BindView(R.id.progress_layout_register)
    RelativeLayout progressLayoutRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setAlertDialog();
        isNoNetwork();
        netReceiver = new netReceiver();
        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(netReceiver, intentFilter);
    }

    @OnClick({R.id.main_btn_register,R.id.login_progress_register, R.id.progress_layout_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_btn_register:
                String name=registerEtUsername.getText().toString().trim();
                String pass1=registerEtPassword.getText().toString().trim();
                String pass2=registerEtPassword2.getText().toString().trim();
                if (name.isEmpty()){
                    Toast.makeText(this,"用户名为空",Toast.LENGTH_SHORT).show();
                }
                else if (pass1.isEmpty()){
                    Toast.makeText(this,"请输入密码",Toast.LENGTH_SHORT).show();
                }
                else if (pass2.isEmpty()){
                    Toast.makeText(this,"请再次输入密码",Toast.LENGTH_SHORT).show();
                }
                else if (!pass1.equals(pass2)){
                    Toast.makeText(this,"输入的两次密码不一致请重新输入",Toast.LENGTH_SHORT).show();
            } else {
                mainBtnRegister.setVisibility(View.GONE);
                progressLayoutRegister.setVisibility(View.VISIBLE);
                new MyServerHttpRequestImpl().checkUser("101", name, MD5Secret.getMD5String(pass2), new MyServerHttpResponseStatus(this));
            }
                break;
            case R.id.login_progress_register:
                break;
            case R.id.progress_layout_register:
                break;

        }
    }

    @Override
    public void resultString(Object data) {
        String checkNum=data.toString().trim();
        progressLayoutRegister.setVisibility(View.GONE);
        if (checkNum.equals("-100")){
            mainBtnRegister.setVisibility(View.VISIBLE);
            Toast.makeText(this,"用户名已存在，换一个试试",Toast.LENGTH_SHORT).show();
        }
        if (checkNum.equals("1")){
            Toast.makeText(this,"注册成功，请登录",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void resultSayList(String list) {

    }

    @Override
    public void resultSayList(String list, int position) {

    }

    public void setAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        alertDialog = builder.setTitle("未联网")
                .setMessage("请连接网络")
                .setCancelable(false)
                .setPositiveButton("好", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).create();
    }

    public void isNoNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            if (alertDialog.isShowing()) {
                alertDialog.dismiss();
            }
        } else {
            alertDialog.show();
        }
    }
    public class netReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            isNoNetwork();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(netReceiver);
    }
}
