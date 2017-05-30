package com.learnice.sharesdemo.ui.changepass.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.learnice.sharesdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangePassActivity extends AppCompatActivity  {
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
}
