package com.tn.blasti.activity;
/**
 * Created by amine 15/12/2018.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.tn.blasti.R;
import com.tn.blasti.data.constant.AppConstant;
import com.tn.blasti.utility.ActivityUtils;
import com.tn.blasti.utility.AdUtils;

public class NotificationDetailsActivity extends BaseActivity {

    private Context mContext;
    private Activity mActivity;

    private TextView titleView, messageView;
    private Button linkButton;
    private String title, message, postId;
    private boolean fromPush = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = NotificationDetailsActivity.this;
        mContext = mActivity.getApplicationContext();
        initView();
        initVeritable();
        initFunctionality();
        initListeners();
    }

    private void initVeritable() {
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            title = extras.getString(AppConstant.BUNDLE_KEY_TITLE);
            message = extras.getString(AppConstant.BUNDLE_KEY_MESSAGE);
            postId = extras.getString(AppConstant.BUNDLE_KEY_POST_ID);
            if (extras.containsKey(AppConstant.BUNDLE_FROM_PUSH)) {
                fromPush = extras.getBoolean(AppConstant.BUNDLE_FROM_PUSH);
            }
        }
    }

    private void initView() {
        setContentView(R.layout.activity_notification_details);

        titleView = (TextView) findViewById(R.id.title);
        messageView = (TextView) findViewById(R.id.message);
        linkButton = (Button) findViewById(R.id.linkButton);

        initToolbar();
        setToolbarTitle(getString(R.string.notifications));
        enableBackButton();
    }


    private void initFunctionality() {

        titleView.setText(title);
        messageView.setText(message);

        if (postId != null && !postId.isEmpty()) {
            linkButton.setEnabled(true);
        } else {
            linkButton.setEnabled(false);
        }

        AdUtils.getInstance(mContext).showBannerAd((AdView) findViewById(R.id.adView));
    }

    private void initListeners() {
        linkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = -1;
                try {
                    id = Integer.parseInt(postId);
                } catch (Exception e) {
                }
                ActivityUtils.getInstance().invokePostDetails(mActivity, PostDetailsActivity.class, id, false);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                goToHome();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onBackPressed() {
        goToHome();
    }

    private void goToHome(){
        if(fromPush) {
            Intent intent = new Intent(NotificationDetailsActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            finish();
        }
    }
}
