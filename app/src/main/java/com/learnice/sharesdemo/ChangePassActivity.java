package com.learnice.sharesdemo;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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
import com.learnice.sharesdemo.SharedData.AboutUser;

import org.xutils.common.Callback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePassActivity extends AppCompatActivity implements IMyServerDataResult {
    @BindView(R.id.change_pass_tv_account)
    ImageView changePassTvAccount;
    @BindView(R.id.change_pass_et_current_pass)
    EditText changePassEtCurrentPass;
    @BindView(R.id.change_pass_usernamelayout)
    RelativeLayout changePassUsernamelayout;
    @BindView(R.id.change_pass_tv_secret)
    ImageView changePassTvSecret;
    @BindView(R.id.change_pass_et_password)
    EditText changePassEtPassword;
    @BindView(R.id.change_pass_passwordlayout)
    RelativeLayout changePassPasswordlayout;
    @BindView(R.id.change_pass_tv_secret2)
    ImageView changePassTvSecret2;
    @BindView(R.id.change_pass_et_password2)
    EditText changePassEtPassword2;
    @BindView(R.id.change_pass_passwordlayout2)
    RelativeLayout changePassPasswordlayout2;
    @BindView(R.id.main_btn_change_pass)
    Button mainBtnChangePass;
    @BindView(R.id.change_pass_progress)
    ProgressBar changePassProgress;
    @BindView(R.id.change_pass_progress_layout)
    RelativeLayout changePassProgressLayout;
    String pass2;
    String pass1;
    String currentPass;
    @BindView(R.id.change_pass_toolbar)
    Toolbar changePassToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        ButterKnife.bind(this);
        setSupportActionBar(changePassToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("修改密码");
        actionBar.setHomeAsUpIndicator(R.mipmap.abc_ic_ab_back_mtrl_am_alpha);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.main_btn_change_pass)
    public void onClick() {
        currentPass = changePassEtCurrentPass.getText().toString().trim();
        pass1 = changePassEtPassword.getText().toString().trim();
        pass2 = changePassEtPassword2.getText().toString().trim();
        if (pass1.length() == 0 || pass2.length() == 0) {
            Toast.makeText(this, "新密码为空", Toast.LENGTH_SHORT).show();
        } else {
            if (pass1.equals(pass2)) {
                mainBtnChangePass.setVisibility(View.GONE);
                changePassProgressLayout.setVisibility(View.VISIBLE);
                new MyServerHttpRequestImpl().checkUser("100", new AboutUser(this).readName(),
                        currentPass, new MyServerHttpResponseStatus(this));
            } else {
                Toast.makeText(this, "输入的两次密码不同", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void resultString(Object data) {
        if (Integer.valueOf(data.toString().trim()) != -1) {
            new MyServerHttpRequestImpl().checkUser("102", new AboutUser(this).readName(), pass2, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    if (result.toString().trim().equals("1")) {
                        Toast.makeText(ChangePassActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });
        } else {
            Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
            changePassProgressLayout.setVisibility(View.GONE);
            mainBtnChangePass.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void resultSayList(String list) {

    }
}
