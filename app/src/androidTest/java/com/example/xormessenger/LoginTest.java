package com.example.xormessenger;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class LoginTest {
    @Rule
    public ActivityTestRule<Login> mActivityRule = new ActivityTestRule<>(Login.class);

    @Test
    public void loginUser() throws InterruptedException {
        onView(withId(R.id.username)).perform(typeText("hello"));
        onView(withId(R.id.password)).perform(typeText("world")).perform(closeSoftKeyboard());
        Thread.sleep(250);
        onView(withId(R.id.loginButton)).perform(click());
        onView(withText("Logging in...")).inRoot(new ToastMatcher()).check(matches(isDisplayed()));
    }
}
