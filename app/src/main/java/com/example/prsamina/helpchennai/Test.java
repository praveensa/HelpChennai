package com.example.prsamina.helpchennai;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public  class Test extends Activity{
    ProgressDialog progressDialog;
    GPSTracker gps;
    Button register;
    TextView name,phone;
    public final String url="http://10.0.2.2/AndroidTest/insertdata.php";
    JSONParser jsonParser=new JSONParser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        name=(TextView)findViewById(R.id.name);
        phone=(TextView)findViewById(R.id.phone);
        register=(Button)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                new  InsertData(name.getText().toString(),phone.getText().toString()).execute();
            }
        });
    }

    private class InsertData extends AsyncTask<String,String,String> {
        String name;
        String phone;

        public InsertData(String s, String s1) {
            this.name=s;
            this.phone=s1;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(Test.this);
            progressDialog.setMessage("Inserting Data");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            //noinspection deprecation
            List<NameValuePair> param = new ArrayList<NameValuePair>();

            //noinspection deprecation
            param.add(new BasicNameValuePair("name", name));
            //noinspection deprecation
            param.add(new BasicNameValuePair("phone", phone));

            try {
                int success=1;
                JSONObject jsonObject=new JSONObject();
                jsonObject = jsonParser.makeHttpRequest(url, "GET", param);
                //if(jsonObject!=null)
                 success=jsonObject.getInt("status");
                if(success==0)
                {

                }

            } catch (JSONException e) {
                Toast.makeText(Test.this,e.toString(),Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
        }
    }
}