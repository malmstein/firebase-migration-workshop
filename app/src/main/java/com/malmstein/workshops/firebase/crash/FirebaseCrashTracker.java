package com.malmstein.workshops.firebase.crash;

import com.google.firebase.crash.FirebaseCrash;

import javax.inject.Inject;

public class FirebaseCrashTracker implements CrashTracker {

    @Inject
    public FirebaseCrashTracker() {

    }

    @Override
    public void crash(String log) {
        FirebaseCrash.log(log);
    }
}
