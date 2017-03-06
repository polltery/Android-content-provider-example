package com.example.balraj.contentproviderapp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by Balraj on 12/2/16.
 */

public class CountryProvider extends ContentProvider {
    private CountryDatabase mOpenHelper;

    private static String TAG = CountryProvider.class.getSimpleName();

    // Checks for valid URIs
    private static UriMatcher sUriMatcher = buildUriMatcher();

    private static final int COUNTRY = 100;
    private static final int COUNTRY_ID = 101;

    private static UriMatcher buildUriMatcher(){

        // Initalize
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        final String authority = CountryContract.CONTENT_AUTHORITY;

        // See if matches country records
        matcher.addURI(authority, "country", COUNTRY);

        // See if matches country item
        matcher.addURI(authority, "country/#", COUNTRY_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new CountryDatabase(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch(match){
            case COUNTRY:
                return CountryContract.Country.CONTENT_TYPE;
            case COUNTRY_ID:
                return CountryContract.Country.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI : "+ uri);
        }
    }

    private void deleteDatabase(){
        mOpenHelper.close();
        CountryDatabase.deleteDatabse(getContext());
        mOpenHelper = new CountryDatabase(getContext());
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.v(TAG,"insert(uri="+ uri + ", values="+values.toString());
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match){
            case COUNTRY:
                // Create a new record
                long recordId = db.insertOrThrow(CountryDatabase.Tables.COUNTRY, null, values);
                return CountryContract.Country.buildCountryUri(String.valueOf(recordId));
            default:
                throw new IllegalArgumentException("Unknown URI : "+ uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.v(TAG,"delete(uri="+ uri);
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);

        switch(match){
            case COUNTRY:
                // Do nothing
                break;
            case COUNTRY_ID:
                String id = CountryContract.Country.getCountryId(uri);
                String selectionCriteria = BaseColumns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : "");
                return db.delete(CountryDatabase.Tables.COUNTRY, selectionCriteria, selectionArgs);
            default:
                throw new IllegalArgumentException("Unknown URI : " + uri);
        }

        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.v(TAG,"update(uri="+ uri + ", values="+values.toString());
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);

        String selectionCriteria = selection;

        switch (match){
            case COUNTRY:
                // Do nothing
                break;
            case COUNTRY_ID:
                String id = CountryContract.Country.getCountryId(uri);
                selectionCriteria = BaseColumns._ID + "=" + id
                            + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : "");
                break;
            default:
                throw new IllegalArgumentException("Unknown URI : "+ uri);
        }

        int updateCount = db.update(CountryDatabase.Tables.COUNTRY, values, selectionCriteria, selectionArgs);
        return updateCount;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        final int match = sUriMatcher.match(uri);

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        queryBuilder.setTables(CountryDatabase.Tables.COUNTRY);

        switch(match){
            case COUNTRY:
                break;
            case COUNTRY_ID:
                String id  = CountryContract.Country.getCountryId(uri);
                queryBuilder.appendWhere(BaseColumns._ID + "="+ id);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI : "+ uri);
        }

        // Projection : Columns to return
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        return cursor;
    }
}
