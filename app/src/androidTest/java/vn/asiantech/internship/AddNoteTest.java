package vn.asiantech.internship;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import vn.asiantech.internship.notesqlite.NoteActivity;

/**
 * Author AsianTech Inc.
 * Created by sony on 12/07/2017.
 */
@RunWith(AndroidJUnit4.class)
public class AddNoteTest {

    @Rule
    public ActivityTestRule<NoteActivity> mActivityRule = new ActivityTestRule<>(NoteActivity.class);

    @Before
    public void showAddNoteFragmentTest() {
        Espresso.onView(ViewMatchers.withId(R.id.imgAddNote))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.llAddNote))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void inputDataTest() {
        //Test blank content
        Espresso.onView(ViewMatchers.withId(R.id.edtTitleAdd)).
                perform(ViewActions.typeText("Note 1"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.imgAdd))
                .perform(ViewActions.click());
        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.tvAddNoteError), ViewMatchers.withText(R.string.note_text_error_content)))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        //Test blank title
        Espresso.onView(ViewMatchers.withId(R.id.edtTitleAdd)).
                perform(ViewActions.clearText());
        Espresso.onView(ViewMatchers.withId(R.id.edtContentAdd)).
                perform(ViewActions.typeText("This is a great day!"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.imgAdd))
                .perform(ViewActions.click());
        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.tvAddNoteError), ViewMatchers.withText(R.string.note_text_error_title)))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        //Test add success
        Espresso.onView(ViewMatchers.withId(R.id.edtTitleAdd)).
                perform(ViewActions.typeText("Note 1"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.imgAdd))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText(Matchers.startsWith("Add success")))
                .inRoot(RootMatchers.withDecorView(Matchers.not(Matchers.is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
