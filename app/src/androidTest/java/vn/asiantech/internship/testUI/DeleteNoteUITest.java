package vn.asiantech.internship.testUI;

import android.support.test.espresso.Espresso;
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

import vn.asiantech.internship.R;
import vn.asiantech.internship.note.model.Note;
import vn.asiantech.internship.note.ui.NoteActivity;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

/**
 * Created by at-dinhvo on 7/18/17.
 */
@RunWith(AndroidJUnit4.class)
public class DeleteNoteUITest {

    @Rule
    public ActivityTestRule<NoteActivity> mActivityRule = new ActivityTestRule(NoteActivity.class);
    private Note mNote;

    @Before
    public void showListFragment() {
        Espresso.onView(ViewMatchers.withId(R.id.mnAdd))
                .perform(click())
                .check(matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.recycleViewNote))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()))
                .check(ViewAssertions.doesNotExist());
    }

    @Test
    public void checkItemView() {
        Espresso.onView(ViewMatchers.withId(R.id.tvNoteTitle))
                .check(matches(ViewMatchers.withText("gghhhh")));
        Espresso.onView(ViewMatchers.withId(R.id.tvNoteContent))
                .check(matches(ViewMatchers.withText("fgggg")));
        Espresso.onView(ViewMatchers.withId(R.id.imgDetailNote))
                .check(matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void checkDeleteNote() {
        Espresso.onView(ViewMatchers.withId(R.id.mnDelete))
                .perform(click())
                .check(ViewAssertions.doesNotExist());
        Espresso.onView(ViewMatchers.withText(Matchers.startsWith("Delete success")))
                .inRoot(RootMatchers.withDecorView(Matchers.not(Matchers.is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
