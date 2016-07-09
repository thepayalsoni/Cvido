package com.cvido;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvido.application.CvidoApplication;
import com.cvido.fragment.FirstFragment;
import com.cvido.fragment.MessageFragment;
import com.cvido.fragment.SecondFragment;
import com.cvido.fragment.ThirdFragment;
import com.cvido.model.Register;
import com.nostra13.universalimageloader.core.ImageLoader;

public class HomeActivity extends AppCompatActivity {
    private DrawerLayout mDrawer;
    public Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    Register loginData;
    ImageLoader imgLoader;
    View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imgLoader = ImageLoader.getInstance();
        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        headerView = nvDrawer.getHeaderView(0);

        loginData = CvidoApplication.getAppliation().getRegister();
        nvDrawer.getMenu().clear();

        if (checkForJobSeeker()) {
            nvDrawer.inflateMenu(R.menu.drawer_view_jobseeker);
        } else {
            nvDrawer.inflateMenu(R.menu.drawer_view_employer);
        }

        drawerToggle = setupDrawerToggle();
        // Setup drawer view
        setupDrawerContent(nvDrawer);


       Class fragmentClass = ThirdFragment.class;
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView

    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        ((TextView) headerView.findViewById(R.id.lblProfileName)).setText(CvidoApplication.getAppliation().getRegister().getData().getUsername());
        ((TextView) headerView.findViewById(R.id.lblProfileEmail)).setText(CvidoApplication.getAppliation().getRegister().getData().getEmail());

        imgLoader.displayImage(CvidoApplication.getAppliation().getRegister().getData().getAvatar(), (ImageView) headerView.findViewById(R.id.imgProfileUser));

        if (CvidoApplication.getAppliation().getRegister().getData().getRoleId() == 2) {
            ((LinearLayout) headerView.findViewById(R.id.llCompanyCredit)).setVisibility(View.GONE);
        } else {
            ((LinearLayout) headerView.findViewById(R.id.llCompanyCredit)).setVisibility(View.VISIBLE);
        }

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass = FirstFragment.class;
        switch (menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragmentClass = ThirdFragment.class;
                break;
            case R.id.nav_second_fragment:
                fragmentClass = SecondFragment.class;
                break;
            case R.id.nav_message_fragment:
                fragmentClass = MessageFragment.class;
                break;
            case R.id.nav_logout_user:
                CvidoApplication.getAppliation().logout(HomeActivity.this);
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            default:
                fragmentClass = FirstFragment.class;
                break;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        //setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE! Make sure to override the method with only a single `Bundle` argument
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }


    private boolean checkForJobSeeker() {
        if (loginData == null)
            return false;
        return loginData.getData().getRoleId() == 2 ? true : false;
    }
}