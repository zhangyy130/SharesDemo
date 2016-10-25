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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.learnice.sharesdemo.MyServerHttp.IMyServerDataResult;
import com.learnice.sharesdemo.MyServerHttp.MyParams;
import com.learnice.sharesdemo.MyServerHttp.MyServerHttpRequestImpl;
import com.learnice.sharesdemo.MyServerHttp.MyServerHttpResponseStatus;
import com.learnice.sharesdemo.Other.MD5Secret;
import com.learnice.sharesdemo.Services.SyncLocalDBServices;
import com.learnice.sharesdemo.SharedData.AboutLogin;
import com.learnice.sharesdemo.SharedData.AboutUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity implements IMyServerDataResult {


    SyncSuccess syncSuccess;
    @BindView(R.id.tv_account)
    ImageView tvAccount;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.login_usernamelayout)
    RelativeLayout loginUsernamelayout;
    @BindView(R.id.tv_secret)
    ImageView tvSecret;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.login_passwordlayout)
    RelativeLayout loginPasswordlayout;
    @BindView(R.id.main_btn_login)
    Button mainBtnLogin;
    @BindView(R.id.login_register)
    TextView loginRegister;
    @BindView(R.id.login_progress)
    ProgressBar loginProgress;
    @BindView(R.id.progress_layout)
    RelativeLayout progressLayout;
    netReceiver netReceiver;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (new AboutLogin(this).readLoginSuccess()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        syncSuccess = new SyncSuccess();
        registerReceiver(syncSuccess, new IntentFilter("SYNCOK"));
        loginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
        setAlertDialog();
        isNoNetwork();
        netReceiver=new netReceiver();
        IntentFilter intentFilter=new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(netReceiver,intentFilter);
    }

    String name;

    @OnClick(R.id.main_btn_login)
    public void onClick() {
        progressLayout.setVisibility(View.VISIBLE);
        mainBtnLogin.setVisibility(View.GONE);
        name = etUsername.getText().toString().trim();
        String pass = etPassword.getText().toString().trim();
        new MyServerHttpRequestImpl().checkUser(MyParams.USER_CHECK, name, MD5Secret.getMD5String(pass), new MyServerHttpResponseStatus(this));
    }

    @Override
    public void resultString(Object data) {
        loginProgress.setVisibility(View.VISIBLE);
        if (data.toString().trim().equals("-1")) {
            progressLayout.setVisibility(View.GONE);
            mainBtnLogin.setVisibility(View.VISIBLE);
            Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
        } else {
            new AboutUser(this).writeName(name);
            new AboutLogin(this).writeLoginSuccess();
            Intent intent = new Intent(this, SyncLocalDBServices.class);
            intent.putExtra("name", name);
            startService(intent);//login 同步数据库
//            startActivity(new Intent(this,MainActivity.class));
//            finish();
        }
    }

    @Override
    public void resultSayList(String list) {

    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, SyncLocalDBServices.class));
        unregisterReceiver(syncSuccess);
        unregisterReceiver(netReceiver);
        super.onDestroy();
    }

    public class SyncSuccess extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            loginProgress.setVisibility(View.GONE);
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }
    public void setAlertDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        alertDialog= builder.setTitle("未联网")
                .setMessage("请连接网络")
                .setCancelable(false)
                .setPositiveButton("好", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).create();
    }
    public void isNoNetwork(){
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if (networkInfo!=null&&networkInfo.isAvailable()){
            if (alertDialog.isShowing()){
                alertDialog.dismiss();
            }
        }
        else {
            alertDialog.show();
        }
    }
    public class netReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            isNoNetwork();
        }
    }
}

