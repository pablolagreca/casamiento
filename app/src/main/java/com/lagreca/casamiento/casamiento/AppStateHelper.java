package com.lagreca.casamiento.casamiento;

import android.content.Context;
import android.content.SharedPreferences;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class AppStateHelper {

    public static boolean isVideoSeen(Context context)
    {
        final SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.videoSeenKey), Context.MODE_PRIVATE);
        return sharedPref.getBoolean(context.getString(R.string.videoSeenKey), false);
    }

    public static void setVideoSeen(Context context, boolean videoSeen) {
        final SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.videoSeenKey), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(context.getString(R.string.videoSeenKey), videoSeen);
        editor.commit();
    }
}
