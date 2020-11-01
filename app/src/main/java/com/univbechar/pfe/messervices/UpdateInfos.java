package com.univbechar.pfe.messervices;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateInfos extends AppCompatActivity {

    DBConnection dbConnection;
    EditText fn, ln, ph, em, us, ps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_infos);

        dbConnection = new DBConnection(this);

        fn = (EditText) findViewById(R.id.updateFirstName);
        ln = (EditText) findViewById(R.id.updateLastName);
        ph = (EditText) findViewById(R.id.updatePhone);
        em = (EditText) findViewById(R.id.updateEmail);
        us = (EditText) findViewById(R.id.updateUser);
        ps = (EditText) findViewById(R.id.updatePass);

        fn.setText(ClientInfo.firstName);
        ln.setText(ClientInfo.lastName);
        ph.setText(ClientInfo.phone);
        em.setText(ClientInfo.email);
        us.setText(ClientInfo.user);
        ps.setText(ClientInfo.pass);

    }

    public void back(View view) {
        Intent intent = new Intent(this, Home2.class);
        startActivity(intent);
    }

    public void updateData(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Vous lez-vous vraiment modifier votre infos ?");
        builder.setPositiveButton("Envoyer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String firstName = fn.getText().toString();
                String lastName = ln.getText().toString();
                String phone = ph.getText().toString();
                String email = em.getText().toString();
                String user = us.getText().toString();
                String pass = ps.getText().toString();
                if((firstName.isEmpty()) || (lastName.isEmpty()) || (phone.isEmpty()) || (email.isEmpty()) || (user.isEmpty()) || (pass.isEmpty())){
                    Toast.makeText(getBaseContext(), "Compléter tous les champs !", Toast.LENGTH_SHORT).show();
                }else {
                    Client client = new Client(ClientInfo.id, firstName, lastName, phone, email, user, pass);
                    try{
                        dbConnection.updateInfos(client);
                        Toast.makeText(getBaseContext(), "Votre infos modifier avec succé !", Toast.LENGTH_LONG).show();
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
}
