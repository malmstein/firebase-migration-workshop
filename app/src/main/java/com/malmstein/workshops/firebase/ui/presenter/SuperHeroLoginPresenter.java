package com.malmstein.workshops.firebase.ui.presenter;

import com.malmstein.workshops.firebase.push.MixpanelPush;
import com.malmstein.workshops.firebase.usecase.UserLogin;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterSession;

import javax.inject.Inject;

public class SuperHeroLoginPresenter extends Presenter<SuperHeroLoginPresenter.View> {

    private final UserLogin userLogin;
    @Inject
    MixpanelPush mixpanelPush;

    @Inject
    public SuperHeroLoginPresenter(UserLogin userLogin) {
        this.userLogin = userLogin;
    }

    public void login(String username, String password) {
        userLogin.login(username, password, new UserLogin.Callback() {
            @Override
            public void onLoginSuccessful() {
                getView().openSuperHeroesScreen();
            }

            @Override
            public void onLoginFailed() {
                getView().showWrongCredentials();
            }
        });
    }

    public void notifyLogin(Result<TwitterSession> result) {
        mixpanelPush.subscribe(result.data.getUserName());
        getView().openSuperHeroesScreen();
    }

    public interface View extends Presenter.View {

        void showWrongCredentials();

        void openSuperHeroesScreen();
    }

}
