package eizougraphic.sintret.finsrecipe.library;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    SharedPreferences.Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "Kueijo";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static final String PREF_GCM_REG_ID = "GCMRegId";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin() {
        editor.putBoolean(KEY_IS_LOGGEDIN, false);
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public void setGCM(String gcmRegId){
        editor.putString(AppConfig.TAG_GCM_REGID, gcmRegId);
        editor.commit();
        Log.d(TAG, "User login session modified!");

    }

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }
    public String gcm_regid() {
        return pref.getString(AppConfig.TAG_GCM_REGID, "DEFAULT");
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public String name() {
        return pref.getString(AppConfig.TAG_NAME, "DEFAULT");
    }

    public String email() {
        return pref.getString(AppConfig.TAG_EMAIL, "DEFAULT");
    }

    public String token() {return pref.getString(AppConfig.TAG_TOKEN, "DEFAULT");}

    public String phone() {
        return pref.getString(AppConfig.TAG_PHONE, "DEFAULT");
    }

    public String photo() {
        return pref.getString(AppConfig.TAG_PHOTO, "DEFAULT");
    }

    public String createdAt() {
        return pref.getString(AppConfig.TAG_CREATED_AT, "DEFAULT");
    }


    public void createLoginSession(String name, String email, String token,String phone,String created_at, String photo) {
        // Storing login value as TRUE
        editor.putBoolean(KEY_IS_LOGGEDIN, true);

        // Storing name in pref
        editor.putString(AppConfig.TAG_NAME, name);
        editor.putString(AppConfig.TAG_EMAIL, email);
        editor.putString(AppConfig.TAG_TOKEN, token);
        editor.putString(AppConfig.TAG_PHONE, phone);
        editor.putString(AppConfig.TAG_CREATED_AT, created_at);
        editor.putString(AppConfig.TAG_PHOTO, photo);

        // commit changes
        editor.commit();
        Log.d(TAG, "User login session modified!");
    }


}