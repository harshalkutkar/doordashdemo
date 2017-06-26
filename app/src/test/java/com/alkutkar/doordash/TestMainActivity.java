package com.alkutkar.doordash;

import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;

import static org.robolectric.Shadows.shadowOf;

/**
 * Created by harshalkutkar on 6/26/17.
 */
@Config(constants = BuildConfig.class, sdk = 21, packageName = "com.alkutkar.doordash")
@RunWith(RobolectricTestRunner.class)
public class TestMainActivity {
    private MainActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(MainActivity.class).create().get();
    }

    @Test
    public void pressingTheButtonShouldStartTheListActivity() throws Exception {
        ShadowActivity shadowActivity = shadowOf(activity);
    }
}
