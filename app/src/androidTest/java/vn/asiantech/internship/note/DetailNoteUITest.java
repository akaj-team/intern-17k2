package vn.asiantech.internship.note;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import vn.asiantech.internship.R;
import vn.asiantech.internship.ui.main.NoteActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by ducle on 17/07/2017.
 * DetailNoteUITest to test UI of fragment detail
 */
@RunWith(AndroidJUnit4.class)
public class DetailNoteUITest {
    @Rule
    public ActivityTestRule<NoteActivity> mActivityRule = new ActivityTestRule<>(NoteActivity.class);

    @Test
    public void checkClickDelete() {
        onView(withId(R.id.recyclerViewNote)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click())).check(doesNotExist());
        onView(withId(R.id.imgDelete)).perform(click()).check(doesNotExist());
        onView(withId(R.id.llDetail)).check(doesNotExist());
        onView(withId(R.id.llMain)).check(matches(isDisplayed()));
    }
}
