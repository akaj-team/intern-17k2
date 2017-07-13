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
import java.util.Random;

import vn.asiantech.internship.models.NoteItem;
import vn.asiantech.internship.ui.main.NoteActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 7/13/2017.
 */
@RunWith(AndroidJUnit4.class)
public class EditNoteTest {

    @Rule
    public ActivityTestRule<NoteActivity> mActivityTestRule = new ActivityTestRule<NoteActivity>(NoteActivity.class);
    List<NoteItem> mNotes;

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

    @Test
    public void checkEditNote() {
        Random random = new Random();
        int pos = random.nextInt(mNotes.size());
        onView(withId(R.id.recyclerViewNote)).perform(RecyclerViewActions.actionOnItemAtPosition(pos, click()));
        onView(withId(R.id.tvTitle)).check(matches(withText(R.string.title_detail)));
        onView(withId(R.id.imgAddImage)).check(matches(not(isDisplayed())));
        onView(withId(R.id.imgAddNote)).check(matches(not(isDisplayed())));
        onView(withId(R.id.imgEdit)).check(matches(isDisplayed()));
        onView(withId(R.id.imgDelete)).check(matches(isDisplayed()));
        onView(withId(R.id.imgSave)).check(matches(not(isDisplayed())));
        onView(withId(R.id.edtNoteTitle)).check(matches(not(isEnabled())));
        onView(withId(R.id.edtNoteTitle)).check(matches(withText(mNotes.get(pos).getTitle())));
        onView(withId(R.id.edtNoteContent)).check(matches(withText(mNotes.get(pos).getContent())));
        onView(withId(R.id.edtNoteContent)).check(matches(not(isEnabled())));
        onView(withId(R.id.tvNoteTime)).check(matches(withText(mNotes.get(pos).getStringTime())));
        onView(withId(R.id.imgEdit)).perform(click());
        onView(withId(R.id.edtNoteTitle)).check(matches(isEnabled()));
        onView(withId(R.id.edtNoteContent)).check(matches(isEnabled()));

        //Non Input
        String title = "";
        String content = "";
        setText(R.id.edtNoteTitle, title);
        setText(R.id.edtNoteContent, content);
        onView(withId(R.id.imgSave)).perform(click());
        onView(withText(R.string.validate))
                .inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

        //Non title
        title = "";
        content = "asdhaskjdh";
        setText(R.id.edtNoteTitle, title);
        setText(R.id.edtNoteContent, content);
        onView(withId(R.id.imgSave)).perform(click());
        onView(withText(R.string.validate))
                .inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

        //Non content
        title = "sadasd";
        content = "";
        setText(R.id.edtNoteTitle, title);
        setText(R.id.edtNoteTitle, content);
        onView(withId(R.id.imgSave)).perform(click());
        onView(withText(R.string.validate))
                .inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
        //Correct
        title = "sadasd";
        content = "asjdhaskjdhasd";
        setText(R.id.edtNoteTitle, title);
        setText(R.id.edtNoteTitle, content);
        onView(withId(R.id.imgSave)).perform(click());
    }

    private void setText(int id, String newText) {
        onView(withId(id)).perform(clearText());
        onView(withId(id)).perform(typeText(newText), closeSoftKeyboard());
    }
}
