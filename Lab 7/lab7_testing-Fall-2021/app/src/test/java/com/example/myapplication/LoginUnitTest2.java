package com.example.myapplication;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import android.content.Context;

import org.junit.Test;
import org.mockito.Mock;

public class LoginUnitTest2 {

    private static final String VALID_STRING = "Login was successful";
    private static final String INVAlID_STRING = "Invalid login!";

    @Mock
    Context mMockContext;

    @Test
    public void readStringFromContext_LocalizedString() {
        LoginActivity myObjectUnderTest = new LoginActivity(mMockContext);

        //..WHEN THE STRING IS RETURNED FROM THE OBJECT UNDER TEST
        String result = myObjectUnderTest.validate("admin", "admin");

        //..THEN THE RESULT SHOULD BE THE EXPECTED ONE
        assertThat(result, is(VALID_STRING));
    }

    @Test
    public void readStringFromContext_ANDTEST() {
        LoginActivity myObjectUnderTest = new LoginActivity(mMockContext);

        //...when the string is returned from the object under test...
        String result = myObjectUnderTest.validate("notadmin", "notadmin");


        //...then the result should be the expected one
        assertThat(result, is(INVAlID_STRING));
    }

}
