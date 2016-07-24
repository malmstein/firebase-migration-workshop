package com.malmstein.workshops.firebase.ui.presenter;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.TwitterAuthProvider;
import com.malmstein.workshops.firebase.analytics.AnalyticsTracking;
import com.twitter.sdk.android.core.TwitterSession;

import javax.inject.Inject;

public class SuperHeroLoginPresenter extends Presenter<SuperHeroLoginPresenter.View> {

    @Inject
    AnalyticsTracking analyticsTracking;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Inject
    public SuperHeroLoginPresenter() {
        this.firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                } else {
                    // User is signed out
                }
            }
        };
    }

    public void handleTwitterSession(TwitterSession session) {
        AuthCredential credential = TwitterAuthProvider.getCredential(
                session.getAuthToken().token,
                session.getAuthToken().secret);

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            analyticsTracking.trackEvent("login succesful");
                            getView().openSuperHeroesScreen();
                        } else {
                            analyticsTracking.trackEvent("login failure");
                            getView().showWrongCredentials();
                        }
                    }
                });
    }

    public void onStart() {
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    public void onStop() {
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    public interface View extends Presenter.View {

        void showWrongCredentials();

        void openSuperHeroesScreen();
    }

}
