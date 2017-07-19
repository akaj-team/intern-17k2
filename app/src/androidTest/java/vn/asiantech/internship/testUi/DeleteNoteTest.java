package vn.asiantech.internship.testUi;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.note.database.NoteDatabase;
import vn.asiantech.internship.note.model.Note;
import vn.asiantech.internship.note.ui.NoteActivity;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

/**
 * Created by at-dinhvo on 7/18/17.
 */
@RunWith(AndroidJUnit4.class)
public class DeleteNoteTest {

    @Rule
    public ActivityTestRule<NoteActivity> mActivityRule = new ActivityTestRule(NoteActivity.class);
    private List<Note> mNotes;

    @Before
    public void getNoteData() {
        NoteDatabase database = new NoteDatabase(mActivityRule.getActivity());
        database.open();
        mNotes = database.getAllData();
        if (mNotes.size() == 0) {
            Espresso.onView(ViewMatchers.withId(R.id.mnAdd))
                    .perform(click())
                    .check(ViewAssertions.doesNotExist());
            addNewNote();
            mNotes = database.getAllData();
        }
        database.close();
        Espresso.onView(ViewMatchers.withId(R.id.recycleViewNote))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()))
                .check(ViewAssertions.doesNotExist());
    }

    public void addNewNote() {
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

    @Test
    public void checkItemView() {
        Note note = mNotes.get(0);
        Espresso.onView(ViewMatchers.withId(R.id.edtNoteTitle))
                .check(matches(ViewMatchers.withText(note.getTitle())));
        Espresso.onView(ViewMatchers.withId(R.id.edtNoteContent))
                .check(matches(ViewMatchers.withText(note.getContent())));
        Espresso.onView(ViewMatchers.withId(R.id.tvDateTimeAdd))
                .check(matches(ViewMatchers.withText(note.getDatetime())));
        Espresso.onView(ViewMatchers.withId(R.id.imgDetailNote))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkDeleteNote() {
        Espresso.onView(ViewMatchers.withId(R.id.mnDelete))
                .perform(click())
                .check(ViewAssertions.doesNotExist());
    }
}
