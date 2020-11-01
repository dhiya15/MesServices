package com.univbechar.pfe.messervices;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ShowMessage extends AppCompatActivity {

    TextView id, firstName, lastName, phone, email, user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_message);

        String infos[];
        id = findViewById(R.id.ID);
        firstName = findViewById(R.id.firstNameMsg);
        lastName = findViewById(R.id.lastNameMsg);
        phone = findViewById(R.id.phoneMsg);
        email = findViewById(R.id.emailMsg);
        user = findViewById(R.id.userMsg);
        pass = findViewById(R.id.passMsg);

        Intent intent = getIntent();

        if(intent.getStringExtra("msg") != null){
            String msg = intent.getStringExtra("msg");
            if (msg.contains(",")){
                infos = msg.split("\\,");
                id.setText("ID : " + infos[0]);
                firstName.setText("Nom : " + infos[1]);
                lastName.setText("Prénom : " + infos[2]);
                phone.setText("Téléphone : " + infos[3]);
                email.setText("Email : " + infos[4]);
                user.setText("Nom d'utilisateur : " + infos[5]);
                pass.setText("Mot de Passe : " + infos[6]);
            }else{

            }
        }

    }

    public void login(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}
