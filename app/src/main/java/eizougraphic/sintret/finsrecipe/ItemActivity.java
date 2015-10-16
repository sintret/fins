package eizougraphic.sintret.finsrecipe;

import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import eizougraphic.sintret.finsrecipe.library.SessionManager;

public class ItemActivity extends BaseActivity {

    SessionManager session;
    TextView textView;
    TextView textView2;
    TextView textView3;
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


        textView = (TextView) findViewById(R.id.textView);
        textView.setText("Name : " + session.name());
        textView2 = (TextView) findViewById(R.id.textView2);
        textView2.setText("Email : " + session.email());
        textView3 = (TextView) findViewById(R.id.textView3);
        textView3.setText("Member since :" + session.createdAt());
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }
}