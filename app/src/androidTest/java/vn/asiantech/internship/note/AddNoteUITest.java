package vn.asiantech.internship.note;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import vn.asiantech.internship.R;
import vn.asiantech.internship.ui.main.NoteActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.StringStartsWith.startsWith;

/**
 * Created by ducle on 17/07/2017.
 */
@RunWith(AndroidJUnit4.class)
public class AddNoteUITest {
    @Rule
    public ActivityTestRule<NoteActivity> mActivityRule = new ActivityTestRule<>(NoteActivity.class);

    @Before
    public void checkShowAddFragment() {
        onView(withId(R.id.imgAdd)).perform(click());
        onView(withId(R.id.llAdd)).check(matches(isDisplayed()));
        onView(withId(R.id.tvTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.imgPicPhoto)).check(matches(isDisplayed()));
        onView(withId(R.id.imgSave)).check(matches(isDisplayed()));
        onView(withId(R.id.imgNote)).check(matches(isDisplayed()));
        onView(withId(R.id.edtTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.edtContent)).check(matches(isDisplayed()));
    }

    @Test
    public void checkClickSave() {
        onView(withId(R.id.edtTitle)).perform(typeText("xxx"), closeSoftKeyboard());
        onView(withId(R.id.edtContent)).perform(typeText("xxx xxx"), closeSoftKeyboard());
        onView(withId(R.id.imgSave)).perform(click());
        onView(withId(R.id.llMain)).check(matches(isDisplayed()));
    }

    @Test
    public void checkToastWhenBlankContent() {
        onView(withId(R.id.edtTitle)).perform(typeText("xxx"), closeSoftKeyboard());
        onView(withId(R.id.imgSave)).perform(click());

        onView(withText(startsWith("vui long nhap day du")))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkToastWhenBlankTitle() {
        onView(withId(R.id.edtContent)).perform(typeText("xxx"), closeSoftKeyboard());
        onView(withId(R.id.imgSave)).perform(click());

        onView(withText(startsWith("vui long nhap day du")))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }
}
