package com.tn.blasti.webengine;

/**
 * Created by amine 15/12/2018.
 */
public interface WebListener {

    public void onStart();

    public void onLoaded();

    public void onProgress(int progress);

    public void onNetworkError();

    public void onPageTitle(String title);
}
