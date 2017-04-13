package com.sccivolley;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.IntentCompat;
import android.util.Log;



public class MyPreferenceManager {

    private String TAG = MyPreferenceManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "sccivolley";

    // All Shared Preferences Keys
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_PASS = "user_pass";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_USER_PHONE = "user_phone";
    private static final String KEY_USER_Image = "user_image";


    // Constructor
    public MyPreferenceManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void storeUser(UserModel user) {
        editor.clear();
        editor.commit();
        editor.putString(KEY_USER_ID, user.getId());
        editor.putString(KEY_USER_NAME, user.getName());
        editor.putString(KEY_USER_EMAIL , user.getEmail());
        editor.putString(KEY_USER_PHONE , user.getPhone());
        editor.putString(KEY_USER_Image , user.getImg());
        editor.putString(KEY_USER_PASS , user.getPass());
        editor.commit();


        Log.e(TAG, "User is stored in shared preferences. " + user.getName() + " ," + user.getEmail() );
    }

    public UserModel getUser() {
        if (pref.getString(KEY_USER_ID, null) != null) {
            String id, name,email,phone , image , pass;
            id = pref.getString(KEY_USER_ID, null);
            name = pref.getString(KEY_USER_NAME, null);
            email = pref.getString(KEY_USER_EMAIL, null);
            phone = pref.getString(KEY_USER_PHONE, null);
            image = pref.getString(KEY_USER_Image, null);
            pass = pref.getString(KEY_USER_PASS, null);

            UserModel user = new UserModel();
            user.setName(name);
            user.setId(id);
            user.setEmail(email);
            user.setPhone(phone);
            user.setImg(image);
            user.setPass(pass);
            return user;
        }
        return null;
    }
    public void clear() {
        editor.clear();
        editor.commit();
        Intent intent = new Intent(_context, MainActivity.class);
        ComponentName cn = intent.getComponent();
        Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
        _context.startActivity(mainIntent);
    }
}
