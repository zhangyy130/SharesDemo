package com.learnice.sharesdemo;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.learnice.sharesdemo.Adapter.MViewPagerAdapter;
import com.learnice.sharesdemo.Database.DbManager;
import com.learnice.sharesdemo.Fragment.Subscribe;
import com.learnice.sharesdemo.Fragment.Trend;
import com.learnice.sharesdemo.Http.MyURL;
import com.learnice.sharesdemo.SharedData.AboutLogin;
import com.learnice.sharesdemo.SharedData.AboutPatternLock;
import com.learnice.sharesdemo.SharedData.AboutUser;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements Trend.Refresh {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.appbar_layout)
    AppBarLayout appbarLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.navigation)
    NavigationView navigation;
    @BindView(R.id.drawerlayout)
    DrawerLayout drawerlayout;
    ActionBar actionBar;
    @BindView(R.id.float_action_button_menu)
    FloatingActionsMenu floatActionButtonMenu;
    @BindView(R.id.sh)
    FloatingActionButton sh;
    @BindView(R.id.sz)
    FloatingActionButton sz;
    @BindView(R.id.hk)
    FloatingActionButton hk;
    @BindView(R.id.us)
    FloatingActionButton us;

    SearchView searchView;
    netReceiver netReceiver;
    AlertDialog alertDialog;
    boolean isLoadTablayotu = true;
    @BindView(R.id.details_fab_bg)
    RelativeLayout detailsFabBg;
    //ListView listView;
    //List<String> tipData;
    //ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);
        setupToolbar();
        setAlertDialog();
        isNoNetwork();
        View headerview = navigation.getHeaderView(0);
        TextView textView = (TextView) headerview.findViewById(R.id.navigation_header_name);
        textView.setText(new AboutUser(this).readName());
        testFloatButton();
        if (AboutPatternLock.readPatternBool(this)) {
            navigation.getMenu().getItem(0).setTitle("手势密码          开");
        } else if (!AboutPatternLock.readPatternBool(this)) {
            navigation.getMenu().getItem(0).setTitle("手势密码          关");
        }
        netReceiver = new netReceiver();
        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(netReceiver, intentFilter);
    }

    public void setupSplash() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setupTablelayout();
            }
        }, 500);
    }

    //为网络提醒设置一个全局的alertdialog
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
            if (isLoadTablayotu) {
                setupSplash();
            }
            if (alertDialog.isShowing()) {

                alertDialog.dismiss();
            }
        } else {
            alertDialog.show();
        }
    }

    public void setupToolbar() {
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("首页");
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerlayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerToggle.syncState();
        drawerlayout.setDrawerListener(drawerToggle);
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item1:
                        if (AboutPatternLock.readPatternBool(MainActivity.this)) {
                            Intent intent = new Intent(MainActivity.this, ConfirmPatternView.class);
                            intent.putExtra("have", "have");
                            startActivityForResult(intent, 1);
                        } else if (!AboutPatternLock.readPatternBool(MainActivity.this)) {
                            Intent intent = new Intent(MainActivity.this, SetPatternView.class);
                            startActivityForResult(intent, 2);
                        }
                        break;
                    case R.id.item2:
                        startActivity(new Intent(MainActivity.this, ChangePassActivity.class));
                        break;
                    case R.id.item4:
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("新版本正在开发中").setPositiveButton("好", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                        break;
                    case R.id.item5:
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                        builder1.setMessage("微博：learnicehe.cn").setPositiveButton("好", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                        break;
                    case R.id.item6:
                        Intent intent6 = new Intent(MainActivity.this, AboutMeActivity.class);
                        intent6.putExtra("url", MyURL.MY_SERVWE_ME);
                        startActivity(intent6);
                        break;
                    case R.id.item7:
                        new AlertDialog.Builder(MainActivity.this)
                                .setMessage("真的要注销?")
                                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        new DbManager(MainActivity.this).clearData();
                                        AboutPatternLock.setPatternBool(MainActivity.this, false);
                                        new AboutLogin(MainActivity.this).clearLoginSuccess();
                                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                        finish();
                                        Toast.makeText(MainActivity.this, "注销成功", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();

                        break;
                }
                return false;
            }
        });
    }

    public void setupTablelayout() {
        isLoadTablayotu = false;
        MViewPagerAdapter mviewPagerAdapter = new MViewPagerAdapter(this, getSupportFragmentManager());
        viewpager.setAdapter(mviewPagerAdapter);
        tablayout.setupWithViewPager(viewpager);
        //tablayout.setTabsFromPagerAdapter(mviewPagerAdapter);
        int[] image = new int[]{R.mipmap.ic_home_white_24dp, R.mipmap.ic_subscriptions_black_24dp,
                R.mipmap.ic_trending_up_black_24dp};
        for (int i = 0; i < tablayout.getTabCount(); i++) {
            tablayout.getTabAt(i).setIcon(image[i]);
        }
        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        tablayout.getTabAt(0).setIcon(R.mipmap.ic_home_white_24dp);
                        tablayout.getTabAt(1).setIcon(R.mipmap.ic_subscriptions_black_24dp);
                        tablayout.getTabAt(2).setIcon(R.mipmap.ic_trending_up_black_24dp);
                        actionBar.setTitle("首页");
                        viewpager.setCurrentItem(0);
                        floatActionButtonMenu.setVisibility(View.GONE);
                        break;
                    case 1:
                        tablayout.getTabAt(1).setIcon(R.mipmap.ic_subscriptions_white_24dp);
                        tablayout.getTabAt(0).setIcon(R.mipmap.ic_home_black_24dp);
                        tablayout.getTabAt(2).setIcon(R.mipmap.ic_trending_up_black_24dp);
                        actionBar.setTitle("自选股");
                        viewpager.setCurrentItem(1);
                        floatActionButtonMenu.setVisibility(View.GONE);
                        break;
                    case 2:
                        tablayout.getTabAt(2).setIcon(R.mipmap.ic_trending_up_white_24dp);
                        tablayout.getTabAt(0).setIcon(R.mipmap.ic_home_black_24dp);
                        tablayout.getTabAt(1).setIcon(R.mipmap.ic_subscriptions_black_24dp);
                        actionBar.setTitle("行情");
                        viewpager.setCurrentItem(2);
                        floatActionButtonMenu.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.toolbar_search);
        searchView = (SearchView) menuItem.getActionView();
        final SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);
        searchView.setQueryHint("请输入股票代号");
        //---------------
//        getMenuInflater().inflate(R.menu.main_menu,menu);
//        MenuItem item = menu.findItem(R.id.action_search);
//        item.setVisible(true);
//        viewSearch.setMenuItem(item);
        //mSearchMenuItem = item;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerlayout.openDrawer(GravityCompat.START);
                break;
            case R.id.toolbar_search:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void testFloatButton() {
        sh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Trend trend = (Trend) getSupportFragmentManager().findFragmentById(R.id.viewpager);
                trend.tabSh();
                floatActionButtonMenu.collapse();
            }
        });
        sz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Trend trend = (Trend) getSupportFragmentManager().findFragmentById(R.id.viewpager);
                trend.tabSz();
                floatActionButtonMenu.collapse();
            }
        });
        hk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Trend trend = (Trend) getSupportFragmentManager().findFragmentById(R.id.viewpager);
                trend.tabHk();
                floatActionButtonMenu.collapse();
            }
        });
        us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Trend trend = (Trend) getSupportFragmentManager().findFragmentById(R.id.viewpager);
                trend.tabUs();
                floatActionButtonMenu.collapse();
            }
        });
    }

    @Override
    public void refresh() {
        Subscribe subscribe = (Subscribe) getSupportFragmentManager().findFragmentById(R.id.viewpager);
        subscribe.onRefresh();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    AboutPatternLock.setPatternBool(this, false);
                    navigation.getMenu().getItem(0).setTitle("手势密码          关");
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    AboutPatternLock.setPatternBool(this, true);
                    navigation.getMenu().getItem(0).setTitle("手势密码          开");
                }
                break;
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
