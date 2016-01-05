package com.example.prsamina.helpchennai;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by prsamina on 1/4/2016.
 */
public class ShareOption extends Fragment {
    private Button register,remove;
    private TextView phonenumber;
    public ShareOption(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v=inflater.inflate(R.layout.shareoption,container,false);
        register=(Button)v.findViewById(R.id.registerMe);
        phonenumber=(TextView)v.findViewById(R.id.phonel);
        final String phone=phonenumber.getText().toString();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),ShareHome.class);
                intent.putExtra("phone",phone);
                startActivity(intent);
            }
        });
        return v;
    }

}
