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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class UploadBallence extends AppCompatActivity {

    final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;


    EditText number ;
    Spinner operator ;
    EditText money ;
    Button btnSend;
    TextWatcher text;
    RadioGroup mode;
    RadioButton simple;
    RadioButton facture;
    RadioButton inter;
    RadioButton maxy ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_ballence);

        simple = (RadioButton) findViewById(R.id.simple);
        maxy = (RadioButton) findViewById(R.id.maxy);
        inter = (RadioButton) findViewById(R.id.international);
        facture = (RadioButton) findViewById(R.id.facture);
        number = (EditText) findViewById(R.id.txtNumber);
        operator = (Spinner) findViewById(R.id.operator);
        money = (EditText) findViewById(R.id.txtMoney);
        btnSend = (Button) findViewById(R.id.btnSend);
        mode = (RadioGroup) findViewById(R.id.mode);
        btnSend.setEnabled(false);

        if(checkPermission(Manifest.permission.SEND_SMS)){
            btnSend.setEnabled(true);
        }else{
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
        }

        btnSend.setEnabled(false);

        text = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                char chr[] = number.getText().toString().toCharArray();
                if (chr.length > 0){
                    if(chr[0] == '0'){
                        btnSend.setEnabled(false);
                        if (chr.length > 1){
                            switch (chr[1]){
                                case '6' :
                                    operator.setSelection(0);
                                    maxy.setEnabled(false);
                                    facture.setEnabled(true);
                                    inter.setEnabled(true);
                                    simple.setEnabled(true);
                                    btnSend.setEnabled(true);
                                    break;
                                case '7' :
                                    operator.setSelection(1);
                                    maxy.setEnabled(false);
                                    facture.setEnabled(true);
                                    inter.setEnabled(false);
                                    simple.setEnabled(true);
                                    btnSend.setEnabled(true);
                                    break;
                                case '5' :
                                    operator.setSelection(2);
                                    maxy.setEnabled(true);
                                    facture.setEnabled(false);
                                    inter.setEnabled(false);
                                    simple.setEnabled(true);
                                    btnSend.setEnabled(true);
                                    break;
                                default: btnSend.setEnabled(false);
                            }
                        }
                    }else{
                        btnSend.setEnabled(false);
                    }
                }

            }
        };

        number.addTextChangedListener(text);
    }

    public void activity_services(View view) {

        Intent intent = new Intent(this,Services.class);
        startActivity(intent);
    }

    public void sendSMS(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Voulez vous vraiment envoyer ce crédit !");
        builder.setPositiveButton("Envoyer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String message = "";
                switch(operator.getSelectedItem().toString()){
                    case "Djezzy" :
                        message = "3,";
                        int id = mode.getCheckedRadioButtonId();
                        RadioButton selectedMode = (RadioButton) findViewById(id);
                        switch(selectedMode.getText().toString()){
                            case "Simple":
                                message += "1,";
                                break;
                            case "Facture":
                                message += "2,";
                                break;
                        }
                        break;
                    case "Mobilis":
                        message = "4,";
                        id = mode.getCheckedRadioButtonId();
                        selectedMode = (RadioButton) findViewById(id);
                        switch(selectedMode.getText().toString()){
                            case "Simple":
                                message += "1,";
                                break;
                            case "Facture":
                                message += "2,";
                                break;
                            case "International":
                                message += "3,";
                                break;
                        }
                        break;
                    case "Ooredoo":
                        message = "5,";
                        id = mode.getCheckedRadioButtonId();
                        selectedMode = (RadioButton) findViewById(id);
                        switch(selectedMode.getText().toString()) {
                            case "Simple":
                                message += "1,";
                                break;
                            case "Maxy":
                                message += "4,";
                                break;
                        }
                        break;
                }
                message += ClientInfo.id+","+money.getText().toString()+","+number.getText().toString();
                String number = "0672023497";
                if(number == null || number.length() == 0 ||
                        message == null || message.length() == 0){
                    return;
                }
                if(checkPermission(Manifest.permission.SEND_SMS)){
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(number, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "Demmande Envoyée!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Demmande Réfusée", Toast.LENGTH_LONG).show();
                }
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

}
