package com.malmstein.workshops.firebase.analytics;

import android.content.Context;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

import javax.inject.Inject;

public class MixpanelAnalytics {

    private MixpanelAPI mixpanel;

    @Inject
    public MixpanelAnalytics(Context context) {
        mixpanel = MixpanelAPI.getInstance(context, "8aa510bfc327878b9f75400f8f6c2968");
    }

    public void trackEvent(String eventName) {
        mixpanel.track(eventName);
    }


}
