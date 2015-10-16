package eizougraphic.sintret.finsrecipe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import eizougraphic.sintret.finsrecipe.library.AppConfig;
import eizougraphic.sintret.finsrecipe.library.JSONParser;
import eizougraphic.sintret.finsrecipe.library.SessionManager;
import eizougraphic.sintret.finsrecipe.sql.Order;
import eizougraphic.sintret.finsrecipe.sql.Person;
import eizougraphic.sintret.finsrecipe.task.NoviewAdapter;
import eizougraphic.sintret.finsrecipe.task.TaskAdapter;

public class TaskActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public Toolbar toolbar;
    ProgressDialog progressDialog;
    private SessionManager session;
    private RecyclerView rv;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from SQLite

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "No Promo at this moment...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /* your logic here */

        rv = (RecyclerView) findViewById(R.id.recycle_view);
        rv.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(mLayoutManager);

        JSONParse jsonParse = new JSONParse();
        jsonParse.execute();

    }

    private void logoutUser() {
        session.setLogin(false);

        // Launching the login activity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent;

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            logoutUser();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;

        if (id == R.id.nav_home) {
            // Handle the camera action
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_task) {
            intent = new Intent(this, TaskActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_history) {
            intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_scanner) {
            intent = new Intent(this, ScannerActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_map) {
            intent = new Intent(this, MapActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class JSONParse extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(TaskActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONParser jsonParser = new JSONParser();

            String Url = AppConfig.URL_TASK + "?token=" + session.token();
            Log.d("this url", Url);

            //Getting JSON from url
            JSONObject jsonObject = jsonParser.getJSONFromUrl(Url);
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            try {

                Log.d("JSON OBJEct",jsonObject.toString());
                Boolean error = jsonObject.getBoolean(AppConfig.TAG_ERROR);
                String errorMessage = jsonObject.getString(AppConfig.TAG_ERROR_MESSAGE);

                //if no data available
                if (error == true) {
                    Toast.makeText(TaskActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                    List<Person> noviews;

                    noviews = new ArrayList<>();
                    noviews.add(new Person("No Data Available", "Your Task Empty"));

                    NoviewAdapter adapter = new NoviewAdapter(noviews);
                    rv.setAdapter(adapter);
                } else {
                    List<Order> orders;
                    orders = new ArrayList<>();
                    JSONArray array = jsonObject.getJSONArray(AppConfig.TAG_DATA);

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonData = array.getJSONObject(i);
                        orders.add(toOrder(jsonData));
                        TaskAdapter adapter = new TaskAdapter(orders);
                        rv.setAdapter(adapter);
                    }
                }

                progressDialog.hide();

                Log.d("description :", error + " is true");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private Order toOrder(JSONObject jsonObject) throws JSONException {

            String id = jsonObject.getString("id");
            String customer = jsonObject.getString("customer");
            String address = jsonObject.getString("address");
            String address2 = jsonObject.getString("address2");
            String phone = jsonObject.getString("phone");
            String phone2 = jsonObject.getString("phone2");
            String kodepos = jsonObject.getString("kodepos");
            String provinsi = jsonObject.getString("provinsi");
            String kabupaten = jsonObject.getString("kabupaten");
            String kecamatan = jsonObject.getString("kecamatan");
            String subTotal = jsonObject.getString("subTotal");
            String discount = jsonObject.getString("discount");
            String total = jsonObject.getString("total");
            String shippingFee = jsonObject.getString("shippingFee");
            String paymentMethod = jsonObject.getString("paymentMethod");
            String deliveryDate = jsonObject.getString("deliveryDate");
            String deliveryHour = jsonObject.getString("deliveryHour");
            String deliveryTime = jsonObject.getString("deliveryTime");
            String remark = jsonObject.getString("remark");
            String items = jsonObject.getString("items");

            return new Order(id, customer, address, address2, phone, phone2, kodepos, provinsi, kabupaten, kecamatan, subTotal, discount, total, shippingFee, paymentMethod, deliveryDate, deliveryHour, deliveryTime, remark, items);
        }


    }
}