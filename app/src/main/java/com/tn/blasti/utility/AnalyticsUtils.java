package com.tn.blasti.utility;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by amine 15/12/2018.
 */
public class AnalyticsUtils {

    private FirebaseAnalytics mFirebaseAnalytics;
    private static AnalyticsUtils analyticsUtils;

    public static AnalyticsUtils getAnalyticsUtils(Context context) {
        if (analyticsUtils == null) {
            analyticsUtils = new AnalyticsUtils(context);
        }
        return analyticsUtils;
    }

    private AnalyticsUtils(Context context) {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    public void trackEvent(String activityName) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, activityName);
        mFirebaseAnalytics.logEvent("PAGE_VISIT", bundle);
    }

}
