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

    //Array containing all tables name
    private static final String tables[]={"User","Admin"};
    //Sql queries to create tables
    private static final String tableCreatorString[] =
            {"CREATE TABLE User (emailId TEXT, username TEXT, password TEXT, firstName TEXT, lastName TEXT, phone INTEGER, address TEXT, city TEXT, postalCode TEXT);",
                    "CREATE TABLE Admin (employeeId INTEGER PRIMARY KEY AUTOINCREMENT , userName TEXT, password TEXT, firstName TEXT, lastName TEXT, phone INTEGER);",
            };

    //class constructor
    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    //initialize database table names and DDL statements
//    public void dbInitialize(String[] tables, String tableCreatorString[])
//    {
//        this.tables=tables;
//        this.tableCreatorString=tableCreatorString;
//    }

    // Create tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Drop existing tables
        for (int i = 0; i < tables.length; i++)
            db.execSQL("DROP TABLE IF EXISTS " + tables[i]);
        //create them
        for (int i = 0; i < tables.length; i++)
            db.execSQL(tableCreatorString[i]);

    }
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop existing tables
        for (int i=0;i<tables.length;i++)
            db.execSQL("DROP TABLE IF EXISTS " + tables[i]);

        // Create tables again
        onCreate(db);
    }
    /////////////////////////
    // Database operations
    /////////////////////////
    // Add a new record
    void addRecord(ContentValues values, String tableName, String fields[], String record[]) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (int i=0;i<record.length;i++)
            values.put(fields[i], record[i]);
        // Insert the row
        db.insert(tableName, null, values);
        db.close(); //close database connection
    }


    // Read all records
    public List getTable(String tableName) {
        List table = new ArrayList(); //to store all rows
        // Select all records
        String selectQuery = "SELECT  * FROM " + tableName;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        //to store one row
        //scroll over rows and store each row in an array list object
        if (cursor.moveToFirst())
        {
            do
            { // for each row
                ArrayList row=new ArrayList();
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    row.add(cursor.getString(i));
                }

                table.add(row); //add row to the list

            } while (cursor.moveToNext());
        }

        // return table as a list
        return table;
    }

    // Read a record
    public List getUser(String tableName, String username) {

        SQLiteDatabase db = this.getReadableDatabase();
        List table = new ArrayList(); //to store all rows

        String selection = "username = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(tableName,null,selection,selectionArgs,null,null,null);
        ArrayList row=new ArrayList(); //to store one row
        //scroll over rows and store each row in an array list object
        if (cursor.moveToFirst())
        {
            // for each row
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                row.add(cursor.getString(i));
            }

            table.add(row); //add row to the list
        }

        // return table as a list
        return table;
    }

    // Read a record
    public List getLastRecord(String tableName) {

        List table = new ArrayList(); //to store all rows
        // Select all records
        String selectQuery = "SELECT  * FROM " + tableName;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList row=new ArrayList(); //to store one row
        //scroll over rows and store each row in an array list object
        if (cursor.moveToLast())
        {
            // for each row
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                row.add(cursor.getString(i));
            }

            table.add(row); //add row to the list
        }

        // return table as a list
        return table;
    }

    // Update a record
    public int updateRecord(ContentValues values, String tableName, String fields[],String record[], int columnNumber) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (int i=1;i<record.length;i++)
            values.put(fields[i], record[i]);

        // updating row with given id = record[0]
        return db.update(tableName, values, fields[columnNumber] + " = ?",
                new String[] { record[columnNumber] });
    }

    // Delete a record with a given id
    public void deleteRecord(String tableName, String idName, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, idName + " = ?",
                new String[] { id });
        db.close();
    }

    // Check user exists
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
