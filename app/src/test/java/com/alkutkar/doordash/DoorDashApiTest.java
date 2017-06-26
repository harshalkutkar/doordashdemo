package com.alkutkar.doordash;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.test.AndroidTestCase;
import android.test.mock.MockContext;

import com.alkutkar.doordash.api.DoorDashApi;
import com.alkutkar.doordash.api.GsonRequest;
import com.alkutkar.doordash.events.FetchRestaurantsEvent;
import com.alkutkar.doordash.events.ViewRestaurantDetailEvent;
import com.alkutkar.doordash.models.Favorites;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.apache.http.client.HttpClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import de.greenrobot.event.EventBus;

import static com.alkutkar.doordash.models.Favorites.SHARED_PREFS_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.doAnswer;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.spy;

/**
 * Created by harshalkutkar on 6/23/17.
 */

@RunWith(RobolectricTestRunner.class)
@PrepareForTest({Volley.class, EventBus.class,RequestQueue.class})
@Config(constants = BuildConfig.class)
public class DoorDashApiTest {

    private DoorDashApi doorDashApi;

    @Mock(name="mContext")
    private Context mContext;
    @Mock
    private EventBus eventBus;
    @Mock
    private RequestQueue queue;
    @Mock
    private PackageInfo packageInfo;
    @Mock
    private PackageManager manager;


    @Before
    public void setup() throws PackageManager.NameNotFoundException {
        MockitoAnnotations.initMocks(this);
        queue  = mock(RequestQueue.class);
        eventBus = mock(EventBus.class);
        mContext = mock(MockContext.class);
        when(mContext.getPackageName()).thenReturn("com.alkutkar.doordash");
        PowerMockito.mockStatic(Volley.class);
        when(mContext.getPackageManager()).thenReturn(manager);
        when(manager.getPackageInfo("com.alkutkar.doordash",0)).thenReturn(packageInfo);
        doorDashApi = Mockito.spy(new DoorDashApi(mContext));

    }

    @Test
    public void onEvent_FetchRestaurantsEvent() {
       doorDashApi.onEvent(new FetchRestaurantsEvent(0.0f,0.0f,false));
       verify(doorDashApi, times(1)).fetchRestaurantsInArea(0.0f,0.0f,false);
    }

    @Test
    public void onEvent_ViewRestaurantDetailsEvent() {
        doorDashApi.onEvent(new ViewRestaurantDetailEvent(1));
        verify(doorDashApi,times(1)).fetchRestaurantDetailsForId(1);
    }


}
