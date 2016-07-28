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

package com.malmstein.workshops.firebase.ui.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.malmstein.workshops.firebase.R;
import com.malmstein.workshops.firebase.SuperHeroesApplication;
import com.malmstein.workshops.firebase.model.SuperHero;
import com.malmstein.workshops.firebase.ui.presenter.SuperHeroDetailPresenter;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.Bind;

public class SuperHeroDetailActivity extends BaseActivity implements SuperHeroDetailPresenter.View {

    private static final String SUPER_HERO_NAME_KEY = "super_hero_name_key";

    @Inject
    SuperHeroDetailPresenter presenter;

    @Bind(R.id.iv_super_hero_photo)
    ImageView superHeroPhotoImageView;
    @Bind(R.id.tv_super_hero_name)
    TextView superHeroNameTextView;
    @Bind(R.id.tv_super_hero_description)
    TextView superHeroDescriptionTextView;
    @Bind(R.id.iv_avengers_badge)
    View avengersBadgeView;

    public static void open(Context context, String superHeroName) {
        Intent intent = new Intent(context, SuperHeroDetailActivity.class);
        intent.putExtra(SUPER_HERO_NAME_KEY, superHeroName);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeDagger();
        initializePresenter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fresh_config_menu:
                presenter.fetchTextSize();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.super_hero_detail_activity;
    }

    @Override
    public void showSuperHero(SuperHero superHero) {
        Picasso.with(this).load(superHero.getPhoto()).fit().centerCrop().into(superHeroPhotoImageView);
        superHeroNameTextView.setText(superHero.getName());
        superHeroDescriptionTextView.setText(superHero.getDescription());
        int avengersBadgeVisibility = superHero.isAvenger() ? View.VISIBLE : View.GONE;
        avengersBadgeView.setVisibility(avengersBadgeVisibility);
    }

    @Override
    public void updateTextSize(long textSize) {
        superHeroDescriptionTextView.setTextSize(textSize);
    }

    @Override
    protected void initializeToolbar() {
        super.initializeToolbar();
        String superHeroName = getSuperHeroName();
        setTitle(superHeroName);
    }

    private void initializeDagger() {
        SuperHeroesApplication app = (SuperHeroesApplication) getApplication();
        app.getMainComponent().inject(this);
    }

    private void initializePresenter() {
        presenter.setView(this);
        String name = getSuperHeroName();
        presenter.setName(name);
        presenter.initialize();
    }

    private String getSuperHeroName() {
        return getIntent().getExtras().getString(SUPER_HERO_NAME_KEY);
    }
}
