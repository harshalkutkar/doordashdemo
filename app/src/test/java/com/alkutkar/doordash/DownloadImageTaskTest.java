package com.alkutkar.doordash;

import android.widget.ImageView;

import com.alkutkar.doordash.api.DownloadImageTask;
import com.alkutkar.doordash.models.Restaurant;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.concurrent.ExecutionException;

import static junit.framework.Assert.assertTrue;

/**
 * Created by halkutkar on 6/26/17.
 */

@Config(constants = BuildConfig.class, sdk = 25, packageName = "com.alkutkar.doordash")
@RunWith(RobolectricTestRunner.class)
public class DownloadImageTaskTest {

    private MainActivity activity;
    public static final String RANDOM_IMAGE_URL  = "https://storage.googleapis.com/gweb-uniblog-publish-prod/static/blog/images/google.a51985becaa6.png";

    // @Before => JUnit 4 annotation that specifies this method should run before each test is run
    // Useful to do setup for objects that are needed in the test
    @Before
    public void setup() {
        // Convenience method to run MainActivity through the Activity Lifecycle methods:
        // onCreate(...) => onStart() => onPostCreate(...) => onResume()
        activity = Robolectric.setupActivity(MainActivity.class);
    }

    @Test
    public void testDownloadImage() {
        Restaurant restaurant = new Restaurant();
        restaurant.setCoverImageUrl(RANDOM_IMAGE_URL);
        ImageView imageView = new ImageView(activity);
        //create task
        DownloadImageTask asyncTask = new DownloadImageTask(imageView);
        //start task
        asyncTask.execute(restaurant.getCoverImageUrl());
        //wait for task code
        Robolectric.flushBackgroundThreadScheduler(); //from 3.0
        assertTrue(asyncTask.isSuccessful());
    }
}
