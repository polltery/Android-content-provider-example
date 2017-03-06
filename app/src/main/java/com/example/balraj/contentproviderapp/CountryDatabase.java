package com.example.balraj.contentproviderapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Balraj on 12/2/16.
 */

public class CountryDatabase extends SQLiteOpenHelper {
    private static final String TAG = CountryDatabase.class.getSimpleName();
    private static final String DATABASE_NAME = "country.db";
    private static final int DATABASE_VERSION = 1;
    private final Context mContext;

    interface Tables{
        String COUNTRY = "country";
    }

    public CountryDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create a SQLite DB on create
        db.execSQL("CREATE TABLE " + Tables.COUNTRY + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CountryContract.CountryColumns.COUNTRY_NAME + " TEXT NOT NULL,"
                + CountryContract.CountryColumns.COUNTRY_POPULATION + " TEXT NOT NULL,"
                + CountryContract.CountryColumns.COUNTRY_CAPITAL + " TEXT NOT NULL,"
                + CountryContract.CountryColumns.COUNTRY_AREA + " TEXT NOT NULL,"
                + CountryContract.CountryColumns.COUNTRY_LANG + " TEXT NOT NULL)");

        db.execSQL("INSERT INTO " + Tables.COUNTRY + " ("
                + CountryContract.CountryColumns.COUNTRY_NAME + ","
                + CountryContract.CountryColumns.COUNTRY_POPULATION + ","
                + CountryContract.CountryColumns.COUNTRY_CAPITAL + ","
                + CountryContract.CountryColumns.COUNTRY_AREA + ","
                + CountryContract.CountryColumns.COUNTRY_LANG + ")"
                + "VALUES('India','1.252 Billion', 'Delhi', '13,287,263 km Square', 'Hindi, English') ");

        db.execSQL("INSERT INTO " + Tables.COUNTRY + " ("
                + CountryContract.CountryColumns.COUNTRY_NAME + ","
                + CountryContract.CountryColumns.COUNTRY_POPULATION + ","
                + CountryContract.CountryColumns.COUNTRY_CAPITAL + ","
                + CountryContract.CountryColumns.COUNTRY_AREA + ","
                + CountryContract.CountryColumns.COUNTRY_LANG + ")"
                + "VALUES('South Korea','50,801,405', 'Seoul', '100,210 km Square', 'Korean')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static void deleteDatabse(Context context){

        // Delete the database
        context.deleteDatabase(DATABASE_NAME);
    }
}
