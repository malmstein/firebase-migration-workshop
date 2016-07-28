package com.malmstein.workshops.firebase.usecase;

import android.os.Handler;
import android.os.Looper;

import com.malmstein.workshops.firebase.model.RemoteConfigRepository;

import javax.inject.Inject;

public class GetTextSize {

    private final RemoteConfigRepository repository;

    @Inject
    public GetTextSize(RemoteConfigRepository repository) {
        this.repository = repository;
    }

    public void getTextSize(final GetTextSize.Callback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                loadSuperHeroes(callback);
            }
        }).start();
    }

    private void loadSuperHeroes(final GetTextSize.Callback callback) {
        repository.fetchConfig(new RemoteConfigRepository.Callback() {
            @Override
            public void onTextSizeChanged(final long textSize) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onTextSizeLoaded(textSize);
                    }
                });
            }
        });

    }

    public interface Callback {

        void onTextSizeLoaded(long textSize);
    }

}
