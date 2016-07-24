package com.malmstein.workshops.firebase.ui.presenter;

import com.malmstein.workshops.firebase.usecase.UserLogin;

import javax.inject.Inject;

public class SuperHeroLoginPresenter extends Presenter<SuperHeroLoginPresenter.View> {

    private final UserLogin userLogin;

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

    public interface View extends Presenter.View {

        void showWrongCredentials();

        void openSuperHeroesScreen();
    }

}
