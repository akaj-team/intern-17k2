package vn.asiantech.internship.testUI;

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
import vn.asiantech.internship.note.ui.NoteActivity;

/**
 * Created by at-dinhvo on 12/07/2017.
 */
@RunWith(AndroidJUnit4.class)
public class AddNoteTest {

    @Rule
    private ActivityTestRule<NoteActivity> mActivityRule = new ActivityTestRule(NoteActivity.class);

    @Before
    public void showUI() {
        Espresso.onView(ViewMatchers.withId(R.id.imgAddNote))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

    }

    @Test
    public void checkInputData() {
        Espresso.onView(ViewMatchers.withId(R.id.edtNoteTitle)).perform(ViewActions.typeText("Demo01"),
                ViewActions.closeSoftKeyboard());

    }
}
