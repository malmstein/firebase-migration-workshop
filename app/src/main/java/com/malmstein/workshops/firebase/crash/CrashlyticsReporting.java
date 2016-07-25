package com.malmstein.workshops.firebase.crash;

import javax.inject.Inject;

public class CrashlyticsReporting implements CrashTracker {

    @Inject
    public CrashlyticsReporting() {
    }

    @Override
    public void crash(String log) {
        throw new RuntimeException(log);
    }
}
