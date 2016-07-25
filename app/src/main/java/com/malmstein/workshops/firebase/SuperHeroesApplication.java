/*
 * Copyright (C) 2015 Karumi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.malmstein.workshops.firebase;

import android.app.Application;
import android.support.annotation.VisibleForTesting;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.malmstein.workshops.firebase.di.ApplicationModule;
import com.malmstein.workshops.firebase.di.DaggerMainComponent;
import com.malmstein.workshops.firebase.di.MainComponent;
import com.malmstein.workshops.firebase.di.MainModule;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;

public class SuperHeroesApplication extends Application {

    private MainComponent mainComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mainComponent = DaggerMainComponent.builder()
                .applicationModule(new ApplicationModule((getApplicationContext())))
                .mainModule(new MainModule()).build();

        setupFabric();
    }

    private void setupFabric() {
        Crashlytics crashlyticsKit = new Crashlytics.Builder()
                .core(new CrashlyticsCore.Builder().build())
                .build();

        TwitterAuthConfig authConfig = new TwitterAuthConfig("SNbfv0VMO6LMyChpTZtdC58uc", "LmGPJBRKKd9G2UmMdJBLlXFvgp1dMuN7Fw9o9CjlzctbTSEdaQ");
        Twitter twitter = new Twitter(authConfig);
        Kit[] kits = new Kit[]{crashlyticsKit, twitter};

        Fabric.with(this, kits);
    }

    public MainComponent getMainComponent() {
        return mainComponent;
    }

    @VisibleForTesting
    public void setComponent(MainComponent mainComponent) {
        this.mainComponent = mainComponent;
    }
}
