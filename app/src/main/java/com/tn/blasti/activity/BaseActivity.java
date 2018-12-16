package com.tn.blasti.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdListener;
import com.tn.blasti.R;

import com.tn.blasti.data.constant.AppConstant;
import com.tn.blasti.utility.ActivityUtils;
import com.tn.blasti.utility.AdUtils;
import com.tn.blasti.utility.AppUtils;
import com.tn.blasti.utility.PermissionUtils;

/**
 * Created by amine 15/12/2018.
 */
public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Context mContext;
    private Activity mActivity;

    // global toolbar
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    private LinearLayout loadingView, noDataView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = BaseActivity.this;
        mContext = mActivity.getApplicationContext();

        // uncomment this line to disable ad
        //AdUtils.getInstance(mContext).disableBannerAd();
        //AdUtils.getInstance(mContext).disableInterstitialAd();

    }

    public void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    public void initDrawer(boolean enable) {

        // Initialize drawer view
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (enable) {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle
                    (this, mDrawerLayout, mToolbar, R.string.openDrawer, R.string.closeDrawer) {
                public void onDrawerClosed(View view) {
                    super.onDrawerClosed(view);

                }

                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);

                }
            };


            mDrawerLayout.setDrawerListener(toggle);
            toggle.syncState();

            mNavigationView = (NavigationView) findViewById(R.id.navigationView);
            mNavigationView.setItemIconTintList(null);
            getNavigationView().setNavigationItemSelectedListener(this);
        } else {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }


    public void setToolbarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    public void enableBackButton() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    /**
     * Access toolbar
     */
    public Toolbar getToolBar() {
        return mToolbar;
    }

    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    public NavigationView getNavigationView() {
        return mNavigationView;
    }

    public void initLoader() {
        loadingView = (LinearLayout) findViewById(R.id.loadingView);
        noDataView = (LinearLayout) findViewById(R.id.noDataView);
    }


    public void showLoader() {
        if (loadingView != null) {
            loadingView.setVisibility(View.VISIBLE);
        }

        if (noDataView != null) {
            noDataView.setVisibility(View.GONE);
        }
    }

    public void hideLoader() {
        if (loadingView != null) {
            loadingView.setVisibility(View.GONE);
        }
        if (noDataView != null) {
            noDataView.setVisibility(View.GONE);
        }
    }

    public void showEmptyView() {
        if (loadingView != null) {
            loadingView.setVisibility(View.GONE);
        }
        if (noDataView != null) {
            noDataView.setVisibility(View.VISIBLE);
        }
    }

    public void showAdThenActivity(final Class<?> activity, final boolean shouldFinish) {
        if (AdUtils.getInstance(mContext).showFullScreenAd()) {
           /* AdUtils.getInstance(mContext).getInterstitialAd().setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    ActivityUtils.getInstance().invokeActivity(mActivity, activity, shouldFinish);
                }
            });*/
            ActivityUtils.getInstance().invokeActivity(mActivity, activity, shouldFinish);
        } else {
            ActivityUtils.getInstance().invokeActivity(mActivity, activity, shouldFinish);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (PermissionUtils.isPermissionResultGranted(grantResults)) {
            if (requestCode == PermissionUtils.REQUEST_CALL) {
                AppUtils.makePhoneCall(mActivity, AppConstant.CALL_NUMBER);
            }
        } else {
            AppUtils.showToast(mActivity, getString(R.string.permission_not_granted));
        }

    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_home) {
            ActivityUtils.getInstance().invokeActivity(mActivity, MainActivity.class, true);
            finish();
        }  else if (id == R.id.action_categories) {
            showAdThenActivity(CategoryListActivity.class, false);
        } else if (id == R.id.action_favourite) {
            showAdThenActivity(FavouriteListActivity.class, false);
        }

        // social
          else if (id == R.id.action_facebook) {
            AppUtils.faceBookLink(mActivity);
        } else if (id == R.id.action_instagram) {
            AppUtils.instagramLink(mActivity);
        }

        // support
        else if (id == R.id.action_call) {
            AppUtils.makePhoneCall(mActivity, AppConstant.CALL_NUMBER);
        } else if (id == R.id.action_message) {
            AppUtils.sendSMS(mActivity, AppConstant.SMS_NUMBER, AppConstant.SMS_TEXT);
        } else if (id == R.id.action_messenger) {
            AppUtils.invokeMessenger(mActivity);
        } else if (id == R.id.action_email) {
            AppUtils.sendEmail(mActivity, AppConstant.EMAIL_ADDRESS, AppConstant.EMAIL_SUBJECT, AppConstant.EMAIL_BODY);
        }

        // others
        else if (id == R.id.action_share) {
            AppUtils.shareApp(mActivity);
        } else if (id == R.id.action_rate_app) {
            AppUtils.rateThisApp(mActivity); // this feature will only work after publish the app
        } else if (id == R.id.action_settings) {
            ActivityUtils.getInstance().invokeActivity(mActivity, SettingsActivity.class, false);
        } /*else if (id == R.id.terms_conditions) {
            ActivityUtils.getInstance().invokeCustomPostAndLink(mActivity, CustomLinkAndPageActivity.class, getResources().getString(R.string.terms), getResources().getString(R.string.terms_url), false);
        } else if (id == R.id.privacy_policy) {
            ActivityUtils.getInstance().invokeCustomPostAndLink(mActivity, CustomLinkAndPageActivity.class, getResources().getString(R.string.privacy), getResources().getString(R.string.privacy_url), false);
        } else if (id == R.id.faq) {
            ActivityUtils.getInstance().invokeCustomPostAndLink(mActivity, CustomLinkAndPageActivity.class, getResources().getString(R.string.faq), getResources().getString(R.string.faq_url), false);
        } */else if (id == R.id.action_exit) {
            AppUtils.appClosePrompt(mActivity);
        }

        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }

        return true;

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}