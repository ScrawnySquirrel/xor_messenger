package com.example.xormessenger;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.Instant;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class RegisterTest {
    @Rule
    public ActivityTestRule<Register> mActivityRule = new ActivityTestRule<>(Register.class);

    @Test
    public void registerUser() throws InterruptedException {
        long now = Instant.now().toEpochMilli();
        onView(withId(R.id.username)).perform(typeText("user"+now));
        onView(withId(R.id.password)).perform(typeText("testing123")).perform(closeSoftKeyboard());
        Thread.sleep(250);
        onView(withId(R.id.registerButton)).perform(click());
        onView(withText("registration successful")).inRoot(new ToastMatcher()).check(matches(isDisplayed()));
    }

    @Test
    public void goToLogin() {
        onView(withId(R.id.login)).perform(click());
    }
}
