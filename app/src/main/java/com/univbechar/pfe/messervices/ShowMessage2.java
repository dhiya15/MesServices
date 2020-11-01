package com.univbechar.pfe.messervices;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowMessage2 extends AppCompatActivity {

    TextView msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_message2);

        msg = findViewById(R.id.textView17);

        Intent intent = getIntent();

        if(intent.getStringExtra("msg") != null) {
            String msg2 = intent.getStringExtra("msg");
            msg.setText("Message : " + msg2);
        }
    }
}
