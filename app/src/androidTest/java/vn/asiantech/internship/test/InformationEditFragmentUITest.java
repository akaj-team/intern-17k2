package vn.asiantech.internship.test;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.hamcrest.core.IsNot;
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
public class InformationEditFragmentUITest {

    @Rule
    public ActivityTestRule<NoteActivity> mActivityRule =
            new ActivityTestRule<>(NoteActivity.class);

    @Before
    public void init() {
        Espresso.onView(ViewMatchers.withId(R.id.llInformationNote)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.imgAdd)).perform(ViewActions.click()).check(ViewAssertions.doesNotExist());
        Espresso.onView(ViewMatchers.withId(R.id.llInformationEditNote)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testWhenInputTitleAndDescription() {
        Espresso.onView(ViewMatchers.withId(R.id.edtTitle)).perform(ViewActions.typeText("anh1"), ViewActions.closeSoftKeyboard()).check(ViewAssertions.matches(ViewMatchers.withText("anh1")));
        Espresso.onView(ViewMatchers.withId(R.id.edtDescription)).perform(ViewActions.typeText("This is image"), ViewActions.closeSoftKeyboard()).check(ViewAssertions.matches(ViewMatchers.withText("This is image")));
        Espresso.onView(ViewMatchers.withId(R.id.imgSave)).perform(ViewActions.click()).check(ViewAssertions.doesNotExist());
        Espresso.onView(ViewMatchers.withId(R.id.llInformationNote)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testNoInputTitleAndDescription() {
        Espresso.onView(ViewMatchers.withId(R.id.imgSave)).perform(ViewActions.click()).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Check toast
        Espresso.onView(ViewMatchers.withText(R.string.toast_input_title))
                .inRoot(RootMatchers.withDecorView(IsNot.not(Matchers.is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.edtTitle)).perform(ViewActions.typeText("anh123"), ViewActions.closeSoftKeyboard()).check(ViewAssertions.matches(ViewMatchers.withText("anh123")));
        Espresso.onView(ViewMatchers.withId(R.id.edtTitle)).perform(ViewActions.clearText()).check(ViewAssertions.matches(ViewMatchers.withText("")));
        Espresso.onView(ViewMatchers.withId(R.id.imgSave)).perform(ViewActions.click()).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Check toast
        Espresso.onView(ViewMatchers.withText(R.string.toast_input_title))
                .inRoot(RootMatchers.withDecorView(IsNot.not(Matchers.is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testInputTitle() {
        Espresso.onView(ViewMatchers.withId(R.id.edtTitle)).perform(ViewActions.typeText("anh987"), ViewActions.closeSoftKeyboard()).check(ViewAssertions.matches(ViewMatchers.withText("anh987")));
        Espresso.onView(ViewMatchers.withId(R.id.imgSave)).perform(ViewActions.click()).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Check toast
        Espresso.onView(ViewMatchers.withText(R.string.toast_input_description))
                .inRoot(RootMatchers.withDecorView(IsNot.not(Matchers.is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.edtDescription)).perform(ViewActions.typeText("anh456"), ViewActions.closeSoftKeyboard()).check(ViewAssertions.matches(ViewMatchers.withText("anh456")));
        Espresso.onView(ViewMatchers.withId(R.id.edtDescription)).perform(ViewActions.clearText()).check(ViewAssertions.matches(ViewMatchers.withText("")));
        Espresso.onView(ViewMatchers.withId(R.id.imgSave)).perform(ViewActions.click()).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Check toast
        Espresso.onView(ViewMatchers.withText(R.string.toast_input_description))
                .inRoot(RootMatchers.withDecorView(IsNot.not(Matchers.is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
