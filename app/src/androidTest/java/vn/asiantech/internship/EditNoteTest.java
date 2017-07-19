package vn.asiantech.internship;

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

    //Non Input
    @Test
    public void checkEditNoteNonInput() {
        testNoteActivity();
        String title = "";
        String content = "";
        setText(R.id.edtNoteTitle, title);
        setText(R.id.edtNoteContent, content);
        Espresso.onView(ViewMatchers.withId(R.id.imgSave)).perform(ViewActions.click()).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText(R.string.validate))
                .inRoot(RootMatchers.withDecorView(Matchers.not(Matchers.is(mActivityTestRule.getActivity().getWindow().getDecorView()))))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Log.i("tag11", e.getMessage());
        }
    }

    //Non title
    @Test
    public void checkEditNoteNonTitle() {
        testNoteActivity();
        String title = "";
        String content = "gggggggggggggggggggggggggg";
        setText(R.id.edtNoteTitle, title);
        setText(R.id.edtNoteContent, content);
        Espresso.onView(ViewMatchers.withId(R.id.imgSave)).perform(ViewActions.click()).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText(R.string.validate))
                .inRoot(RootMatchers.withDecorView(Matchers.not(Matchers.is(mActivityTestRule.getActivity().getWindow().getDecorView()))))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Log.i("tag11", e.getMessage());
        }
    }

    //Non content
    @Test
    public void checkEditNoteNonContent() {
        testNoteActivity();
        String title = "llllllllllllllllllll";
        String content = "";
        setText(R.id.edtNoteTitle, title);
        setText(R.id.edtNoteContent, content);
        Espresso.onView(ViewMatchers.withId(R.id.imgSave)).perform(ViewActions.click()).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText(R.string.validate))
                .inRoot(RootMatchers.withDecorView(Matchers.not(Matchers.is(mActivityTestRule.getActivity().getWindow().getDecorView()))))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Log.i("tag11", e.getMessage());
        }
    }

    //Correct
    @Test
    public void checkEditNoteCorrect() {
        testNoteActivity();
        String title = "12121212121212";
        String content = "565656565656565656";
        setText(R.id.edtNoteTitle, title);
        setText(R.id.edtNoteContent, content);
        Espresso.onView(ViewMatchers.withId(R.id.imgSave)).perform(ViewActions.click()).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withText(R.string.success))
                .inRoot(RootMatchers.withDecorView(Matchers.not(Matchers.is(mActivityTestRule.getActivity().getWindow().getDecorView()))))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Log.i("tag11", e.getMessage());
        }
    }

    private void setText(int id, String newText) {
        Espresso.onView(ViewMatchers.withId(id)).perform(ViewActions.clearText()).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(id)).perform(ViewActions.typeText(newText), ViewActions.closeSoftKeyboard()).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    private void testNoteActivity() {
        mNotes = mActivityTestRule.getActivity().getNotes();
        Espresso.onView(ViewMatchers.withId(R.id.llMain)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.tvTitle)).check(ViewAssertions.matches(ViewMatchers.withText(R.string.title_note)));
        Espresso.onView(ViewMatchers.withId(R.id.imgAddImage)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.imgAddNote)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.imgEdit)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.imgDelete)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.recyclerViewNote)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Random random = new Random();
        int pos = random.nextInt(mNotes.size());
        Espresso.onView(ViewMatchers.withId(R.id.recyclerViewNote)).perform(RecyclerViewActions.actionOnItemAtPosition(pos, ViewActions.click())).check(ViewAssertions.doesNotExist());
        Espresso.onView(ViewMatchers.withId(R.id.imgEdit)).perform(ViewActions.click()).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.edtNoteTitle)).check(ViewAssertions.matches(ViewMatchers.isEnabled()));
        Espresso.onView(ViewMatchers.withId(R.id.edtNoteContent)).check(ViewAssertions.matches(ViewMatchers.isEnabled()));
    }
}
