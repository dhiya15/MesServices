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
import android.widget.Toast;

public class ADSL extends AppCompatActivity {

    final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adsl);

        if(checkPermission(Manifest.permission.SEND_SMS)){

        }else{
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
        }
    }

    public void back(View view) {
        Intent intent = new Intent(this, Services.class);
        startActivity(intent);
    }

    public void adsl500da(View view) {
        String BON = "500";
        String ADSLP = "Recharger votre compte ADSL pour 10 jours avec un prix de 500 DA.";
        showConfirmDialog(ADSLP, BON);
    }

    public void adsl1000da(View view) {
        String BON = "1000";
        String ADSLP = "Recharger votre compte ADSL pour 19 jours avec un prix de 1000 DA.";
        showConfirmDialog(ADSLP, BON);
    }

    public void adsl2000da(View view) {
        String BON = "2000";
        String ADSLP = "Recharger votre compte ADSL avec un prix de 2000 DA.";
        showConfirmDialog(ADSLP, BON);
    }

    public void adsl3000da(View view) {
        String BON = "3000";
        String ADSLP = "Recharger votre compte ADSL avec un prix de 3000 DA.";
        showConfirmDialog(ADSLP, BON);
    }

    private void showConfirmDialog(String msg, final String prix) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg);
        builder.setPositiveButton("Envoyer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sendSMS(prix);
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }

    public void sendSMS(String bon){
        String message = "2," + ClientInfo.id + "," + bon;
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
