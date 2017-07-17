package vn.asiantech.internship;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.models.Note;
import vn.asiantech.internship.ui.main.NoteActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by ducle on 17/07/2017.
 */
@RunWith(AndroidJUnit4.class)
public class MainNoteUITest {

    @Rule
    public ActivityTestRule<NoteActivity> mActivityRule = new ActivityTestRule<>(NoteActivity.class);

    @Before
    public void initData() {
        List<Note> notes = new ArrayList<>();
        Note note;
        for (int i = 0; i < 10; i++) {
            note = new Note();
            note.setTitle("note " + i);
            note.setContent("content " + 1);
            notes.add(note);
        }
    }

    @Test
    public void checkShowMainFragment() {
        onView(withId(R.id.llMain)).check(matches(isDisplayed()));
//        onView(withId(R.id.tvTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.imgAdd)).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerViewNote)).check(matches(isDisplayed()));
    }

    @Test
    public void checkClickAdd() {
        onView(withId(R.id.imgAdd)).perform(click());
        onView(withId(R.id.llAdd)).check(matches(isDisplayed()));
        onView(withId(R.id.tvTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.imgSave)).check(matches(isDisplayed()));
        onView(withId(R.id.imgPicPhoto)).check(matches(isDisplayed()));
        onView(withId(R.id.imgNote)).check(matches(isDisplayed()));
        onView(withId(R.id.edtTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.edtContent)).check(matches(isDisplayed()));
    }

    @Test
    public void checkItemClick() {
        onView(withId(R.id.recyclerViewNote)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.llDetail)).check(matches(isDisplayed()));
    }
}
