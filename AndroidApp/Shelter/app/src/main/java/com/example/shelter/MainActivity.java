package com.example.shelter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shelter.netConector.NetConector;
import com.example.shelter.netConector.NetProcess;
import com.example.shelter.protocol.Protocol;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    NetConector net;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        context = findViewById(R.id.login).getContext();
        Button btn = findViewById(R.id.login);
        btn.setOnClickListener(this);
        net = new NetConector();
    }

    public void testResponse(Protocol prol) {
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            (String) prol.getBody(), Toast.LENGTH_SHORT);

            toast1.show();

    }
    public void timeOut() {

            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            "timeOut", Toast.LENGTH_SHORT);

            toast1.show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                EditText email = findViewById(R.id.email);

                EditText password = findViewById(R.id.password);

                NetProcess np = new NetProcess(this,context,net,new Protocol(0,"","LoginUser","user",email.getText().toString(),"pass",password.getText().toString()),"testResponse","testResponse","timeOut");

        }
    }
}