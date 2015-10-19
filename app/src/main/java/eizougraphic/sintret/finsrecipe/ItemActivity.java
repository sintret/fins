package eizougraphic.sintret.finsrecipe;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
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
import java.util.List;

import eizougraphic.sintret.finsrecipe.library.AppConfig;
import eizougraphic.sintret.finsrecipe.library.JSONParser;
import eizougraphic.sintret.finsrecipe.library.SessionManager;
import eizougraphic.sintret.finsrecipe.sql.Order;
import eizougraphic.sintret.finsrecipe.sql.Person;

public class ItemActivity extends BaseActivity {

    SessionManager session;
    TextView id;

    TextView customer;
    TextView address;
    TextView phone;
    TextView provinsi;
    TextView kodepos;
    TextView item;
    TextView paymentMethod;
    TextView deliveryDate;

    TextView subTotal;
    TextView discount;
    TextView total;
    TextView shippingFee;
    String _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        super.onCreateDrawer();

        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.content_item);
        View inflated = stub.inflate();
        /* your logic here */

        session = new SessionManager(getApplicationContext());
        Bundle bundle = getIntent().getExtras();

        _id = bundle.getString("id");

        if(_id != null)
        {
            setTitle("Order No. "+ bundle.getString("id"));

            //TODO here get the string stored in the string variable and do
            // setText() on userName
            /*id = (TextView) findViewById(R.id.id);
            id.setText("Order ID : " + bundle.getString("id"));*/
            customer = (TextView) findViewById(R.id.customer);
            customer.setText("Customer : " + bundle.getString("customer"));
            address = (TextView) findViewById(R.id.address);
            address.setText("Address : " + bundle.getString("address"));
            phone = (TextView) findViewById(R.id.phone);
            phone.setText("Phone : " + bundle.getString("phone"));
            provinsi = (TextView) findViewById(R.id.provinsi);
            provinsi.setText("Provinsi : " + bundle.getString("provinsi") +" Kab : " +bundle.get("kabupaten") + " Kec: "+ bundle.get("kecamatan"));
            item = (TextView) findViewById(R.id.item);
            item.setText("Items : " + bundle.getString("items"));
            paymentMethod= (TextView) findViewById(R.id.payment_method);
            paymentMethod.setText("Payment Method : " + bundle.getString("paymentMethod"));
            deliveryDate = (TextView) findViewById(R.id.delivery_date);
            deliveryDate.setText("Delivery Date : "+ bundle.getString("deliveryTime") +" " +  bundle.getString("deliveryDate") +" " + bundle.getString("deliveryHour"));

        }

        // for floating button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Integer  deliveryStatus = bundle.getInt("deliveryStatus");

        Log.d("deliveryStatus :", "delivery Status is "+deliveryStatus.toString());

        if(deliveryStatus==1){
            fab.hide();
        } else {
            fab.show();
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HashMap<String, String> data = new HashMap<String, String>();
                    data.put("token", session.token());
                    data.put("id", _id);
                    data.put("location", "Location is not availble");

                    JSONParse jsonParse = new JSONParse(data);
                    jsonParse.execute();

                   /* Snackbar.make(view, "No Service at this moment...", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();*/
                }
            });
        }



    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public class JSONParse extends AsyncTask<String, Void, JSONObject> {

        private String url= AppConfig.URL_EXECUTE;
        private HashMap<String, String> params;
        InputStream is = null;
        String json = "";
        JSONObject jsonObject =null;
        JSONObject jObj = null;


        public JSONParse(HashMap<String, String> params){
            this.params=params;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(ItemActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
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
        protected void onPostExecute(JSONObject jsonObject) {
            try {

                Log.d("JSON OBJEct",jsonObject.toString());
                Boolean error = jsonObject.getBoolean(AppConfig.TAG_ERROR);
                String errorMessage = jsonObject.getString(AppConfig.TAG_ERROR_MESSAGE);
                Intent intent;


                if(error==false){
                    intent = new Intent(ItemActivity.this, TaskActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(ItemActivity.this,"Something Wrong, Please try again", Toast.LENGTH_LONG).show();
                }

                progressDialog.hide();

                Log.d("description :", error + " is true");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }
}