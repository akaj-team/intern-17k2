package vn.asiantech.internship.testUI;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import vn.asiantech.internship.R;
import vn.asiantech.internship.note.ui.NoteActivity;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

/**
 * Created by at-dinhvo on 12/07/2017.
 */
@RunWith(AndroidJUnit4.class)
public class NoteUITest {

    @Rule
    private ActivityTestRule<NoteActivity> mActivityRule = new ActivityTestRule(NoteActivity.class);

    @Test
    public void showUI() {
        Espresso.onView(ViewMatchers.withId(R.id.recycleViewNote))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        Espresso.onView(ViewMatchers.withId(R.id.mnAdd)).perform(click())
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkValidateAddNote() {
        Espresso.onView(ViewMatchers.withId(R.id.edtNoteTitle))
                .perform(typeText("DinhDepTrai"), ViewActions.closeSoftKeyboard())
                .check(matches(isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.edtNoteContent))
                .perform(typeText("OMG DinhDepTrai"), ViewActions.closeSoftKeyboard())
                .check(matches(isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.imgAddNote)).check(matches(isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.mnAttachFile)).check(matches(isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.mnSave))
                .perform(click())
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkValidateBlankTitle() {
        Espresso.onView(ViewMatchers.withId(R.id.edtNoteContent))
                .perform(typeText("OMG DinhDepTrai"), ViewActions.closeSoftKeyboard())
                .check(matches(isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.mnSave))
                .perform(click())
                .check(matches(isDisplayed()));
        // Check toast
        Espresso.onView(ViewMatchers.withText("Please add note title!"))
                .inRoot(RootMatchers.withDecorView(Matchers.not(Matchers.is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkValidateBlankContent() {
        Espresso.onView(ViewMatchers.withId(R.id.edtNoteTitle))
                .perform(typeText("DinhDepTrai"), ViewActions.closeSoftKeyboard())
                .check(matches(isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.mnSave))
                .perform(click())
                .check(matches(isDisplayed()));
        Espresso.onView(ViewMatchers.withText("Please add note content!"))
                .inRoot(RootMatchers.withDecorView(Matchers.not(Matchers.is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }


}
