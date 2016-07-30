package com.malmstein.workshops.firebase.push;


import android.content.Context;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

import javax.inject.Inject;

public class MixpanelPush {

    private final MixpanelAPI mixpanel;

    @Inject
    public MixpanelPush(Context context) {
        mixpanel = MixpanelAPI.getInstance(context, "8aa510bfc327878b9f75400f8f6c2968");
    }

    public void subscribe(String username) {
        // Always identify before initializing push notifications
        mixpanel.getPeople().identify(username);

        // Sets up GCM registration and handling of GCM messages
        // for Google API project number 717871474771
        mixpanel.getPeople().initPushHandling("421720080319");
    }

}
