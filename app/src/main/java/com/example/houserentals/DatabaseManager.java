package com.example.houserentals;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "OnlineTicketBooking.db";
    private static final int DATABASE_VERSION = 1;


    private static final String tables[]={"User","Admin"};

    private static final String tableCreatorString[] =
            {"CREATE TABLE User (emailId TEXT, username TEXT, password TEXT, firstName TEXT, lastName TEXT, phone INTEGER, address TEXT, city TEXT, postalCode TEXT);",
                    "CREATE TABLE Admin (employeeId INTEGER PRIMARY KEY AUTOINCREMENT , userName TEXT, password TEXT, firstName TEXT, lastName TEXT, phone INTEGER);",
            };


    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        for (int i = 0; i < tables.length; i++)
            db.execSQL("DROP TABLE IF EXISTS " + tables[i]);

        for (int i = 0; i < tables.length; i++)
            db.execSQL(tableCreatorString[i]);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        for (int i=0;i<tables.length;i++)
            db.execSQL("DROP TABLE IF EXISTS " + tables[i]);


        onCreate(db);
    }

    void addRecord(ContentValues values, String tableName, String fields[], String record[]) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (int i=0;i<record.length;i++)
            values.put(fields[i], record[i]);

        db.insert(tableName, null, values);
        db.close();
    }



    public List getTable(String tableName) {
        List table = new ArrayList(); //to store all rows

        String selectQuery = "SELECT  * FROM " + tableName;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst())
        {
            do
            {
                ArrayList row=new ArrayList();
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    row.add(cursor.getString(i));
                }

                table.add(row);

            } while (cursor.moveToNext());
        }


        return table;
    }


    public List getUser(String tableName, String username) {

        SQLiteDatabase db = this.getReadableDatabase();
        List table = new ArrayList();

        String selection = "username = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(tableName,null,selection,selectionArgs,null,null,null);
        ArrayList row=new ArrayList();

        if (cursor.moveToFirst())
        {

            for (int i = 0; i < cursor.getColumnCount(); i++) {
                row.add(cursor.getString(i));
            }

            table.add(row);
        }


        return table;
    }


    public List getLastRecord(String tableName) {

        List table = new ArrayList();

        String selectQuery = "SELECT  * FROM " + tableName;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList row=new ArrayList();

        if (cursor.moveToLast())
        {

            for (int i = 0; i < cursor.getColumnCount(); i++) {
                row.add(cursor.getString(i));
            }

            table.add(row);
        }


        return table;
    }


    public int updateRecord(ContentValues values, String tableName, String fields[],String record[], int columnNumber) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (int i=1;i<record.length;i++)
            values.put(fields[i], record[i]);


        return db.update(tableName, values, fields[columnNumber] + " = ?",
                new String[] { record[columnNumber] });
    }


    public void deleteRecord(String tableName, String idName, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, idName + " = ?",
                new String[] { id });
        db.close();
    }


    public  boolean checkUser(String tableName, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        String[] coulmns = {"username"};
        String selection = "username = ? AND password = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(tableName,coulmns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        if (count>0) {
            return  true;
        }
        return  false;
    }
}
