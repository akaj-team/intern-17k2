package vn.asiantech.internship.uitest;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import vn.asiantech.internship.R;
import vn.asiantech.internship.ui.note.NoteActivity;

/**
 * UI Test with Espresso for Delete Note.
 * Created by AnhHuy on 20-Jul-17.
 */
@RunWith(AndroidJUnit4.class)
public class DeleteNoteTest {

    @Rule
    public ActivityTestRule<NoteActivity> mActivityTestRule = new ActivityTestRule<>(NoteActivity.class);

    @Before
    public void testShowDeleteNoteFragment() {
        Espresso.onView(ViewMatchers.withId(R.id.recyclerViewHome))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()))
                .check(ViewAssertions.doesNotExist());
    }

    @Test
    public void testDeleteNote() {
        Espresso.onView(ViewMatchers.withId(R.id.menuDelete))
                .perform(ViewActions.click())
                .check(ViewAssertions.doesNotExist());
    }
}
