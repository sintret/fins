package eizougraphic.sintret.finsrecipe;


import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;

public class ScannerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        super.onCreateDrawer();

        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.content_scanner);
        View inflated = stub.inflate();
        /* your logic here */


    }
}