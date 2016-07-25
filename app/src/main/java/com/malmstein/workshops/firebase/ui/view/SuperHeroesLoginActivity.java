package com.malmstein.workshops.firebase.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;

import com.malmstein.workshops.firebase.R;
import com.malmstein.workshops.firebase.SuperHeroesApplication;
import com.malmstein.workshops.firebase.crash.CrashlyticsReporting;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import javax.inject.Inject;

import butterknife.Bind;

public class SuperHeroesLoginActivity extends BaseActivity {

    // Inject your Analytics tracking here

    @Inject
    CrashlyticsReporting crashlyticsReporting;

    @Bind(R.id.login_button)
    TwitterLoginButton loginButton;

    @Override
    public int getLayoutId() {
        return R.layout.super_heroes_login_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeDagger();
        setupLogin();
    }

    private void setupLogin() {
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // This is a good place to track a successful login
                SuperHeroesActivity.open(SuperHeroesLoginActivity.this);
            }

            @Override
            public void failure(TwitterException exception) {
                // This is a good place to track a failed login
                Snackbar.make(loginButton, exception.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
                crashlyticsReporting.crash();
            }
        });
    }

    private void initializeDagger() {
        SuperHeroesApplication app = (SuperHeroesApplication) getApplication();
        app.getMainComponent().inject(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

}
