package com.pthomasdesigns.myhealth;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.pthomasdesigns.myhealth.rest.service.RestClient;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityTest {

    private String mUserId;
    private MockWebServer server;
    private String url;

    private static final String TAG = "LoginActivityTest";

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);

    @Before
    public void initUserId() throws Exception {
        mUserId = "user1";
        server = new MockWebServer();
        url = server.url("/demo/").toString();
        Log.d(TAG, "Mock server URL:" + url);
        RestClient.BASE_URL = url;
        //RestClient.getInstance().setBaseUrl(url);
        //server.start();
    }

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.pthomasdesigns.myhealth", appContext.getPackageName());
    }

    @Test
    public void typeValidUserIdAndLogin() {

        Intents.init();

        Log.d(TAG, "Mock server URL:" + url);
        server.enqueue(new MockResponse().setBody("{\"id\" : \"user1\", \"password\" : \"\", \"visits\" : 0, \"bills\" : 0, \"message\" : 0 }"));

        onView(allOf(withId(R.id.userid),isDisplayed())).perform(replaceText(mUserId), closeSoftKeyboard());
        onView(allOf(withId(R.id.sign_in_button), withText("Log in"), isDisplayed())).perform(click());

        intended(hasComponent(MainActivity.class.getName()));

        Intents.release();


    }

    @Test
    public void typeInvalidUserIdandLogin() {
        onView(withId(R.id.userid)).perform(typeText("usr"), closeSoftKeyboard());
        onView(allOf(withId(R.id.sign_in_button), withText("Log in"), isDisplayed())).perform(click());
        onView(withId(R.id.userid)).check(matches(hasErrorText("This username is incorrect")));
    }
}
