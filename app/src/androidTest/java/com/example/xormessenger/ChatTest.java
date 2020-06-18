package com.example.xormessenger;

import android.view.View;
import android.view.ViewGroup;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
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
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ChatTest {
    @Rule
    public ActivityTestRule<Chat> mActivityRule = new ActivityTestRule<>(Chat.class);

    @Test
    public void sendNormalMsgTest() throws InterruptedException {
        String testMsg = String.valueOf(Instant.now().toEpochMilli());
        onView(withId(R.id.messageArea)).perform(typeText(testMsg));
        Thread.sleep(250);
        onView(withId(R.id.sendButton)).perform(click());
        onView(nthChildOf(withId(R.id.layout1))).check(matches(withText(testMsg)));
    }

    @Test
    public void sendEncryptedMsgTest() throws InterruptedException {
        String testMsg = "<Encrypted Message>";
        onView(withId(R.id.encryptToggle)).perform(click());
        onView(withId(R.id.messageArea)).perform(typeText("this is a test message"));
        Thread.sleep(250);
        onView(withId(R.id.sendButton)).perform(click());
        onView(nthChildOf(withId(R.id.layout1))).check(matches(withText(testMsg)));
    }

    public static Matcher<View> nthChildOf(final Matcher<View> parentMatcher) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {

            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view.getParent() instanceof ViewGroup)) {
                    return parentMatcher.matches(view.getParent());
                }

                ViewGroup group = (ViewGroup) view.getParent();
                int lastChild = group.getChildCount();
                return parentMatcher.matches(view.getParent()) && group.getChildAt(lastChild-1).equals(view);
            }
        };
    }
}
