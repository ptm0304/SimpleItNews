package com.example.simpleitnews;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.simpleitnews.view.MainActivityKt;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4.class)
public class MainScreenTest {
    @Rule
    public ActivityTestRule<MainActivityKt> activityRule
            = new ActivityTestRule<>(MainActivityKt.class, true, false);

    @Test
    public void testInitialScreen() {
        activityRule.launchActivity(null);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.main_rv)).check(matches(isDisplayed()));
    }
}
