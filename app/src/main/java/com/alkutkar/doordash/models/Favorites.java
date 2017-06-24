package com.alkutkar.doordash.models;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by harshalkutkar on 6/22/17.
 */

public class Favorites {
    public static final String SHARED_PREFS_NAME = "DOORDASH_FAV";
    ArrayList<Integer> idList;
    SharedPreferences prefs;

    private Context mContext;

    public Favorites(Context context) {
        this.mContext = context;
        prefs = mContext.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        idList =  new ArrayList (Arrays.asList(getFromPrefs()));
    }

    public boolean save() {
        Integer[] idArray = getFavorites().toArray(new Integer[getFavorites().size()]);
        return storeIntArray(idArray);
    }

    public void add(int i) {
        if (!getFavorites().contains(i)) {
            getFavorites().add(i);
        }
    }

    public void remove(int i) {
        idList.remove(Integer.valueOf(i));
    }

    public ArrayList<Integer> getFavorites() {
        return idList;
    }

    private boolean storeIntArray(Integer array[]){

        SharedPreferences.Editor edit= prefs.edit();
        edit.putInt("Count", array.length);
        int count = 0;
        for (Integer i: array){
            edit.putInt("IntValue_" + count++, i);
        }
        return edit.commit();
    }

    private Integer[] getFromPrefs(){
        Integer[] ret;
        int count = prefs.getInt("Count", 0);
        ret = new Integer[count];
        for (int i = 0; i < count; i++){
            ret[i] = prefs.getInt("IntValue_"+ i, i);
        }
        return ret;
    }
}
