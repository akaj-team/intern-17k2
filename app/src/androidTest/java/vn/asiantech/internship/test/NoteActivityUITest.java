package vn.asiantech.internship.test;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day11.ui.activity.NoteActivity;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 12/07/2017.
 */
@RunWith(AndroidJUnit4.class)
public class NoteActivityUITest {
    @Rule
    public ActivityTestRule<NoteActivity> mActivityRule =
            new ActivityTestRule<>(NoteActivity.class);

    @Test
    public void checkWhenIntoActivity() {
        Espresso.onView(ViewMatchers.withId(R.id.flNote)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.imgAdd)).perform(ViewActions.click()).check(ViewAssertions.doesNotExist());
        Espresso.onView(ViewMatchers.withId(R.id.llInformationEditNote)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
