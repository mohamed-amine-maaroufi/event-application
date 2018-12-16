package com.tn.blasti.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;

import com.google.android.gms.ads.AdView;
import com.tn.blasti.R;

import com.tn.blasti.data.constant.AppConstant;
import com.tn.blasti.utility.ActivityUtils;
import com.tn.blasti.utility.AdUtils;
import com.tn.blasti.utility.AppUtils;
import com.tn.blasti.utility.FilePicker;
import com.tn.blasti.utility.PermissionUtils;
import com.tn.blasti.webengine.WebEngine;
import com.tn.blasti.webengine.WebListener;

/**
 * Created by amine 15/12/2018.
 */

public class CustomLinkAndPageActivity extends BaseActivity {

    private Activity mActivity;
    private Context mContext;
    private String pageTitle, pageUrl;
    private WebView webView;
    private WebEngine webEngine;

    boolean isFromAppLink = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVar();
        initView();
        initAppLink();
        initFunctionality();
    }

    private void initVar() {
        mActivity = CustomLinkAndPageActivity.this;
        mContext = mActivity.getApplicationContext();

        Intent intent = getIntent();
        if (intent != null) {
            pageTitle = intent.getStringExtra(AppConstant.BUNDLE_KEY_TITLE);
            pageUrl = intent.getStringExtra(AppConstant.BUNDLE_KEY_URL);
        }
    }

    private void initView() {
        setContentView(R.layout.activity_custom_link);

        initWebEngine();

        initToolbar();
        setToolbarTitle(pageTitle);
        enableBackButton();
    }

    public void initWebEngine() {

        webView = (WebView) findViewById(R.id.web_view);

        webEngine = new WebEngine(webView, mActivity);
        webEngine.initWebView();


        webEngine.initListeners(new WebListener() {
            @Override
            public void onStart() {
                initLoader();
            }

            @Override
            public void onLoaded() {
                hideLoader();
            }

            @Override
            public void onProgress(int progress) {
            }

            @Override
            public void onNetworkError() {
                showEmptyView();
            }

            @Override
            public void onPageTitle(String title) {
            }
        });
    }


    private void initFunctionality() {

        showLoader();
        if (!isFromAppLink) {
            loadCustomLinkAndPage();
        } else {
            String postIdText="";
            for(int i=pageUrl.length()-2;i>=0;i--) {
                if(!Character.isDigit(pageUrl.charAt(i))) {
                    break;
                }
                postIdText+=pageUrl.charAt(i);
            }

            try {
                int postId = Integer.parseInt(new StringBuilder(postIdText).reverse().toString());
                ActivityUtils.getInstance().invokePostDetailsForAppLink(mActivity, PostDetailsActivity.class, postId, true, true);
            } catch (Exception e) {
                loadCustomLinkAndPage();
            }

        }

        // show banner ads
        AdUtils.getInstance(mContext).showBannerAd((AdView) findViewById(R.id.adView));
    }

    public void initAppLink() {
        Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();
        pageUrl = data.toString();
        isFromAppLink = true;
    }

    private void loadCustomLinkAndPage() {
        webEngine.loadPage(pageUrl);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (PermissionUtils.isPermissionResultGranted(grantResults)) {
            if (requestCode == PermissionUtils.REQUEST_WRITE_STORAGE_UPLOAD) {
                if (webEngine != null) {
                    webEngine.invokeImagePickerActivity();
                }
            } else if (requestCode == PermissionUtils.REQUEST_WRITE_STORAGE_DOWNLOAD) {
                if (webEngine != null) {
                    webEngine.downloadFile();
                }
            } else if (requestCode == PermissionUtils.REQUEST_CALL) {
                AppUtils.makePhoneCall(mActivity, AppConstant.CALL_NUMBER);
            }
        } else {
            AppUtils.showToast(mActivity, getString(R.string.permission_not_granted));
        }

    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (reqCode == WebEngine.KEY_FILE_PICKER) {
                String picturePath = FilePicker.getPickedFilePath(this, data);
                if (webEngine != null) {
                    webEngine.uploadFile(data, picturePath);
                } else {
                    AppUtils.showToast(mContext, getString(R.string.failed));
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            if (webEngine != null) {
                webEngine.cancelUpload();
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

}
