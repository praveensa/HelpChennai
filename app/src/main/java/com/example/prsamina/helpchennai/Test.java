package com.example.prsamina.helpchennai;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public  class Test extends Activity {
    ProgressDialog progressDialog;
    GPSTracker gps;
    Button register;
    TextView name, phone;
    public final String url = "http://10.0.2.2/AndroidTest/insertdata.php";
    JSONParser jsonParser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shareoption);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            String phone=bundle.getString("phone");
            EditText phonev=(EditText)findViewById(R.id.phonel);
            phonev.setHint(phone);

        }
    }
}