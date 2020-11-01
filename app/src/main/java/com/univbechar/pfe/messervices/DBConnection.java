package com.univbechar.pfe.messervices;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBConnection {

    DBInfo dbInfo;

    public DBConnection(Context context){
        dbInfo = new DBInfo(context);
    }

    public void insertData(Client client){
        SQLiteDatabase sqLiteDatabase = dbInfo.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBInfo.id, client.getId());
        contentValues.put(DBInfo.firstName, client.getFirstName());
        contentValues.put(DBInfo.lastName, client.getLastName());
        contentValues.put(DBInfo.phone, client.getPhone());
        contentValues.put(DBInfo.email, client.getEmail());
        contentValues.put(DBInfo.user, client.getUser());
        contentValues.put(DBInfo.pass, client.getPass());
        sqLiteDatabase.insert(DBInfo.tblName, null, contentValues);
        ClientInfo clientInfo = new ClientInfo(client.getId(), client.getFirstName(), client.getLastName(), client.getPhone(), client.getEmail(), client.getUser(), client.getPass());
    }

    public boolean checkUser(String user, String pass){
        boolean check = false;
        SQLiteDatabase sqLiteDatabase = dbInfo.getReadableDatabase();
        String columns[] = {DBInfo.id, DBInfo.firstName, DBInfo.lastName, DBInfo.phone, DBInfo.email, DBInfo.user, DBInfo.pass};
        String selectionArgs[] = {user, pass};
        Cursor cursor = sqLiteDatabase.query(DBInfo.tblName, columns, DBInfo.user+"=? AND "+DBInfo.pass+"=?", selectionArgs, null, null, null);
        if (cursor.moveToNext()){
            check = true;
            String id = cursor.getString(0);
            String fn = cursor.getString(1)
                    ;
            String ln = cursor.getString(2);
            String ph = cursor.getString(3);
            String em = cursor.getString(4);
            String us = cursor.getString(5);
            String ps = cursor.getString(6);
            ClientInfo clientInfo = new ClientInfo(id, fn, ln, ph, em, us, ps);
        }
        return check;
    }

    public void updateInfos(Client client){
        SQLiteDatabase sqLiteDatabase = dbInfo.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBInfo.firstName, client.getFirstName());
        contentValues.put(DBInfo.lastName, client.getLastName());
        contentValues.put(DBInfo.phone, client.getPhone());
        contentValues.put(DBInfo.email, client.getEmail());
        contentValues.put(DBInfo.user, client.getUser());
        contentValues.put(DBInfo.pass, client.getPass());
        String wherArgs[] = {client.getId()};
        sqLiteDatabase.update(DBInfo.tblName, contentValues, DBInfo.id + "=?", wherArgs);
        ClientInfo clientInfo = new ClientInfo(client.getId(), client.getFirstName(), client.getLastName(), client.getPhone(), client.getEmail(), client.getUser(), client.getPass());
    }

    public void deleteClient(String id){
        SQLiteDatabase sqLiteDatabase = dbInfo.getWritableDatabase();
        String wherArgs[] = {id};
        //sqLiteDatabase.execSQL("delete from client where id='"+DBInfo.id+"'");
        sqLiteDatabase.delete(DBInfo.tblName, DBInfo.id + "=?", wherArgs);
    }

    static class DBInfo extends SQLiteOpenHelper{

        Context context;
        private static String dbName = "services";
        private static int dbVersion = 1;

        private static String tblName = "client";

        private static String id = "id";
        private static String firstName = "firstName";
        private static String lastName = "lastName";
        private static String phone = "phone";
        private static String email = "email";
        private static String user = "user";
        private static String pass = "pass";

        private static String createTable = "create table client ("+id+" varchar(50) primary key, "
                +firstName+" varchar(50), "+lastName+" varchar(50), "+phone+" varchar(50), "+email+" varchar(50), "
                +user+" varchar(50), "+pass+" varchar(50));";

        private static String dropTable = "drop table if exists client";

        public DBInfo(Context context){
            super(context, dbName, null, dbVersion);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(createTable);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(dropTable);
            onCreate(sqLiteDatabase);
        }
    }
}
