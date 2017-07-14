package vn.asiantech.internship;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import vn.asiantech.internship.note.NoteActivity;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;

/**
 * Created by datbu on 14-07-2017.
 */
@RunWith(AndroidJUnit4.class)
public class AddNoteTest {

    @Rule
    public ActivityTestRule<NoteActivity> mRule =
            new ActivityTestRule<>(NoteActivity.class);


    @Before
    public void setData() {
        Intent intent = new Intent();
        mRule.launchActivity(intent);

        Espresso.onView(ViewMatchers.withId(R.id.flNote)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.imgPick)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.imgSave)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.imgEdit)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
        Espresso.onView(ViewMatchers.withId(R.id.imgDelete)).check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));

        Espresso.onView(ViewMatchers.withId(R.id.imgAdd)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.tvOutput)).check(ViewAssertions.matches(ViewMatchers.withText(R.string.title_notefragment)));
    }

    @Test
    public void addNoteFragmentTest() {
        // Correct data
        String title = "trungnnq@gmail.com";
        String note = "123ab " + "\n" + "hello";
        Espresso.onView(ViewMatchers.withId(R.id.imgAdd)).perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.edtTitle))
                .perform(typeText(title), closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.edtNote))
                .perform(typeText(note), closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.imgSave)).perform(click());

        title = "datbuitan96@gmail.com";
        note = "ahihi" + "\n" + "ahuhu";
        Espresso.onView(ViewMatchers.withId(R.id.imgAdd)).perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.edtTitle))
                .perform(typeText(title), closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.edtNote))
                .perform(typeText(note), closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.imgSave)).perform(click());

        // No title
        title = "";
        note = "ahihi" + "\n" + "ahuhu";
        Espresso.onView(ViewMatchers.withId(R.id.imgAdd)).perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.edtTitle))
                .perform(typeText(title), closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.edtNote))
                .perform(typeText(note), closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.imgSave)).perform(click());

        // No note
        title = "datbuitan96@gmail.com";
        note = "";
        Espresso.onView(ViewMatchers.withId(R.id.imgAdd)).perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.edtTitle))
                .perform(typeText(title), closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.edtNote))
                .perform(typeText(note), closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.imgSave)).perform(click());

        // No content
        title = "";
        note = "";
        Espresso.onView(ViewMatchers.withId(R.id.imgAdd)).perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.edtTitle))
                .perform(typeText(title), closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.edtNote))
                .perform(typeText(note), closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.imgSave)).perform(click());
    }
}
