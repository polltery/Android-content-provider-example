package com.example.balraj.contentproviderapp;

import android.annotation.TargetApi;
import android.support.v4.content.AsyncTaskLoader;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Balraj on 12/2/16.
 */

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CountryListLoader extends AsyncTaskLoader<List<Country>> {
    private static final String LOG_TAG = CountryListLoader.class.getSimpleName();
    private List<Country> mCountry;
    private ContentResolver mContentResolver;
    private Cursor mCursor;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public CountryListLoader(Context context, Uri uri, ContentResolver contentResolver){
        super(context);
        mContentResolver = contentResolver;
    }

    @Override
    public List<Country> loadInBackground() {
        String[] projection = {BaseColumns._ID,
                CountryContract.CountryColumns.COUNTRY_NAME,
                CountryContract.CountryColumns.COUNTRY_POPULATION,
                CountryContract.CountryColumns.COUNTRY_CAPITAL,
                CountryContract.CountryColumns.COUNTRY_AREA,
                CountryContract.CountryColumns.COUNTRY_LANG};

        List<Country> entries = new ArrayList<Country>();

        mCursor = mContentResolver.query(CountryContract.URI_TABLE, projection, null, null, CountryContract.CountryColumns.COUNTRY_NAME + " limit 20");

        if(mCursor != null){
            if(mCursor.moveToFirst()){
                do{
                    int _id = mCursor.getInt(mCursor.getColumnIndex(BaseColumns._ID));
                    String name = mCursor.getString(mCursor.getColumnIndex(CountryContract.CountryColumns.COUNTRY_NAME));
                    String population = mCursor.getString(mCursor.getColumnIndex(CountryContract.CountryColumns.COUNTRY_POPULATION));
                    String capital = mCursor.getString(mCursor.getColumnIndex(CountryContract.CountryColumns.COUNTRY_CAPITAL));
                    String area = mCursor.getString(mCursor.getColumnIndex(CountryContract.CountryColumns.COUNTRY_AREA));
                    String lang = mCursor.getString(mCursor.getColumnIndex(CountryContract.CountryColumns.COUNTRY_LANG));
                    Country country = new Country(_id,area,capital,lang,name,population);
                    entries.add(country);
                }while(mCursor.moveToNext());
            }
        }
        return entries;
    }

    @Override
    public void deliverResult(List<Country> country) {

        if(isReset()){
            if(country != null){
                mCursor.close();
            }
        }

        List<Country> oldCountryList = mCountry;

        if(mCountry == null || mCountry.size() == 0){
            Log.d(LOG_TAG, "++++++++++++ NO DATA RETURNED ");
        }

        // Saving the country
        mCountry = country;

        if(isStarted()){
            super.deliverResult(country);
        }

        if(oldCountryList != null && oldCountryList != country){
            mCursor.close();
        }
    }

    @Override
    protected void onStartLoading() {
        if(mCountry != null){
            deliverResult(mCountry);
        }

        if(takeContentChanged() || mCountry == null){
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        onStopLoading();
        if(mCursor != null){
            mCursor.close();
        }

        mCountry = null;
    }

    @Override
    public void onCanceled(List<Country> country) {
        super.onCanceled(country);
        if(mCursor != null){
            mCursor.close();
        }
    }

    @Override
    public void forceLoad() {
        super.forceLoad();
    }
}
