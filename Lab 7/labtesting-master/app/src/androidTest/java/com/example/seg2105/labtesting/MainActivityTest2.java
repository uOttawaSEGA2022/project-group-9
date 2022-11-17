package com.example.seg2105.labtesting;

import android.content.pm.ActivityInfo;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

public class MainActivityTest2 {

    @Rule
    public ActivityTestRule<MainActivty> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void emailIsInvalid() {
        onView(withID(R.id.username)).perform(typeText("user"), closeSoftKeyboard());
        onView(withID(R.id.Lastname)).perform(typeText("test"), closeSoftKeyboard());
        onView(withID(R.id.email)).perform(typeText("email@"), closeSoftKeyboard());
        onView(withID(R.id.loginBtn)).perform(click());
        onView(withText("Invalid Email")).check(matches(isDisplayed()));
    }

    @Test
    public void firstIsInvalid() {
        onView(withID(R.id.username)).perform(typeText("user"), closeSoftKeyboard());
        onView(withID(R.id.loginBtn)).perform(click());
        onView(withText("Invalid First Name")).check(matches(isDisplayed()));

    }


}
