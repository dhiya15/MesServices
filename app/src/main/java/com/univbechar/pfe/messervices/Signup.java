package com.univbechar.pfe.messervices;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Signup extends AppCompatActivity {

    final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;

    DBConnection dbConnection;

    EditText firstName, lastName, phone, email, user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        if(checkPermission(Manifest.permission.SEND_SMS)){

        }else{
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
        }

        //Toast.makeText(this, "Il faut crée le compte avec les mème informations existe dans le site web", Toast.LENGTH_LONG).show();

        dbConnection = new DBConnection(this);

        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        phone = (EditText) findViewById(R.id.phone);
        email = (EditText) findViewById(R.id.email);
        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.pass);

    }

    public void insertData(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Vous lez-vous vraiment inscrit ?");
        builder.setPositiveButton("Envoyer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String fn = firstName.getText().toString();
                String ln = lastName.getText().toString();
                String ph = phone.getText().toString();
                String em = email.getText().toString();
                String us = user.getText().toString();
                String ps = pass.getText().toString();
                if((fn.isEmpty()) || (ln.isEmpty()) || (ph.isEmpty()) || (em.isEmpty()) || (us.isEmpty()) || (ps.isEmpty())){
                    Toast.makeText(getBaseContext(), "Compléter tous les champs !", Toast.LENGTH_SHORT).show();
                }else {
                    String id = fn.charAt(0) + ph + ln.charAt(0);
                    Client client = new Client(id, fn, ln, ph, em, us, ps);
                    try{
                        sendSMS(client);
                        firstName.setText("");
                        lastName.setText("");
                        phone.setText("");
                        email.setText("");
                        user.setText("");
                        pass.setText("");
                        Toast.makeText(getBaseContext(), "Votre demmande envoyé avec succé et en cour de traitement !", Toast.LENGTH_LONG).show();
                    }catch (Exception ex){
                        Toast.makeText(getBaseContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getBaseContext(), Login.class);
                startActivity(intent);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void back(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }

    public void sendSMS(Client client){
        String message = "8,"+client.getId()+","+client.getFirstName()+","+client.getLastName()+","+client.getPhone()+","+client.getEmail()+","+client.getUser()+","+client.getPass();
        String number = "0672023497";
        if(number == null || number.length() == 0 ||
                message == null || message.length() == 0){
            return;
        }
        if(checkPermission(Manifest.permission.SEND_SMS)){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number, null, message, null, null);
            Toast.makeText(this, "Demmande Envoyée!", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Demmande Réfusée", Toast.LENGTH_LONG).show();
        }
    }
}
