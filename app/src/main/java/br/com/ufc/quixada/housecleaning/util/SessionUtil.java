package br.com.ufc.quixada.housecleaning.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionUtil {

    public static int getCurrentUserId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("session_settings", Context.MODE_PRIVATE);
        int currentUserId = sharedPreferences.getInt("user_id", 0);

        return currentUserId;
    }

}
