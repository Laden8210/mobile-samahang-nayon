package nasaph8210.samahangnayon.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String TAG = SessionManager.class.getSimpleName();
    private static final String PREF_NAME = "SamahangNayon";
    private static final String TOKEN = "token";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private static SessionManager sessionManager;
    private SessionManager(Context context) {
        this.pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.editor = pref.edit();
    }
    public static synchronized SessionManager getInstance(Context context) {
        if (sessionManager == null) {
            sessionManager = new SessionManager(context.getApplicationContext());
        }
        return sessionManager;
    }
    public void setToken(String token) {
        editor.putString(TOKEN, token);
        editor.apply();
    }
    public String getToken() {
        return pref.getString(TOKEN, null);
    }

    public void clear() {
        editor.clear();

        editor.apply();
    }
}
