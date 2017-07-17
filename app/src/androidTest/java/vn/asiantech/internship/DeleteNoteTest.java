package vn.asiantech.internship;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import vn.asiantech.internship.note.ItemNote;
import vn.asiantech.internship.note.NoteActivity;

/**
 * Created by datbu on 14-07-2017.
 */
@RunWith(AndroidJUnit4.class)
public class DeleteNoteTest {

    @Rule
    public ActivityTestRule<NoteActivity> mRule = new ActivityTestRule<>(NoteActivity.class);
    private List<ItemNote> mItemNotes;

    @Before
    public void setData() {
        mItemNotes = mRule.getActivity().itemNoteList();
    }

    private void prepareTest() {
        Espresso.onView(ViewMatchers.withId(R.id.flNote)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.imgPick)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.imgSave)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.imgEdit)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.imgDelete)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.imgAdd)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.tvOutput)).check(ViewAssertions.matches(ViewMatchers.withText(R.string.title_notefragment)));
    }

    @Test
    public void testDeleteItemNote() {
        prepareTest();
        for (int i = mItemNotes.size() - 1; i == 0; i--) {
            Espresso.onView(ViewMatchers.withId(R.id.recyclerViewNote))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(i, ViewActions.click()))
                    .check(ViewAssertions.doesNotExist());
            Espresso.onView(ViewMatchers.withId(R.id.flNote)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
            Espresso.onView(ViewMatchers.withId(R.id.imgPick)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
            Espresso.onView(ViewMatchers.withId(R.id.imgSave)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
            Espresso.onView(ViewMatchers.withId(R.id.imgEdit)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
            Espresso.onView(ViewMatchers.withId(R.id.imgDelete)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
            Espresso.onView(ViewMatchers.withId(R.id.imgAdd)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
            Espresso.onView(ViewMatchers.withId(R.id.tvOutput)).check(ViewAssertions.matches(ViewMatchers.withText(R.string.title_detail)));
            Espresso.onView(ViewMatchers.withId(R.id.edtNote))
                    .check(ViewAssertions.matches(ViewMatchers
                            .withText(mItemNotes.get(i).getNote())));
            Espresso.onView(ViewMatchers.withId(R.id.edtTitle))
                    .check(ViewAssertions.matches(ViewMatchers
                            .withText(mItemNotes.get(i).getTitle())));
            Espresso.onView(ViewMatchers.withId(R.id.tvTime))
                    .check(ViewAssertions.matches(ViewMatchers
                            .withText(mItemNotes.get(i).getTime())));
            Espresso.onView(ViewMatchers.withId(R.id.imgDelete))
                    .perform(ViewActions.click())
                    .check(ViewAssertions.matches(ViewMatchers.isClickable()));
        }
    }
}
