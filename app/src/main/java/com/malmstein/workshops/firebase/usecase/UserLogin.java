package com.malmstein.workshops.firebase.usecase;

import android.os.Handler;
import android.os.Looper;

import com.malmstein.workshops.firebase.model.SuperHeroesRepository;

import javax.inject.Inject;

public class UserLogin {

    private final SuperHeroesRepository repository;

    @Inject
    public UserLogin(SuperHeroesRepository repository) {
        this.repository = repository;
    }

    public void login(final String username, final String password, final Callback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                checkCredentials(username, password, callback);
            }
        }).start();
    }

    private void checkCredentials(final String username, String password, final Callback callback) {
        final boolean isLoginValid = true;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (isLoginValid) {
                    callback.onLoginSuccessful();
                } else {
                    callback.onLoginFailed();
                }
            }
        });
    }

    public interface Callback {

        void onLoginSuccessful();

        void onLoginFailed();
    }

}
