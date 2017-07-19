package vn.asiantech.internship;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import vn.asiantech.internship.ui.main.NoteActivity;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 7/13/2017.
 */
@RunWith(AndroidJUnit4.class)
public class AddNoteTest {

    @Rule
    public ActivityTestRule<NoteActivity> mActivityTestRule = new ActivityTestRule<NoteActivity>(NoteActivity.class);

    @Test
    public void testAddFragmentNonInput() {
        testNoteActivity();
        //Non Input
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

    @Test
    public void testAddFragmentNonContent() {
        testNoteActivity();
        //Non Content
        String title = "asdhasd";
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

    @Test
    public void testAddFragmentCorrect() {
        testNoteActivity();
        //Correct Data
        String title = "Cuonggggggggggggggggg";
        String content = "Cuong";
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

    @Test
    public void testAddFragmentNonTitle() {
        testNoteActivity();
        //Non Title
        String title = "";
        String content = "jhgjhgjhg";
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

    private void setText(int id, String newText) {
        Espresso.onView(ViewMatchers.withId(id)).perform(ViewActions.clearText()).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(id)).perform(ViewActions.typeText(newText), ViewActions.closeSoftKeyboard()).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    private void testNoteActivity() {
        Espresso.onView(ViewMatchers.withId(R.id.llMain)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.tvTitle)).check(ViewAssertions.matches(ViewMatchers.withText(R.string.title_note)));
        Espresso.onView(ViewMatchers.withId(R.id.imgAddImage)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.imgAddNote)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.imgEdit)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.imgDelete)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.recyclerViewNote)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.imgAddNote)).perform(ViewActions.click()).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.imgAddImage)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.imgAddNote)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.imgEdit)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.imgDelete)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.imgSave)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
