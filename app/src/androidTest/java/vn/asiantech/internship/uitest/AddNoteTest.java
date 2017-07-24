package vn.asiantech.internship.uitest;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
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
 * UI Test with Espresso for Add Note.
 * Created by huypham on 20-Jul-17.
 */
@RunWith(AndroidJUnit4.class)
public class AddNoteTest {

    @Rule
    public ActivityTestRule<NoteActivity> mActivityTestRule = new ActivityTestRule<>(NoteActivity.class);

    @Before
    public void testShowAddNoteFragment() {
        Espresso.onView(ViewMatchers.withId(R.id.menuAdd))
                .perform(ViewActions.click())
                .check(ViewAssertions.doesNotExist());
        Espresso.onView(ViewMatchers.withId(R.id.cardViewAddNote))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testNoContent() {
        Espresso.onView(ViewMatchers.withId(R.id.edtTitle))
                .perform(ViewActions.typeText("Title ABC"), ViewActions.closeSoftKeyboard())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.menuAddNote))
                .perform(ViewActions.click())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testNoTitle() {
        Espresso.onView(ViewMatchers.withId(R.id.edtContent))
                .perform(ViewActions.typeText("Rainy Day"), ViewActions.closeSoftKeyboard())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.menuAddNote))
                .perform(ViewActions.click())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testBlank() {
        Espresso.onView(ViewMatchers.withId(R.id.edtTitle))
                .perform(ViewActions.typeText(""), ViewActions.closeSoftKeyboard())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.edtContent))
                .perform(ViewActions.typeText(""), ViewActions.closeSoftKeyboard())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.menuAddNote))
                .perform(ViewActions.click())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testAddSuccess() {
        Espresso.onView(ViewMatchers.withId(R.id.edtTitle))
                .perform(ViewActions.typeText("Title ABC"), ViewActions.closeSoftKeyboard())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.edtContent))
                .perform(ViewActions.typeText("Rainy Day"), ViewActions.closeSoftKeyboard())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.menuAddNote))
                .perform(ViewActions.click())
                .check(ViewAssertions.doesNotExist());
    }
}
