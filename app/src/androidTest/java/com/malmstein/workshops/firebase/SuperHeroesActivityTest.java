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

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;

import com.karumi.katasuperheroes.di.MainComponent;
import com.karumi.katasuperheroes.di.MainModule;
import com.karumi.katasuperheroes.model.SuperHero;
import com.karumi.katasuperheroes.model.SuperHeroesRepository;
import com.karumi.katasuperheroes.ui.view.SuperHeroesActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import it.cosenonjaviste.daggermock.DaggerMockRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SuperHeroesActivityTest {

    @Rule
    public DaggerMockRule<MainComponent> daggerRule =
            new DaggerMockRule<>(MainComponent.class, new MainModule()).set(
                    new DaggerMockRule.ComponentSetter<MainComponent>() {
                        @Override
                        public void setComponent(MainComponent component) {
                            SuperHeroesApplication app =
                                    (SuperHeroesApplication) InstrumentationRegistry.getInstrumentation()
                                            .getTargetContext()
                                            .getApplicationContext();
                            app.setComponent(component);
                        }
                    });

    @Rule
    public IntentsTestRule<SuperHeroesActivity> activityRule =
            new IntentsTestRule<>(SuperHeroesActivity.class, true, false);

    @Mock
    SuperHeroesRepository repository;

    @Test
    public void showsEmptyCaseIfThereAreNoSuperHeroes() {
        givenThereAreNoSuperHeroes();

        startActivity();

        onView(withText("¯\\_(ツ)_/¯")).check(matches(isDisplayed()));
    }

    @Test
    public void showsSuperHeroesNameIfThereAreSuperHeroes() {
        List<SuperHero> superHeroes = givenThereAreSomeSuperHeroes(ANY_NUMBER_OF_SUPER_HEROES);

        startActivity();

        RecyclerViewInteraction.<SuperHero>onRecyclerView(withId(R.id.recycler_view))
                .withItems(superHeroes)
                .check(new RecyclerViewInteraction.ItemViewAssertion<SuperHero>() {
                    @Override
                    public void check(SuperHero superHero, View view, NoMatchingViewException e) {
                        matches(hasDescendant(withText(superHero.getName()))).check(view, e);
                    }
                });
    }

    @Test
    public void showsAvengersBadgeIfASuperHeroIsPartOfTheAvengersTeam() {
        List<SuperHero> superHeroes = givenThereAreSomeAvengers(ANY_NUMBER_OF_SUPER_HEROES);

        startActivity();

        RecyclerViewInteraction.<SuperHero>onRecyclerView(withId(R.id.recycler_view))
                .withItems(superHeroes)
                .check(new RecyclerViewInteraction.ItemViewAssertion<SuperHero>() {
                    @Override
                    public void check(SuperHero superHero, View view, NoMatchingViewException e) {
                        matches(hasDescendant(allOf(withId(R.id.iv_avengers_badge),
                                withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))).check(view, e);
                    }
                });
    }

    @Test
    public void doesNotShowAvengersBadgeIfASuperHeroIsNotPartOfTheAvengersTeam() {
        List<SuperHero> superHeroes = givenThereAreSomeSuperHeroes(ANY_NUMBER_OF_SUPER_HEROES, false);

        startActivity();

        RecyclerViewInteraction.<SuperHero>onRecyclerView(withId(R.id.recycler_view))
                .withItems(superHeroes)
                .check(new RecyclerViewInteraction.ItemViewAssertion<SuperHero>() {
                    @Override
                    public void check(SuperHero superHero, View view, NoMatchingViewException e) {
                        matches(hasDescendant(allOf(withId(R.id.iv_avengers_badge),
                                withEffectiveVisibility(ViewMatchers.Visibility.GONE)))).check(view, e);
                    }
                });
    }

    @Test
    public void doesNotShowEmptyCaseIfThereAreSuperHeroes() {
        givenThereAreSomeSuperHeroes(ANY_NUMBER_OF_SUPER_HEROES);

        startActivity();

        onView(withId(R.id.tv_empty_case)).check(matches(not(isDisplayed())));
    }

    @Test
    public void doesNotShowLoadingViewOnceSuperHeroesAreShown() {
        givenThereAreSomeSuperHeroes(ANY_NUMBER_OF_SUPER_HEROES);

        startActivity();

        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())));
    }

    @Test
    public void opensSuperHeroDetailActivityOnRecyclerViewItemTapped() {
        List<SuperHero> superHeroes = givenThereAreSomeSuperHeroes();
        int superHeroIndex = 0;
        startActivity();

        onView(withId(R.id.recycler_view)).
                perform(RecyclerViewActions.actionOnItemAtPosition(superHeroIndex, click()));

        SuperHero superHeroSelected = superHeroes.get(superHeroIndex);
        intended(hasComponent(SuperHeroDetailActivity.class.getCanonicalName()));
        intended(hasExtra("super_hero_name_key", superHeroSelected.getName()));
    }

    @Test
    public void showsTheExactNumberOfSuperHeroes() {
        givenThereAreSomeSuperHeroes(ANY_NUMBER_OF_SUPER_HEROES);

        startActivity();

        onView(withId(R.id.recycler_view)).check(
                matches(recyclerViewHasItemCount(ANY_NUMBER_OF_SUPER_HEROES)));
    }

    private List<SuperHero> givenThereAreSomeAvengers(int numberOfAvengers) {
        return givenThereAreSomeSuperHeroes(numberOfAvengers, true);
    }

    private List<SuperHero> givenThereAreSomeSuperHeroes() {
        return givenThereAreSomeSuperHeroes(ANY_NUMBER_OF_SUPER_HEROES);
    }

    private List<SuperHero> givenThereAreSomeSuperHeroes(int numberOfSuperHeroes) {
        return givenThereAreSomeSuperHeroes(numberOfSuperHeroes, false);
    }

    private List<SuperHero> givenThereAreSomeSuperHeroes(int numberOfSuperHeroes, boolean avengers) {
        List<SuperHero> superHeroes = new LinkedList<>();
        for (int i = 0; i < numberOfSuperHeroes; i++) {
            String superHeroName = "SuperHero - " + i;
            String superHeroPhoto = "https://i.annihil.us/u/prod/marvel/i/mg/c/60/55b6a28ef24fa.jpg";
            String superHeroDescription = "Description Super Hero - " + i;
            SuperHero superHero =
                    new SuperHero(superHeroName, superHeroPhoto, avengers, superHeroDescription);
            superHeroes.add(superHero);
            when(repository.getByName(superHeroName)).thenReturn(superHero);
        }
        when(repository.getAll()).thenReturn(superHeroes);
        return superHeroes;
    }

    private void givenThereAreNoSuperHeroes() {
        when(repository.getAll()).thenReturn(Collections.<SuperHero>emptyList());
    }

    private MainActivity startActivity() {
        return activityRule.launchActivity(null);
    }
}