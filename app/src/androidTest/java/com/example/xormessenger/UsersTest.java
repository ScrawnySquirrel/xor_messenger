package com.example.xormessenger;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class UsersTest {
    @Rule
    public ActivityTestRule<Users> mActivityRule = new ActivityTestRule<>(Users.class);

    @Test
    public void usersTest() {
        try {
            Thread.sleep(1000);
            onView(withId(R.id.usersList)).check(matches(isDisplayed()));
        } catch (Exception NoMatchingViewException) {
            onView(withId(R.id.noUsersText)).check(matches(isDisplayed()));
        }
    }
}




