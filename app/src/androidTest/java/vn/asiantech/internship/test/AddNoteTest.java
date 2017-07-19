package vn.asiantech.internship.test;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

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
public class AddNoteTest {

    @Rule
    public ActivityTestRule<NoteActivity> mActivityRule = new ActivityTestRule(NoteActivity.class);

    @Test
    public void checkBlankContent() {
        Espresso.onView(ViewMatchers.withId(R.id.mnAdd))
                .perform(click())
                .check(ViewAssertions.doesNotExist());
        Espresso.onView(ViewMatchers.withId(R.id.edtAddNoteTitle))
                .perform(typeText("DinhDepTrai Blank"), ViewActions.closeSoftKeyboard())
                .check(matches(isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.mnSave))
                .perform(click())
                .check(ViewAssertions.doesNotExist());
    }

    @Test
    public void checkBlankTitle() {
        Espresso.onView(ViewMatchers.withId(R.id.mnAdd))
                .perform(click())
                .check(ViewAssertions.doesNotExist());
        Espresso.onView(ViewMatchers.withId(R.id.edtAddNoteContent))
                .perform(typeText("OMG DinhDepTrai Blank"), ViewActions.closeSoftKeyboard())
                .check(matches(isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.mnSave))
                .perform(click())
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkValidateNote() {
        Espresso.onView(ViewMatchers.withId(R.id.mnAdd))
                .perform(click())
                .check(ViewAssertions.doesNotExist());
        Espresso.onView(ViewMatchers.withId(R.id.edtAddNoteTitle))
                .perform(typeText("DinhDepTrai"), ViewActions.closeSoftKeyboard())
                .check(matches(isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.edtAddNoteContent))
                .perform(typeText("DinhHandsome"), ViewActions.closeSoftKeyboard())
                .check(matches(isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.imgAddNote)).check(matches(isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.mnAttachFile)).check(matches(isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.mnSave))
                .perform(click())
                .check(ViewAssertions.doesNotExist());
    }
}
