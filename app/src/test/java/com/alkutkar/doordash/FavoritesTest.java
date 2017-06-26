package com.alkutkar.doordash;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.test.AndroidTestCase;

import com.alkutkar.doordash.models.Favorites;

import org.junit.Before;
import org.junit.Test;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.internal.primitivesupport.PrimitiveWrapper;

import java.util.ArrayList;
import java.util.Set;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;

import static com.alkutkar.doordash.models.Favorites.SHARED_PREFS_NAME;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by harshalkutkar on 6/23/17.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({/* Static Classes I am Mocking */})
public class FavoritesTest{

    ArrayList<Integer> idList;
    @Mock(name = "mContext")
    private Context mContext;
    @Mock
    private SharedPreferences preferences;
    Favorites favorites;


    @Before
    public void setup(){
        preferences = mock(SharedPreferences.class);
        PowerMockito.when(preferences.getInt("Count",0)).thenReturn(0);
        PowerMockito.when(mContext.getSharedPreferences(SHARED_PREFS_NAME,Context.MODE_PRIVATE)).thenReturn(preferences);
    }

    @Test
    public void testAddingObjectToFavorites(){
        favorites = new Favorites(mContext);
        favorites.add(1);
        assertEquals(1,favorites.getFavorites().size());

    }
    @Test
    public void testSaveFavorites(){
        //Mock your objects like other "normally" mocked objects
        preferences = mock(SharedPreferences.class);
        when(mContext.getSharedPreferences(anyString(),anyInt())).thenReturn(preferences);
        when(preferences.getInt(anyString(),anyInt())).thenReturn(0);
        when(preferences.edit()).thenReturn(mock(SharedPreferences.Editor.class));
        when(preferences.edit().putInt(anyString(),anyInt())).thenReturn(mock(SharedPreferences.Editor.class));
        when(preferences.edit().commit()).thenReturn(true);
        favorites = new Favorites(mContext);
        favorites.add(1);
        assertEquals(1,favorites.getFavorites().size());
        assertTrue(favorites.save());
    }

    @Test
    public void testRemove(){
        favorites = new Favorites(mContext);
        favorites.add(1);
        favorites.add(2);
        favorites.add(3);
        favorites.remove(1);
        favorites.remove(3);
        assertEquals(1,favorites.getFavorites().size());
        assertEquals(2,favorites.getFavorites().get(0).intValue());
    }

}
