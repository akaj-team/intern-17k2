package vn.asiantech.internship.test;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.StringStartsWith;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day11.ui.activity.NoteActivity;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 13/07/2017.
 */
@RunWith(AndroidJUnit4.class)
public class InformationFragmentUITest {

    @Rule
    public ActivityTestRule<NoteActivity> mActivityRule =
            new ActivityTestRule<>(NoteActivity.class);

    @Before
    public void init() {
        Espresso.onView(ViewMatchers.withId(R.id.recyclerViewNote)).perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
    }

    @Test
    public void testWhenPressDelete() {
        Espresso.onView(ViewMatchers.withId(R.id.imgDeleteNote)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.flNote)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Check toast
        Espresso.onView(ViewMatchers.withText(StringStartsWith.startsWith("Deleted note")))
                .inRoot(RootMatchers.withDecorView(IsNot.not(Matchers.is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testWhenPressEdit() {
        Espresso.onView(ViewMatchers.withId(R.id.imgEditNote)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.edtTitleInformation)).perform(ViewActions.typeText("aaaaa"));
        Espresso.onView(ViewMatchers.withId(R.id.edtDescriptionInforNote)).perform(ViewActions.typeText("bbbbb"));
        Espresso.onView(ViewMatchers.withId(R.id.imgBack)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.flNote)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testWhenPressBack() {
        Espresso.onView(ViewMatchers.withId(R.id.imgBack)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.flNote)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testWhenNoInputTitle() {
        Espresso.onView(ViewMatchers.withId(R.id.imgEditNote)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.edtTitleInformation)).perform(ViewActions.clearText());
        Espresso.onView(ViewMatchers.withId(R.id.imgBack)).perform(ViewActions.click());

        // Check toast
        Espresso.onView(ViewMatchers.withText(StringStartsWith.startsWith("Input title")))
                .inRoot(RootMatchers.withDecorView(IsNot.not(Matchers.is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testWhenNoInputDescription() {
        Espresso.onView(ViewMatchers.withId(R.id.imgEditNote)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.edtDescriptionInforNote)).perform(ViewActions.clearText());
        Espresso.onView(ViewMatchers.withId(R.id.imgBack)).perform(ViewActions.click());

        // Check toast
        Espresso.onView(ViewMatchers.withText(StringStartsWith.startsWith("Input Description")))
                .inRoot(RootMatchers.withDecorView(IsNot.not(Matchers.is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
