package com.sukinsan.pixelgame.utils;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Created by victor on 9/5/2015.
 */
public class SystemUtils {
    public static final int FLAG_FULLSCREEN =  View.SYSTEM_UI_FLAG_LAYOUT_STABLE |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN |View.SYSTEM_UI_FLAG_IMMERSIVE;
    public static final int FLAG_FULLSCREEN_RESTORE = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

    private static Toast toast;

    public static void toast(Context context,String message){
        if(toast != null){
            toast.cancel();
        }
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
        Log.i(context.getClass().getSimpleName(), message);
    }

    public static void initFullScreen(AppCompatActivity activity){
        activity.getWindow().getDecorView().getRootView().setSystemUiVisibility(FLAG_FULLSCREEN);
    }

    public static void restoreFullScreen(AppCompatActivity activity,boolean hasFocus){
        if(hasFocus) {
            activity.getWindow().getDecorView().getRootView().setSystemUiVisibility(FLAG_FULLSCREEN_RESTORE);
        }
    }
}
