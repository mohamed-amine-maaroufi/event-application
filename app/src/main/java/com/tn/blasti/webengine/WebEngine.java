package com.tn.blasti.webengine;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tn.blasti.R;
import com.tn.blasti.data.constant.AppConstant;
import com.tn.blasti.data.preference.AppPreference;
import com.tn.blasti.utility.FilePicker;
import com.tn.blasti.utility.PermissionUtils;

import java.io.File;

/**
 * Created by amine 15/12/2018.
 */

public class WebEngine {

    private WebView webView;
    private Activity mActivity;
    private Context mContext;
    private Fragment fragment;

    public static final int KEY_FILE_PICKER = 554;
    private static final String GOOGLE_DOCS_VIEWER = "https://docs.google.com/viewerng/viewer?url=";
    //"http://docs.google.com/gview?embedded=true&url=";
    // "http://drive.google.com/viewerng/viewer?embedded=true&url=";

    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mFilePathCallback;

    private WebListener webListener;
    private String downloadUrl;
    private VideoView videoView;
    private WebChromeClient.CustomViewCallback videoViewCallback;

    public WebEngine(WebView webView, Activity activity) {
        this.webView = webView;
        this.mActivity = activity;
        this.mContext = mActivity.getApplicationContext();
        videoView = VideoView.getInstance();
    }

    public WebEngine(WebView webView, Activity activity, Fragment fragment) {
        this.webView = webView;
        this.mActivity = activity;
        this.fragment = fragment;
        this.mContext = mActivity.getApplicationContext();
        videoView = VideoView.getInstance();
    }


    public void initWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setAppCacheMaxSize(AppConstant.SITE_CACHE_SIZE);
        webView.getSettings().setAppCachePath(mContext.getCacheDir().getAbsolutePath());
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT); // load online by default
        //webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        //webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        if (!isNetworkAvailable(mContext)) {
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }


        /**
         * set parameters based on settings
         */
        /*if (AppPreference.getInstance(mContext).isZoomEnabled()) {
            webView.getSettings().setBuiltInZoomControls(true);
        } else {
            webView.getSettings().setBuiltInZoomControls(false);
        }

        boolean cookie = AppPreference.getInstance(mContext).isCookieEnabled();
        CookieManager.getInstance().setAcceptCookie(cookie);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, cookie);
        }
        webView.getSettings().setSavePassword(cookie);
        webView.getSettings().setSaveFormData(cookie);
        */

        if (AppPreference.getInstance(mContext).getTextSize().equals(mContext.getResources().getString(R.string.small_text))) {
            webView.getSettings().setTextSize(WebSettings.TextSize.SMALLER);
        } else if (AppPreference.getInstance(mContext).getTextSize().equals(mContext.getResources().getString(R.string.default_text))) {
            webView.getSettings().setTextSize(WebSettings.TextSize.NORMAL);
        } else if (AppPreference.getInstance(mContext).getTextSize().equals(mContext.getResources().getString(R.string.large_text))) {
            webView.getSettings().setTextSize(WebSettings.TextSize.LARGER);
        }

    }

    public void initListeners(final WebListener webListener) {

        this.webListener = webListener;

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                webListener.onProgress(newProgress);
            }

            @Override
            public boolean onShowFileChooser(WebView view, ValueCallback<Uri[]> filePath, WebChromeClient.FileChooserParams fileChooserParams) {

                if (mFilePathCallback != null) {
                    mFilePathCallback.onReceiveValue(null);
                }
                mFilePathCallback = filePath;
                invokeImagePickerActivity();
                return true;
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                        String acceptType,
                                        String capture) {
                mUploadMessage = uploadMsg;
                invokeImagePickerActivity();
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                mUploadMessage = uploadMsg;
                invokeImagePickerActivity();
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                webListener.onPageTitle(webView.getTitle());
            }


            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                super.onShowCustomView(view, callback);
                videoViewCallback = callback;
                videoView.show(mActivity);
                videoView.setVideoLayout(view);
            }

            @Override
            public void onHideCustomView() {
                super.onHideCustomView();
                videoView.dismiss();
                videoViewCallback.onCustomViewHidden();
            }


        });

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String webUrl) {

                loadPage(webUrl);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                webListener.onStart();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                webListener.onLoaded();
            }

        });

        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                downloadUrl = url;
                downloadFile();

            }
        });

    }

    public void loadPage(String webUrl) {
        if (isNetworkAvailable(mContext)) {

            if (webUrl.startsWith("tel:") || webUrl.startsWith("sms:") || webUrl.startsWith("smsto:")
                    || webUrl.startsWith("mms:") || webUrl.startsWith("mmsto:")
                    || webUrl.startsWith("mailto:") /*|| webUrl.contains("youtube.com")*/
                    || webUrl.contains("geo:")) {
                invokeNativeApp(webUrl);
            } else if (webUrl.contains("?target=blank")) {
                invokeNativeApp(webUrl.replace("?target=blank", ""));
            } else if (webUrl.endsWith(".doc") || webUrl.endsWith(".docx") || webUrl.endsWith(".xls")
                    || webUrl.endsWith(".xlsx") || webUrl.endsWith(".pptx") || webUrl.endsWith(".pdf")) {
                webView.loadUrl(GOOGLE_DOCS_VIEWER + webUrl);
                webView.getSettings().setBuiltInZoomControls(true);
            } else {
                webView.loadUrl(webUrl);
            }

        } else {
            webListener.onNetworkError();
        }
    }

    public void loadHtml(String htmlString) {
        if (isNetworkAvailable(mContext)) {

            if (htmlString.startsWith("tel:") || htmlString.startsWith("sms:") || htmlString.startsWith("smsto:")
                    || htmlString.startsWith("mms:") || htmlString.startsWith("mmsto:")
                    || htmlString.startsWith("mailto:") /*|| htmlString.contains("youtube.com")*/
                    || htmlString.contains("geo:")) {
                invokeNativeApp(htmlString);
            } else if (htmlString.contains("?target=blank")) {
                invokeNativeApp(htmlString.replace("?target=blank", ""));
            } else if (htmlString.endsWith(".doc") || htmlString.endsWith(".docx") || htmlString.endsWith(".xls")
                    || htmlString.endsWith(".xlsx") || htmlString.endsWith(".pptx") || htmlString.endsWith(".pdf")) {
                webView.loadUrl(GOOGLE_DOCS_VIEWER + htmlString);
                webView.getSettings().setBuiltInZoomControls(true);
            } else {
                webView.loadData(htmlString, "text/html; charset=utf-8", "UTF-8");
            }

        } else {
            webListener.onNetworkError();
        }
    }


    public void reloadPage() {
        webView.reload();
    }

    public boolean hasHistory() {
        return webView.canGoBack();
    }

    public void loadPreviousPage() {
        webView.goBack();
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public String getCurrentUrl() {
        return webView.getUrl();
    }

    private void invokeNativeApp(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        mActivity.startActivity(intent);
    }

    public void invokeImagePickerActivity() {
        if (PermissionUtils.isPermissionGranted(mActivity, PermissionUtils.SD_WRITE_PERMISSIONS, PermissionUtils.REQUEST_WRITE_STORAGE_UPLOAD)) {
            Intent chooseImageIntent = FilePicker.getPickFileIntent(mActivity);
            if (fragment == null) {
                mActivity.startActivityForResult(chooseImageIntent, KEY_FILE_PICKER);
            } else {
                fragment.startActivityForResult(chooseImageIntent, KEY_FILE_PICKER);
            }
        }
    }

    public void uploadFile(Intent data, String filePath) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Uri[] results = null;
            if (filePath != null) {
                results = new Uri[]{Uri.fromFile(new File(filePath))};//Uri.parse(filePath)};
            }

            if (results == null) {
                String dataString = data.getDataString();
                if (dataString != null) {
                    results = new Uri[]{Uri.parse(dataString)};
                }
            }


            if (mFilePathCallback != null) {
                mFilePathCallback.onReceiveValue(results);
                mFilePathCallback = null;
            }
        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            Uri result = data == null ? Uri.fromFile(new File(filePath)) : data.getData();
            if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }

        }
    }

    public void cancelUpload() {
        if (mFilePathCallback != null) {
            mFilePathCallback.onReceiveValue(null);
        }
        mFilePathCallback = null;
    }

    public void downloadFile() {
        if (PermissionUtils.isPermissionGranted(mActivity, PermissionUtils.SD_WRITE_PERMISSIONS, PermissionUtils.REQUEST_WRITE_STORAGE_DOWNLOAD)) {
            DownloadManager.Request request = new DownloadManager.Request(
                    Uri.parse(downloadUrl));

            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Downloading file...");
            DownloadManager dm = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
            dm.enqueue(request);
        }
    }

}
