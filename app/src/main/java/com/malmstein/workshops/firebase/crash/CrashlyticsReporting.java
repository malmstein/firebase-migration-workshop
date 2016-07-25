package com.malmstein.workshops.firebase.crash;

import javax.inject.Inject;

public class CrashlyticsReporting {

    @Inject
    public CrashlyticsReporting() {
    }

    public void crash() {
        throw new RuntimeException();
    }
}
