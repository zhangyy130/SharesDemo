package com.learnice.sharesdemo.ui.news;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.learnice.sharesdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsActivity extends AppCompatActivity {

    @BindView(R.id.wv_news)
    WebView wvNews;
    @BindView(R.id.toolbar_news)
    Toolbar toolbarNews;
    private String url;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        url = getIntent().getStringExtra("newsURL");
        boolean supportJs = getIntent().getBooleanExtra("supportJs", false);
        setSupportActionBar(toolbarNews);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.mipmap.abc_ic_ab_back_mtrl_am_alpha);
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);

        wvNews.getSettings().setJavaScriptEnabled(supportJs);

        wvNews.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                wvNews.loadUrl(url);
                return true;
            }
        });
        WebChromeClient chromeClient = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                actionBar.setTitle(title);
            }
        };
        wvNews.setWebChromeClient(chromeClient);
        initData();
    }

    private void initData() {
        wvNews.loadUrl(url);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
