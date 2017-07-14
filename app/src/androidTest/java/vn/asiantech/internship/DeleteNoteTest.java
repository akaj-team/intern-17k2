package vn.asiantech.internship;

import android.content.Intent;
import android.support.test.espresso.Espresso;
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

import static android.support.test.espresso.action.ViewActions.click;

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
        Intent intent = new Intent();
        mRule.launchActivity(intent);
        mItemNotes = mRule.getActivity().itemNoteList();
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
        for (int i = mItemNotes.size() - 1; i > 0; i--) {
            Espresso.onView(ViewMatchers.withId(R.id.recyclerViewNote)).perform(RecyclerViewActions.actionOnItemAtPosition(i, click()));
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
            Espresso.onView(ViewMatchers.withId(R.id.imgDelete)).perform(click());
        }
    }
}
