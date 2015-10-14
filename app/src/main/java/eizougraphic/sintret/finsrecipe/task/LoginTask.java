package eizougraphic.sintret.finsrecipe.task;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import eizougraphic.sintret.finsrecipe.MainActivity;
import eizougraphic.sintret.finsrecipe.library.AppConfig;
import eizougraphic.sintret.finsrecipe.library.SessionManager;

public class LoginTask extends AsyncTask<String, String, JSONObject> {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
    private HashMap<String, String> params = null;// post data
    JSONObject jsonObject =null;
    Context context;
    ProgressDialog progressDialog;
    private SessionManager session;
    public Button _button;

    // variables passed in:
    String url;

    // constructor
    public LoginTask(Context context, String url, HashMap<String, String> params, Button button, ProgressDialog progressDialog, SessionManager session) {
        this.url = url;
        this.params = params;
        this._button = button;
        this.progressDialog = progressDialog;
        this.session = session;
        this.context = context;
    }

    @Override
    protected JSONObject doInBackground(String... args) {
        // Making HTTP request
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            Iterator<String> it = params.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                nameValuePair.add(new BasicNameValuePair(key, params.get(key)));
            }

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
            Log.e("JSON", json);
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jObj;
    }

    @Override
    protected void onPostExecute(JSONObject jObj) {
        jsonObject=jObj;
        // Session manager
        try {
            Boolean error = jsonObject.getBoolean(AppConfig.TAG_ERROR);
            String errorMessage = jsonObject.getString(AppConfig.TAG_ERROR_MESSAGE);
            String name = jsonObject.getString(AppConfig.TAG_NAME);
            String email =jsonObject.getString(AppConfig.TAG_EMAIL);
            String token = jsonObject.getString(AppConfig.TAG_TOKEN);
            String phone = jsonObject.getString(AppConfig.TAG_PHONE);
            String created_at = jsonObject.getString(AppConfig.TAG_CREATED_AT);
            String photo = jsonObject.getString(AppConfig.TAG_PHOTO);
            Log.d("email user",email+" is true");
            Log.d("status",error+" is " + error);

            if (error == true) {
                //Jika ada error
                session.setLogin(false);
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
                onSignupFailed();
            } else {
                //Jika Oke
                //session.setLogin(true);
                session.createLoginSession(name,email,token,phone,created_at,photo);
                onSignupSuccess();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // return;
    }

    @Override
    protected void onPreExecute() {
        //progressDialog = new ProgressDialog(context, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
    }

    public void onSignupSuccess() {
        _button.setEnabled(true);
        // Launching the MAIN activity
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public void onSignupFailed() {
        _button.setEnabled(true);
        progressDialog.hide();
    }
}