package vn.asiantech.internship.uitest;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import vn.asiantech.internship.R;
import vn.asiantech.internship.ui.note.NoteActivity;

/**
 * UI Test with Espresso for Edit Note.
 * Created by AnhHuy on 21-Jul-17.
 */
@RunWith(AndroidJUnit4.class)
public class EditNoteTest {

    @Rule
    public ActivityTestRule<NoteActivity> mActivityTestRule = new ActivityTestRule<>(NoteActivity.class);

    @Test
    public void testShowEditNoteFragment() {
        openEditNoteFragment();
    }

    @Test
    public void testClearTitle() {
        openEditNoteFragment();
        Espresso.onView(ViewMatchers.withId(R.id.edtTitleDetail))
                .perform(ViewActions.clearText())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.menuEdit))
                .perform(ViewActions.click())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testClearContent() {
        openEditNoteFragment();
        Espresso.onView(ViewMatchers.withId(R.id.edtContentDetail))
                .perform(ViewActions.clearText())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.menuEdit))
                .perform(ViewActions.click())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testBlank() {
        openEditNoteFragment();
        Espresso.onView(ViewMatchers.withId(R.id.edtTitleDetail))
                .perform(ViewActions.clearText())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.edtContentDetail))
                .perform(ViewActions.clearText())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.menuEdit))
                .perform(ViewActions.click())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testEditSuccess() {
        openEditNoteFragment();
        Espresso.onView(ViewMatchers.withId(R.id.edtTitleDetail))
                .perform(ViewActions.typeText(" XYZ"), ViewActions.closeSoftKeyboard())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.edtContentDetail))
                .perform(ViewActions.typeText(" Hate the rain"), ViewActions.closeSoftKeyboard())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.menuEdit))
                .perform(ViewActions.click())
                .check(ViewAssertions.doesNotExist());
    }

    private void openEditNoteFragment() {
        Espresso.onView(ViewMatchers.withId(R.id.recyclerViewHome))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()))
                .check(ViewAssertions.doesNotExist());
    }
}
