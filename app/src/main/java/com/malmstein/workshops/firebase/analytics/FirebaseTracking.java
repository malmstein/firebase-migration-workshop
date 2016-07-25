package com.malmstein.workshops.firebase.analytics;

import android.content.Context;

import com.google.firebase.analytics.FirebaseAnalytics;

import javax.inject.Inject;

public class FirebaseTracking implements AnalyticsTracking{

    private FirebaseAnalytics firebaseAnalytics;

    @Inject
    public FirebaseTracking(Context context) {
        this.firebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    @Override
    public void trackEvent(String eventName) {
        firebaseAnalytics.logEvent(eventName, null);
    }

}
