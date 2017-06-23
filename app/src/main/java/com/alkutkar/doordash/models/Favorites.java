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
    public static String SHARED_PREFS_NAME = "DOORDASH_FAV";
    ArrayList<Integer> idList;

    private Context mContext;

    public Favorites(Context context) {
        this.mContext = context;
        idList =  new ArrayList (Arrays.asList(getFromPrefs()));

    }

    public void save() {
        Integer[] idArray = idList.toArray(new Integer[idList.size()]);
        storeIntArray(idArray);
    }

    public void add(int i) {
        if (!idList.contains(Integer.valueOf(i))) {
            idList.add(i);
        }
    }

    public void remove(int i) {
        idList.remove(Integer.valueOf(i));
    }

    public ArrayList<Integer> getFavorites() {
        return idList;
    }

    public void storeIntArray(Integer array[]){
        SharedPreferences.Editor edit= mContext.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE).edit();
        edit.putInt("Count", array.length);
        int count = 0;
        for (Integer i: array){
            edit.putInt("IntValue_" + count++, i);
        }
        edit.commit();
    }

    public Integer[] getFromPrefs(){
        Integer[] ret;
        SharedPreferences prefs = mContext.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        int count = prefs.getInt("Count", 0);
        ret = new Integer[count];
        for (int i = 0; i < count; i++){
            ret[i] = prefs.getInt("IntValue_"+ i, i);
        }
        return ret;
    }
}
