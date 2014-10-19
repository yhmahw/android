package me.gethelloworld.android.youhadmeathelloworld.auth;

import android.content.Context;

/**
 * Created by david on 10/18/14.
 */
public class AuthenticationManager {

    private static String authToken;

    public static String getAuthToken(Context context) {
        if (authToken == null) {
            authToken = context.getSharedPreferences("auth", Context.MODE_PRIVATE).getString("token", null);
        }
        return authToken;
    }

    public static void setAuthToken(Context context, String authToken) {
        AuthenticationManager.authToken = authToken;
        context.getSharedPreferences("auth", Context.MODE_PRIVATE).edit().putString("token", authToken).commit();
    }


    private static String username;

    public static String getUsername(Context context) {
        if (username == null) {
            username = context.getSharedPreferences("auth", Context.MODE_PRIVATE).getString("name", null);
        }
        return username;
    }

    public static void setUsername(Context context, String username) {
        AuthenticationManager.username = username;
        context.getSharedPreferences("auth", Context.MODE_PRIVATE).edit().putString("name", username).commit();
    }
}
