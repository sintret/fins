package eizougraphic.sintret.finsrecipe.library;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class AppConfig {
    // variable to get data from async
    public static final String TAG_DATA = "data";
    public static final String TAG_ID = "id";
    public static final String TAG_ERROR = "error";
    public static final String TAG_ERROR_MESSAGE = "error_message";
    public static final String TAG_NAME = "name";
    public static final String TAG_EMAIL = "email";
    public static final String TAG_TOKEN = "token";
    public static final String TAG_PHONE = "phone";
    public static final String TAG_ADDRESS = "address";
    public static final String TAG_PHOTO = "photo";

    public static final String TAG_CREATED_AT = "created_at";
    public static final String TAG_UPDATED_AT = "updated_at";

    public static final String TAG_GCM_REGID = "gcm_regid";


    // Server user login url
    public static String URL_LOGIN = "http://gurame.net/kueijo/web/api/login";

    public static String URL_FORGOT = "http://gurame.net/kueijo/web/site/forgot_password";

    public static String URL_TASK = "http://gurame.net/kueijo/web/api/task";
    public static String URL_HISTORY = "http://gurame.net/kueijo/web/api/history";
    public static String URL_TASK_TEST = "http://gurame.net/kueijo/web/api/test-task";
    public static String URL_EXECUTE = "http://gurame.net/kueijo/web/api/delivery";
    public static String URL_REGISTER_GCM = "http://gurame.net/kueijo/web/api/register";

    public static String SERVER_SUCCESS = "Succcess to Register";

    public static boolean isNetworkAvailable(Context ctx)
    {
        ConnectivityManager cm = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()&& cm.getActiveNetworkInfo().isAvailable()&& cm.getActiveNetworkInfo().isConnected())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
