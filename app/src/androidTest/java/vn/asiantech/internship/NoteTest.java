package vn.asiantech.internship;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import vn.asiantech.internship.ui.note.activity.NoteActivity;

/**
 * Test UI for NoteActivity
 * Created by quanghai on 13/07/2017.
 */
@RunWith(AndroidJUnit4.class)
public class NoteTest {
    @Rule
    public ActivityTestRule<NoteActivity> rule = new ActivityTestRule<>(NoteActivity.class);

    @Test
    public void checkNewNote() {
        onView(withId(R.id.imgNewNote)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.edtNoteTitle)).check(matches(isDisplayed())).perform(typeText("New note"), closeSoftKeyboard());
        onView(withId(R.id.edtInputContent)).check(matches(isDisplayed())).perform(typeText("Content"), closeSoftKeyboard());
        onView(withId(R.id.imgSaveNote)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.recyclerViewNote)).check(matches(isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.edtEditNoteTitle)).check(matches(withText("New note")));
        onView(withId(R.id.edtEditInputContent)).check(matches(withText("Content")));
    }

    @Test
    public void checkNewNoteFail() {
        onView(withId(R.id.imgNewNote)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.edtInputContent)).check(matches(isDisplayed())).perform(typeText("Content"), closeSoftKeyboard());
        onView(withId(R.id.imgSaveNote)).check(matches(isDisplayed())).perform(click());
    }

    @Test
    public void checkSelectNote() {
        onView(withId(R.id.recyclerViewNote)).check(matches(isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @Test
    public void checkEditNoteCompleted() {
        onView(withId(R.id.recyclerViewNote)).check(matches(isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.edtEditNoteTitle)).check(matches(isDisplayed())).perform(clearText(), typeText("Edit title"), closeSoftKeyboard());
        onView(withId(R.id.imgEditNote)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.recyclerViewNote)).check(matches(isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.edtEditNoteTitle)).check(matches(withText("Edit title")));
    }

    @Test
    public void checkEditNoteFail() {
        onView(withId(R.id.recyclerViewNote)).check(matches(isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.edtEditNoteTitle)).check(matches(isDisplayed())).perform(clearText(), closeSoftKeyboard());
        onView(withId(R.id.imgEditNote)).check(matches(isDisplayed())).perform(click());
    }

    @Test
    public void checkDeleteNote() {
        onView(withId(R.id.imgNewNote)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.edtNoteTitle)).check(matches(isDisplayed())).perform(typeText("New note"), closeSoftKeyboard());
        onView(withId(R.id.edtInputContent)).check(matches(isDisplayed())).perform(typeText("Content"), closeSoftKeyboard());
        onView(withId(R.id.imgSaveNote)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.recyclerViewNote)).check(matches(isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.imgDeleteNote)).check(matches(isDisplayed())).perform(click());
    }
}
