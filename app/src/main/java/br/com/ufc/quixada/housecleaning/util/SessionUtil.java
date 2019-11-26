package br.com.ufc.quixada.housecleaning.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionUtil {

    public static void setCurrentUserId(Context context, String currentUserId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("session_settings", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("current_user_id", currentUserId);
        editor.commit();
    }

    public static String getCurrentUserId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("session_settings", Context.MODE_PRIVATE);

        String currentUserId = sharedPreferences.getString("current_user_id", "");

        return currentUserId;
    }

}
