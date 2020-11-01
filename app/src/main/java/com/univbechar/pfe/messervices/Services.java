package com.univbechar.pfe.messervices;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Services extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
    }


    public void upload_ballence(View view) {
        Intent intent = new Intent(this, UploadBallence.class);
        startActivity(intent);
    }

    public void activity_home(View view) {
        Intent intent = new Intent(this, Home2.class);
        startActivity(intent);
    }

    public void sendFax(View view) {
        Intent intent = new Intent(this, SendFax.class);
        startActivity(intent);
    }

    public void lte4g(View view) {
        Intent intent = new Intent(this, Internet.class);
        startActivity(intent);

    }

    public void activity_adsl(View view) {
        Intent intent = new Intent(this, ADSL.class);
        startActivity(intent);
    }
}
