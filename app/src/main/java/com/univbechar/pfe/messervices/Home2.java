package com.univbechar.pfe.messervices;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class Home2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;
    DBConnection dbConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(checkPermission(Manifest.permission.SEND_SMS)){

        }else{
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
        }

        Toast.makeText(this, "Bienvenu "+ClientInfo.firstName+" "+ClientInfo.lastName, Toast.LENGTH_LONG).show();

        TextView fullName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.fullNameNav);
        TextView phone = (TextView) navigationView.getHeaderView(0).findViewById(R.id.phoneNav);
        TextView email = (TextView) navigationView.getHeaderView(0).findViewById(R.id.emailNav);

        fullName.setText(ClientInfo.firstName + " " + ClientInfo.lastName);
        phone.setText(ClientInfo.phone);
        email.setText(ClientInfo.email);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;

        switch(id){
            case R.id.update_infos :
                intent = new Intent(this, UpdateInfos.class);
                startActivity(intent);
                break;
            case R.id.check_balance :
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setMessage("Vous lez-vous vraiment consulter votre solde ?");
                builder1.setPositiveButton("Envoyer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String message = "6,"+ClientInfo.id;
                        sendSMS(message);
                    }
                });
                builder1.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alertDialog1 = builder1.create();
                alertDialog1.show();
                break;
            case R.id.unsubscribe :
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Vous lez-vous vraiment désinscrit ?");
                builder.setPositiveButton("Envoyer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String message = "7,"+ClientInfo.id;
                        sendSMS(message);
                        Intent intent = new Intent(getBaseContext(), Login.class);
                        startActivity(intent);
                        dbConnection.deleteClient(ClientInfo.id);
                    }
                });
                builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
            case R.id.logout :
                intent = new Intent(this, Login.class);
                startActivity(intent);
                break;
            case R.id.exit :
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void services_activity(View view) {
        Intent intent = new Intent(this, Services.class);
        startActivity(intent);
    }

    public void exit(View view) {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
    public void contact_activity(View view) {
        Intent intent = new Intent(this, Contact.class);
        startActivity(intent);
    }

    public void about_activity(View view) {
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }

        public void sendSMS(String message) {

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

        public boolean checkPermission(String permission){
            int check = ContextCompat.checkSelfPermission(this, permission);
            return (check == PackageManager.PERMISSION_GRANTED);
        }

}
