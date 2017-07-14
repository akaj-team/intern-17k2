package vn.asiantech.internship;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Random;

import vn.asiantech.internship.models.NoteItem;
import vn.asiantech.internship.ui.main.NoteActivity;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 7/13/2017.
 */
@RunWith(AndroidJUnit4.class)
public class EditNoteTest {

    @Rule
    public ActivityTestRule<NoteActivity> mActivityTestRule = new ActivityTestRule<NoteActivity>(NoteActivity.class);
    private List<NoteItem> mNotes;

    @Before
    public void openNoteActivity() {
        Intent intent = new Intent();
        mActivityTestRule.launchActivity(intent);
        mNotes = mActivityTestRule.getActivity().getNotes();
        Espresso.onView(ViewMatchers.withId(R.id.llMain)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.tvTitle)).check(ViewAssertions.matches(ViewMatchers.withText(R.string.title_note)));
        Espresso.onView(ViewMatchers.withId(R.id.imgAddImage)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.imgAddNote)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.imgEdit)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.imgDelete)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.recyclerViewNote)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void checkEditNote() {
        Random random = new Random();
        int pos = random.nextInt(mNotes.size());
        Espresso.onView(ViewMatchers.withId(R.id.recyclerViewNote)).perform(RecyclerViewActions.actionOnItemAtPosition(pos, ViewActions.click()));
        Espresso.onView(ViewMatchers.withId(R.id.tvTitle)).check(ViewAssertions.matches(ViewMatchers.withText(R.string.title_detail)));
        Espresso.onView(ViewMatchers.withId(R.id.imgAddImage)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.imgAddNote)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.imgEdit)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.imgDelete)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.imgSave)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.edtNoteTitle)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isEnabled())));
        Espresso.onView(ViewMatchers.withId(R.id.edtNoteTitle)).check(ViewAssertions.matches(ViewMatchers.withText(mNotes.get(pos).getTitle())));
        Espresso.onView(ViewMatchers.withId(R.id.edtNoteContent)).check(ViewAssertions.matches(ViewMatchers.withText(mNotes.get(pos).getContent())));
        Espresso.onView(ViewMatchers.withId(R.id.edtNoteContent)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isEnabled())));
        Espresso.onView(ViewMatchers.withId(R.id.tvNoteTime)).check(ViewAssertions.matches(ViewMatchers.withText(mNotes.get(pos).getStringTime())));
        Espresso.onView(ViewMatchers.withId(R.id.imgEdit)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.edtNoteTitle)).check(ViewAssertions.matches(ViewMatchers.isEnabled()));
        Espresso.onView(ViewMatchers.withId(R.id.edtNoteContent)).check(ViewAssertions.matches(ViewMatchers.isEnabled()));

        //Non Input
        String title = "";
        String content = "";
        setText(R.id.edtNoteTitle, title);
        setText(R.id.edtNoteContent, content);
        Espresso.onView(ViewMatchers.withId(R.id.imgSave)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText(R.string.validate))
                .inRoot(RootMatchers.withDecorView(Matchers.not(Matchers.is(mActivityTestRule.getActivity().getWindow().getDecorView()))))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        //Non title
        title = "";
        content = "gggggggggggggggggggggggggg";
        setText(R.id.edtNoteTitle, title);
        setText(R.id.edtNoteContent, content);
        Espresso.onView(ViewMatchers.withId(R.id.imgSave)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText(R.string.validate))
                .inRoot(RootMatchers.withDecorView(Matchers.not(Matchers.is(mActivityTestRule.getActivity().getWindow().getDecorView()))))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        //Non content
        title = "llllllllllllllllllll";
        content = "";
        Espresso.onView(ViewMatchers.withId(R.id.edtNoteTitle)).perform(ViewActions.clearText());
        Espresso.onView(ViewMatchers.withId(R.id.edtNoteContent)).perform(ViewActions.clearText());
        Espresso.onView(ViewMatchers.withId(R.id.edtNoteTitle)).perform(ViewActions.typeText(title), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.edtNoteContent)).perform(ViewActions.typeText(content), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.imgSave)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText(R.string.validate))
                .inRoot(RootMatchers.withDecorView(Matchers.not(Matchers.is(mActivityTestRule.getActivity().getWindow().getDecorView()))))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        //Correct
        openNoteActivity();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Log.i("tag11", e.getMessage());
        }
        Espresso.onView(ViewMatchers.withId(R.id.recyclerViewNote)).perform(RecyclerViewActions.actionOnItemAtPosition(pos, ViewActions.click()));
        Espresso.onView(ViewMatchers.withId(R.id.imgEdit)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.edtNoteTitle)).check(ViewAssertions.matches(ViewMatchers.isEnabled()));
        Espresso.onView(ViewMatchers.withId(R.id.edtNoteContent)).check(ViewAssertions.matches(ViewMatchers.isEnabled()));
        title = "12121212121212";
        content = "565656565656565656";
        setText(R.id.edtNoteTitle, title);
        setText(R.id.edtNoteContent, content);
        Espresso.onView(ViewMatchers.withId(R.id.imgSave)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.recyclerViewNote)).perform(RecyclerViewActions.actionOnItemAtPosition(pos, ViewActions.click()));
        Espresso.onView(ViewMatchers.withId(R.id.tvTitle)).check(ViewAssertions.matches(ViewMatchers.withText(R.string.title_detail)));
        Espresso.onView(ViewMatchers.withId(R.id.imgAddImage)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.imgAddNote)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.imgEdit)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.imgDelete)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.imgSave)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.edtNoteTitle)).check(ViewAssertions.matches(ViewMatchers.withText(title)));
        Espresso.onView(ViewMatchers.withId(R.id.edtNoteContent)).check(ViewAssertions.matches(ViewMatchers.withText(content)));
    }

    private void setText(int id, String newText) {
        Espresso.onView(ViewMatchers.withId(id)).perform(ViewActions.clearText());
        Espresso.onView(ViewMatchers.withId(id)).perform(ViewActions.typeText(newText), ViewActions.closeSoftKeyboard());
    }
}
