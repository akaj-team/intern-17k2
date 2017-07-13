package vn.asiantech.internship;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import vn.asiantech.internship.notesqlite.NoteActivity;

/**
 * Author AsianTech Inc.
 * Created by sony on 13/07/2017.
 */
@RunWith(AndroidJUnit4.class)
public class HDeleteNoteTest {

    @Rule
    public ActivityTestRule<NoteActivity> mActivityRule = new ActivityTestRule<>(NoteActivity.class);

    @Before
    public void checkShowFragment() {
        Espresso.onView(ViewMatchers.withId(R.id.recyclerViewNote)).
                perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
        Espresso.onView(ViewMatchers.withId(R.id.llEditNote)).
                check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void deleteNoteTest() {
        Espresso.onView(ViewMatchers.withId(R.id.imgDeleteNote))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText(Matchers.startsWith("Delete success")))
                .inRoot(RootMatchers.withDecorView(Matchers.not(Matchers.is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
