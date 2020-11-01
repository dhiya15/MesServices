package com.univbechar.pfe.messervices;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    DBConnection dbConnection;
    EditText user, pass;



    public boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbConnection = new DBConnection(this);

        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.pass);



    }

    public void home_activity(View view) {

        String us = user.getText().toString();
        String ps = pass.getText().toString();

        if(us.isEmpty()){
            //Toast.makeText(this, "Entrer un nom d'utilisateur et un mot de passe SVP !", Toast.LENGTH_SHORT).show();
            user.setError("Merçi de d'entrez la valeur de ce champ !");
        }else{
            if(ps.isEmpty()){
                //Toast.makeText(this, "Entrer un nom d'utilisateur et un mot de passe SVP !", Toast.LENGTH_SHORT).show();
                pass.setError("Merçi de d'entrez la valeur de ce champ !");
            }else if(dbConnection.checkUser(us, ps)){
                Intent intent = new Intent(this, Home2.class);
                startActivity(intent);
            }else{
                Toast.makeText(this, "Votre infos incorrect !", Toast.LENGTH_SHORT).show();

            }
        }


    }

    public void subscribe(View view) {
        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);
    }

}
