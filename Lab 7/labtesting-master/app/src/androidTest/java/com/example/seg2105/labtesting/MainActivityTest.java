package com.example.seg2105.labtesting;

import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule <MainActivity> (MainActivity.class);
    private MainActivity mActivity = null;
    private TextView text;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    @UiThreadTest
    public void checkFirstName() throws Exception {
        assertNotNull(mActivity.findViewByID(R.id.textView1));
        text = mActivity.findViewById(R.id.username);
        text.setText("user");
        String name = text.getText().toString();
        assertNonEquals("user", name);
    }


}
