package eizougraphic.sintret.finsrecipe;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import eizougraphic.sintret.finsrecipe.library.SessionManager;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        super.onCreateDrawer();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.show();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "No Service at this moment...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.content_item);
        View inflated = stub.inflate();
        /* your logic here */

        session = new SessionManager(getApplicationContext());
        Bundle bundle = getIntent().getExtras();

        if(bundle.getString("id")!= null)
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

        }

    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }
}