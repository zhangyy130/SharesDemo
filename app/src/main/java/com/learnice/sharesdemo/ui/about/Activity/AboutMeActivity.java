package com.learnice.sharesdemo.ui.about.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.learnice.sharesdemo.R;
import butterknife.BindView;
import butterknife.ButterKnife;


public class AboutMeActivity extends AppCompatActivity {

    @BindView(R.id.about_me_toolbar)
    Toolbar aboutMeToolbar;
    @BindView(R.id.about_me_webview)
    WebView aboutMeWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        ButterKnife.bind(this);
        setSupportActionBar(aboutMeToolbar);
        final ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.mipmap.abc_ic_ab_back_mtrl_am_alpha);
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);
        String url=getIntent().getStringExtra("url");
        Log.e("8y4652695",url);
        aboutMeWebview.getSettings().setJavaScriptEnabled(true);

        aboutMeWebview.loadUrl(url);
        aboutMeWebview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                aboutMeWebview.loadUrl(url);
                return true;
            }
        });
        WebChromeClient chromeClient=new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                actionBar.setTitle(title);
            }
        };
        aboutMeWebview.setWebChromeClient(chromeClient);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (aboutMeWebview.canGoBack()){
            aboutMeWebview.goBack();
        }
        else {
            finish();
        }
    }
}
