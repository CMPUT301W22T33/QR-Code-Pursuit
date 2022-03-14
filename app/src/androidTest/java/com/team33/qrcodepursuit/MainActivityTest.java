/*
agenda:
clicking menus indeed switches fragments
program starts on ScanFragment
 */

package com.team33.qrcodepursuit;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Test
    public void startsOnScan() {
        onView(withId(R.id.scannerview)).check(matches(isDisplayed()));
    }

    @Test
    public void changeFrag() {
        // in the future, change this to other fragments
        onView(withId(R.id.bottomnavigation_menu_scan)).perform(click());
        onView(withId(R.id.container)).check(matches(isDisplayed()));
    }

}
