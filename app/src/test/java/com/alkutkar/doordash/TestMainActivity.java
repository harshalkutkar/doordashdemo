package com.alkutkar.doordash;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;

import static junit.framework.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by harshalkutkar on 6/26/17.
 */
@Config(constants = BuildConfig.class, sdk = 25, packageName = "com.alkutkar.doordash")
@RunWith(RobolectricTestRunner.class)
public class TestMainActivity {
    private MainActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(MainActivity.class).create().get();
    }

    @Test
    public void testLoadDefaultFragment() {
        Fragment listFragment = activity.getSupportFragmentManager()
                .findFragmentByTag("ListFragment");
        assertTrue(listFragment instanceof com.alkutkar.doordash.ui.ListFragment);
    }

    @Test
    public void testFavoritesOpenFragment(){
        NavigationView view = (NavigationView) activity.findViewById(R.id.nav_view);
        view.setCheckedItem(R.id.nav_favorites);
        Fragment listFragment = activity.getSupportFragmentManager()
                .findFragmentByTag("ListFragment");
        assertTrue(listFragment instanceof com.alkutkar.doordash.ui.ListFragment);
    }
}
