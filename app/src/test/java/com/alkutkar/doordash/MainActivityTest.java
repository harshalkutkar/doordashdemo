package com.alkutkar.doordash;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.alkutkar.doordash.api.DoorDashApi;
import com.alkutkar.doordash.events.ViewRestaurantDetailEvent;
import com.alkutkar.doordash.models.Restaurant;
import com.alkutkar.doordash.ui.DetailFragment;
import com.alkutkar.doordash.ui.RestaurantListAdapter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowListView;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by harshalkutkar on 6/26/17.
 */
@Config(constants = BuildConfig.class, sdk = 25, packageName = "com.alkutkar.doordash")
@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {
    private MainActivity activity;
    private ListView listView;
    private List<String> transcript;
    private static final String TEST_IMAGE_URL = "https://cdn.doordash.com/static/img/doordash-square-red.jpg";

    @Before
    public void setUp() throws Exception {
        activity =   Robolectric.buildActivity(MainActivity.class)
                .create()
                .visible()
                .start()
                .resume()
                .get();
        listView = (ListView) activity.findViewById(R.id.list);
        transcript = new ArrayList<String>();
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

    @Test
    public void testListActivityTouchEvent(){
        Fragment listFragment = activity.getSupportFragmentManager()
                .findFragmentByTag("ListFragment");
        listView = (ListView) listFragment.getView().findViewById(R.id.list);
        assertNotNull(listFragment);
        assertNotNull(listView);
        //prepare the listview
        prepareListWithTwoItems(listView);
        //click on the first item
        listView.performItemClick(listView.getAdapter().getView(1, null, null), 1, listView.getItemIdAtPosition(1));
        //check that the restaurant details frag has started
        assert (activity.getSupportFragmentManager().findFragmentById(R.id.fragment_placeholder) instanceof DetailFragment);
    }

    private void prepareListWithTwoItems(ListView listView) {
        ArrayList<Restaurant> data = new ArrayList<Restaurant>();
        Restaurant r1 = new Restaurant();
        r1.setCoverImageUrl(TEST_IMAGE_URL);
        r1.setName("Restaurant_1");
        r1.setId(1);
        r1.setDescription("Restaurant_1");
        r1.setDeliveryFee("10");
        r1.setStatus("Open");
        data.add(r1);
        Restaurant r2 = new Restaurant();
        r2.setCoverImageUrl(TEST_IMAGE_URL);
        r2.setName("Restaurant_2");
        r2.setId(2);
        r2.setStatus("Closed");
        r2.setDescription("Restaurant_2");
        r2.setDeliveryFee("11");
        data.add(r2);

        listView.setAdapter(new RestaurantListAdapter(data,activity));
    }

}
