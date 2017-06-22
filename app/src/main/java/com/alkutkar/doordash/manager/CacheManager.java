package com.alkutkar.doordash.manager;

import android.content.Context;

import com.alkutkar.doordash.models.Restaurant;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by harshalkutkar on 6/21/17.
 */

public class CacheManager {
    public static void createCachedFile (Context context, String key, ArrayList<Restaurant> restaurants) throws IOException {
        for (Restaurant r : restaurants) {
            FileOutputStream fos = context.openFileOutput (key, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream (fos);
            oos.writeObject (r);
            oos.close ();
            fos.close ();
        }
    }

    public static Object readCachedFile (Context context, String key) throws IOException, ClassNotFoundException {
        FileInputStream fis = context.openFileInput (key);
        ObjectInputStream ois = new ObjectInputStream (fis);
        Object object = ois.readObject ();
        return object;
    }

}
