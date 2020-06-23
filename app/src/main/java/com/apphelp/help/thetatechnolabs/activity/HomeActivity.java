package com.apphelp.help.thetatechnolabs.activity;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.apphelp.help.thetatechnolabs.R;
import com.apphelp.help.thetatechnolabs.controller.Global;
import com.apphelp.help.thetatechnolabs.fragment.Home_Fragment;
import com.apphelp.help.thetatechnolabs.fragment.Map_Fragment;
import com.apphelp.help.thetatechnolabs.fragment.Profile_Fragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    DrawerLayout drawer;
    TextView txt_email, txt_username,txt_mobile;
    SharedPreferences sharedPreferences;
    String EMAIL_ID, USER_NAME, MOBILE_NO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sharedPreferences = getSharedPreferences(Global.THETA_TECHNOLABS, MODE_PRIVATE);
        EMAIL_ID = sharedPreferences.getString(Global.L_EMAIL, "");
        USER_NAME = sharedPreferences.getString(Global.USER_NAME,"");
        MOBILE_NO = sharedPreferences.getString(Global.MOBILE,"");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Theta Technolabs");
        toolbar.setTitleTextColor(Color.WHITE);

        viewPager = (ViewPager) findViewById(R.id.Home_Page_View);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.Home_tabs);
        tabLayout.setupWithViewPager(viewPager);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        txt_username = (TextView) header.findViewById(R.id.txt_username);
        txt_mobile = (TextView) header.findViewById(R.id.txt_mobile);
        txt_email = (TextView) header.findViewById(R.id.txt_email);
        navigationView.setNavigationItemSelectedListener(this);

        if (USER_NAME.equalsIgnoreCase("") || USER_NAME.equalsIgnoreCase("null") || USER_NAME.equalsIgnoreCase(null)) {

            txt_username.setVisibility(View.GONE);

        } else {

            txt_username.setVisibility(View.VISIBLE);
            txt_username.setText(USER_NAME);
        }

        if (EMAIL_ID.equalsIgnoreCase("") || EMAIL_ID.equalsIgnoreCase("null") || EMAIL_ID.equalsIgnoreCase(null)) {

            txt_email.setVisibility(View.GONE);

        } else {

            txt_email.setVisibility(View.VISIBLE);
            txt_email.setText(EMAIL_ID);
        }

        if (MOBILE_NO.equalsIgnoreCase("") || MOBILE_NO.equalsIgnoreCase("null") || MOBILE_NO.equalsIgnoreCase(null)) {

            txt_mobile.setVisibility(View.GONE);

        } else {

            txt_mobile.setVisibility(View.VISIBLE);
            txt_mobile.setText(MOBILE_NO);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_RProfile) {

        } else if (id == R.id.nav_share) {

            Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, Global.PLAY_STORE);
            emailIntent.setType("text/plain");
            startActivity(Intent.createChooser(emailIntent, "Share Via..."));

        } else if (id == R.id.nav_RLogout) {

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(Global.L_EMAIL);
            editor.remove(Global.L_PASSWORD);
            editor.apply();
            editor.clear();
            editor.commit();
            CookieSyncManager.createInstance(HomeActivity.this);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeSessionCookie();

            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            HomeActivity.this.finish();


        } else if (id == R.id.nav_rate) {

            final String appPackageName = getPackageName();
            try {

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));

            } catch (android.content.ActivityNotFoundException anfe) {

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }

        }
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            super.onBackPressed();
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Home_Fragment(), "Home");
        adapter.addFragment(new Map_Fragment(), "Map");
        adapter.addFragment(new Profile_Fragment(), "Profile");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(androidx.fragment.app.FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
