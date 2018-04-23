package com.pthomasdesigns.myhealth;

/**
 * Created by tthomas on 4/14/2018.
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.pthomasdesigns.myhealth.rest.model.UserInfo;
import com.pthomasdesigns.myhealth.rest.service.RestClient;

import org.parceler.Parcels;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyHealth:MainActivity";
    public static String PACKAGE_NAME = BuildConfig.APPLICATION_ID;
    public static final String FRAGMENT_ID = PACKAGE_NAME + ".FragmentId";
    private DrawerLayout mDrawerLayout;
    private TextView mNavigationViewUserId;
    private NavigationView mNavigationView;
    private ActionBar mActionBar;
    private static UserInfo mUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        mActionBar.setTitle(R.string.app_name);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);
        mNavigationViewUserId = mNavigationView.getHeaderView(0).findViewById(R.id.nav_view_userid);

        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        startFragment(item.getItemId());
                        item.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                }
        );

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            Parcelable pExtras = getIntent().getParcelableExtra(UserInfo.TAG);
            if (pExtras != null) {
                mUserInfo = Parcels.unwrap(pExtras);
                mNavigationViewUserId.setText(mUserInfo.getName());
            }
            if (extras != null && extras.getInt(FRAGMENT_ID) != 0) {
                startFragment(extras.getInt(FRAGMENT_ID));
            } else {
                startMainFragment();
            }
        } else {
            mUserInfo = Parcels.unwrap(savedInstanceState.getParcelable(UserInfo.TAG));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startFragment(int itemId) {
        switch(itemId) {
            case R.id.nav_visits:
                startVisitsFragment();
                break;

            case R.id.nav_bills:
                startBillsFragment();
                break;

            case R.id.nav_messages:
                startMessagesFragment();
                break;

            case R.id.logout:
                finish();
                break;

            default:
                startMainFragment();
                break;
        }
    }

    @Override
    public void onBackPressed () {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(MainFragment.TAG);
        if (fragment != null && fragment.isVisible()) {
            finish();
        } else {
            startMainFragmentAnimation();
        }

    }

    private void startMainFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        MainFragment fragment = new MainFragment();
        fragment.setUserInfo(mUserInfo);
        transaction.replace(R.id.fragment_container, fragment, fragment.TAG);
        transaction.commit();

    }

    private void startMainFragmentAnimation() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        MainFragment fragment = new MainFragment();
        fragment.setUserInfo(mUserInfo);
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right );
        transaction.replace(R.id.fragment_container, fragment, fragment.TAG);
        transaction.commit();
        mActionBar.setTitle(R.string.app_name);
    }

    private void startVisitsFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        VisitsFragment fragment = new VisitsFragment();
        fragment.setUserId(mUserInfo.getId());
        transaction.replace(R.id.fragment_container, fragment, fragment.TAG);
        transaction.commit();
    }

    private void startBillsFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        BillsFragment fragment = new BillsFragment();
        transaction.replace(R.id.fragment_container, fragment, fragment.TAG);
        transaction.commit();
    }

    private void startMessagesFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        MessagesFragment fragment = new MessagesFragment();
        fragment.setUserInfo(mUserInfo);
        transaction.replace(R.id.fragment_container, fragment, fragment.TAG);
        transaction.commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(UserInfo.TAG, Parcels.wrap(mUserInfo));
        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }
}
