package com.alkutkar.doordash;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alkutkar.doordash.models.Restaurant;
import com.alkutkar.doordash.ui.RestaurantListAdapter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowListView;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static org.assertj.core.api.Java6Assertions.assertThat;
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

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(MainActivity.class).create().get();
        listView = new ListView(RuntimeEnvironment.application);
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
        ShadowListView shadowListView = prepareListWithThreeItems();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                transcript.add("clicked on item " + position);
            }
        });
        shadowListView.clickFirstItemContainingText("Restaurant_1");
        assertThat(transcript).containsExactly("clicked on item 0");
    }

    private ShadowListView prepareListWithThreeItems() {
        ArrayList<Restaurant> data = new ArrayList<Restaurant>();
        Restaurant r1 = new Restaurant();
        r1.setCoverImageUrl(DownloadImageTaskTest.RANDOM_IMAGE_URL);
        r1.setName("Restaurant_1");
        r1.setId(1);
        r1.setDescription("Restaurant_1");
        r1.setDeliveryFee("10");
        r1.setStatus("Open");
        data.add(r1);
        Restaurant r2 = new Restaurant();
        r2.setCoverImageUrl(DownloadImageTaskTest.RANDOM_IMAGE_URL);
        r2.setName("Restaurant_2");
        r2.setId(2);
        r2.setStatus("Closed");
        r2.setDescription("Restaurant_2");
        r2.setDeliveryFee("11");
        data.add(r2);

        listView.setAdapter(new RestaurantListAdapter(data,activity));
        shadowOf(listView).populateItems();
        return shadowOf(listView);
    }

}
