package vn.asiantech.internship;

import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import vn.asiantech.internship.models.NoteItem;
import vn.asiantech.internship.ui.main.NoteActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 7/12/2017.
 */
@RunWith(AndroidJUnit4.class)
public class DetailNoteTest {
    @Rule
    public ActivityTestRule<NoteActivity> mActivityTestRule = new ActivityTestRule<NoteActivity>(NoteActivity.class);
    List<NoteItem> mNotes;

    @Test
    public void testDetailFragment() {
        for (int i = 0; i < mNotes.size(); i++) {
            openNoteActivity();
            onView(withId(R.id.recyclerViewNote)).perform(RecyclerViewActions.actionOnItemAtPosition(i, click()));
            onView(withId(R.id.tvTitle)).check(matches(withText(R.string.title_detail)));
            onView(withId(R.id.imgAddImage)).check(matches(not(isDisplayed())));
            onView(withId(R.id.imgAddNote)).check(matches(not(isDisplayed())));
            onView(withId(R.id.imgEdit)).check(matches(isDisplayed()));
            onView(withId(R.id.imgDelete)).check(matches(isDisplayed()));
            onView(withId(R.id.imgSave)).check(matches(not(isDisplayed())));
            onView(withId(R.id.edtNoteTitle)).check(matches(withText(mNotes.get(i).getTitle())));
            onView(withId(R.id.edtNoteContent)).check(matches(withText(mNotes.get(i).getContent())));
            onView(withId(R.id.tvNoteTime)).check(matches(withText(mNotes.get(i).getStringTime())));
        }
    }

    @Before
    public void openNoteActivity() {
        Intent intent = new Intent();
        mActivityTestRule.launchActivity(intent);
        mNotes = mActivityTestRule.getActivity().getNotes();
        onView(withId(R.id.llMain)).check(matches(isDisplayed()));
        onView(withId(R.id.tvTitle)).check(matches(withText(R.string.title_note)));
        onView(withId(R.id.imgAddImage)).check(matches(not(isDisplayed())));
        onView(withId(R.id.imgAddNote)).check(matches(isDisplayed()));
        onView(withId(R.id.imgEdit)).check(matches(not(isDisplayed())));
        onView(withId(R.id.imgDelete)).check(matches(not(isDisplayed())));
        onView(withId(R.id.recyclerViewNote)).check(matches(isDisplayed()));
    }
}
