package com.example.prsamina.helpchennai;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prsamina on 1/4/2016.
 */
public class TrackPerson extends Activity {
    private JSONParser jsonParser=new JSONParser();
    private Button track;
    private boolean flag=false;
    private String phone;
    private TextView phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trackperson);
        track=(Button)findViewById(R.id.track);
        phoneNumber=(TextView)findViewById(R.id.phonetext);
        phone=phoneNumber.getText().toString();
        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Track().execute();
            }
        });
    }

    private class Track extends AsyncTask<String,String,String> {
        ProgressDialog progressDialog=new ProgressDialog(TrackPerson.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Tracking....");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            List<NameValuePair> parms=new ArrayList<NameValuePair>();
            parms.add(new BasicNameValuePair("phone",phone));
           // JSONObject jsonObject=jsonParser.makeHttpRequest()
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.cancel();
            if(flag==false)
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final AlertDialog.Builder alert = new AlertDialog.Builder(TrackPerson.this);
                        alert.setTitle("The Person is Safe");
                        alert.setMessage("the person you are searching is safe");
                        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        alert.setNegativeButton("wanna see his Location", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                    }
                });


            }
            else
            {
            }
            super.onPostExecute(s);
        }
    }
}
