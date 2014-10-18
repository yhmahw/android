package me.gethelloworld.android.youhadmeathelloworld.auth;

import android.content.Context;

/**
 * Created by david on 10/18/14.
 */
public class AuthenticationManager {

    private static String authToken;

    public static String getAuthToken(Context context) {
        if(authToken == null) {
            authToken = context.getSharedPreferences("auth", Context.MODE_PRIVATE).getString("token", null);
        }
        return authToken;
    }

    public static void setAuthToken(Context context, String authToken) {
        AuthenticationManager.authToken = authToken;
        context.getSharedPreferences("auth", Context.MODE_PRIVATE).edit().putString("token", authToken).commit();
    }
}
