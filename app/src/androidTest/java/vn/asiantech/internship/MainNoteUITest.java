package vn.asiantech.internship;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import vn.asiantech.internship.ui.main.NoteActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by ducle on 17/07/2017.
 * MainNoteUITest to test UI of fragment main
 */
@RunWith(AndroidJUnit4.class)
public class MainNoteUITest {

    @Rule
    public ActivityTestRule<NoteActivity> mActivityRule = new ActivityTestRule<>(NoteActivity.class);

    @Before
    public void checkShowMainFragment() {
        onView(withId(R.id.llMain)).check(matches(isDisplayed()));
        onView(withId(R.id.imgAdd)).check(matches(isDisplayed()));
        onView(withId(R.id.tvTitleMain)).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerViewNote)).check(matches(isDisplayed()));
    }

    @Test
    public void checkClickAdd() {
        onView(withId(R.id.imgAdd)).perform(click()).check(doesNotExist());
        onView(withId(R.id.llAdd)).check(matches(isDisplayed()));
        onView(withId(R.id.tvTitleAdd)).check(matches(isDisplayed()));
        onView(withId(R.id.imgSave)).check(matches(isDisplayed()));
        onView(withId(R.id.imgPicPhoto)).check(matches(isDisplayed()));
        onView(withId(R.id.imgNote)).check(matches(isDisplayed()));
        onView(withId(R.id.edtTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.edtContent)).check(matches(isDisplayed()));
    }

    @Test
    public void checkItemClick() {
        onView(withId(R.id.recyclerViewNote)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click())).check(doesNotExist());
        onView(withId(R.id.llDetail)).check(matches(isDisplayed()));
    }
}
