package vn.asiantech.internship;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
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

import java.util.List;

import vn.asiantech.internship.notesqlite.Note;
import vn.asiantech.internship.notesqlite.NoteActivity;
import vn.asiantech.internship.notesqlite.NoteSqlite;

/**
 * Author AsianTech Inc.
 * Created by sony on 13/07/2017.
 */
@RunWith(AndroidJUnit4.class)
public class EditNoteTest {
    private List<Note> mNotes;

    @Rule
    public ActivityTestRule<NoteActivity> mActivityRule = new ActivityTestRule<>(NoteActivity.class);

    @Before
    public void getData() {
        NoteSqlite database = new NoteSqlite(mActivityRule.getActivity());
        database.open();
        mNotes = database.getNotes();
        database.close();
        Espresso.onView(ViewMatchers.withId(R.id.flContainer)).
                check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.recyclerViewNote)).
                perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
        Espresso.onView(ViewMatchers.withId(R.id.llEditNote)).
                check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void dataShowCheck() {
        Espresso.onView(ViewMatchers.withId(R.id.edtTitleEdit)).
                check(ViewAssertions.matches(ViewMatchers.withText(mNotes.get(0).getTitle())));
        Espresso.onView(ViewMatchers.withId(R.id.edtContentEdit)).
                check(ViewAssertions.matches(ViewMatchers.withText(mNotes.get(0).getContent())));
        Espresso.onView(ViewMatchers.withId(R.id.tvTime)).
                check(ViewAssertions.matches(ViewMatchers.withText(mNotes.get(0).getDay() + "\n" + mNotes.get(0).getDayOfWeek() + " " + mNotes.get(0).getMonth() + "\n" + mNotes.get(0).getHour())));

        Espresso.onView(ViewMatchers.withId(R.id.imgEditNote))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.edtTitleEdit)).
                perform(ViewActions.clearText());
        Espresso.onView(ViewMatchers.withId(R.id.imgSave))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText(Matchers.startsWith("Inquire enter title")))
                .inRoot(RootMatchers.withDecorView(Matchers.not(Matchers.is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.edtTitleEdit)).
                perform(ViewActions.typeText("Note 111111"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.edtContentEdit)).
                perform(ViewActions.clearText());
        Espresso.onView(ViewMatchers.withId(R.id.imgSave))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText(Matchers.startsWith("Inquire enter content")))
                .inRoot(RootMatchers.withDecorView(Matchers.not(Matchers.is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.edtContentEdit)).
                perform(ViewActions.typeText("I did it :)))"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.imgSave))
                .perform(ViewActions.click());
    }
}
