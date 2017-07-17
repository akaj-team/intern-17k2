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

import org.hamcrest.Matchers;
import org.junit.Before;
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
    public ActivityTestRule<NoteActivity> activityTestRule = new ActivityTestRule<NoteActivity>(NoteActivity.class);

    @Before
    public void openNoteActivity() {
        Intent intent = new Intent();
        activityTestRule.launchActivity(intent);
        Espresso.onView(ViewMatchers.withId(R.id.llMain)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.tvTitle)).check(ViewAssertions.matches(ViewMatchers.withText(R.string.title_note)));
        Espresso.onView(ViewMatchers.withId(R.id.imgAddImage)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.imgAddNote)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.imgEdit)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.imgDelete)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.recyclerViewNote)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    //Test AddFragment
    @Test
    public void testAddFragment() {
        Espresso.onView(ViewMatchers.withId(R.id.imgAddNote)).perform(ViewActions.click()).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.imgAddImage)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.imgAddNote)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.imgEdit)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.imgDelete)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.imgSave)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        //Non Input
        String title = "";
        String content = "";
        setText(R.id.edtNoteTitle, title);
        setText(R.id.edtNoteContent, content);
        Espresso.onView(ViewMatchers.withId(R.id.imgSave)).perform(ViewActions.click()).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText(R.string.validate))
                .inRoot(RootMatchers.withDecorView(Matchers.not(Matchers.is(activityTestRule.getActivity().getWindow().getDecorView()))))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        //Non Content
        title = "asdhasd";
        content = "";
        setText(R.id.edtNoteTitle, title);
        setText(R.id.edtNoteContent, content);
        Espresso.onView(ViewMatchers.withId(R.id.imgSave)).perform(ViewActions.click()).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText(R.string.validate))
                .inRoot(RootMatchers.withDecorView(Matchers.not(Matchers.is(activityTestRule.getActivity().getWindow().getDecorView()))))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        //Non Title
        title = "";
        content = "jhgjhgjhg";
        setText(R.id.edtNoteTitle, title);
        setText(R.id.edtNoteContent, content);
        Espresso.onView(ViewMatchers.withId(R.id.imgSave)).perform(ViewActions.click()).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText(R.string.validate))
                .inRoot(RootMatchers.withDecorView(Matchers.not(Matchers.is(activityTestRule.getActivity().getWindow().getDecorView()))))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        //Correct Data
        title = "Cuonggggggggggggggggg";
        content = "Cuong";
        openNoteActivity();
        Espresso.onView(ViewMatchers.withId(R.id.imgAddNote)).perform(ViewActions.click()).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        setText(R.id.edtNoteTitle, title);
        setText(R.id.edtNoteContent, content);
        Espresso.onView(ViewMatchers.withId(R.id.imgSave)).perform(ViewActions.click()).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.recyclerViewNote)).perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click())).check(ViewAssertions.doesNotExist());
        Espresso.onView(ViewMatchers.withId(R.id.tvTitle)).check(ViewAssertions.matches(ViewMatchers.withText(R.string.title_detail)));
        Espresso.onView(ViewMatchers.withId(R.id.imgAddImage)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.imgAddNote)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.imgEdit)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.imgDelete)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.imgSave)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.edtNoteTitle)).check(ViewAssertions.matches(ViewMatchers.withText(title)));
        Espresso.onView(ViewMatchers.withId(R.id.edtNoteContent)).check(ViewAssertions.matches(ViewMatchers.withText(content)));
        Espresso.onView(ViewMatchers.withText(R.string.success))
                .inRoot(RootMatchers.withDecorView(Matchers.not(Matchers.is(activityTestRule.getActivity().getWindow().getDecorView()))))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    private void setText(int id, String newText) {
        Espresso.onView(ViewMatchers.withId(id)).perform(ViewActions.clearText()).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(id)).perform(ViewActions.typeText(newText), ViewActions.closeSoftKeyboard()).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
