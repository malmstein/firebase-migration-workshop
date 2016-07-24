package com.malmstein.workshops.firebase.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;

import com.malmstein.workshops.firebase.R;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import butterknife.Bind;

public class SuperHeroesLoginActivity extends BaseActivity {

    @Bind(R.id.login_button)
    TwitterLoginButton loginButton;

    @Override
    public int getLayoutId() {
        return R.layout.super_heroes_login_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupLogin();
    }

    private void setupLogin() {
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                SuperHeroesActivity.open(SuperHeroesLoginActivity.this);
            }

            @Override
            public void failure(TwitterException exception) {
                Snackbar.make(loginButton, exception.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

}
