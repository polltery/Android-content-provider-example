package com.example.balraj.contentproviderapp;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Balraj on 12/2/16.
 */

public class CountryContract {
    interface CountryColumns{
        String COUNTRY_ID = "_id";
        String COUNTRY_NAME = "country_name";
        String COUNTRY_CAPITAL = "country_capital";
        String COUNTRY_POPULATION = "country_population";
        String COUNTRY_AREA = "country_area";
        String COUNTRY_LANG = "country_lang";
    }

    // Used to access the content
    public static final String CONTENT_AUTHORITY = "com.example.balraj.contentproviderapp.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    private static final String PATH_COUNTRY = "country";

    public static final Uri URI_TABLE = Uri.parse(BASE_CONTENT_URI.toString() +"/"+PATH_COUNTRY );

    public static final String[] TOP_LEVEL_PATHS = {PATH_COUNTRY};

    // Table for country
    public static class Country implements CountryColumns, BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendEncodedPath(PATH_COUNTRY).build();

        // Accessing content directory and item
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd."+CONTENT_AUTHORITY+".country";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd."+CONTENT_AUTHORITY+".country";

        public static Uri buildCountryUri(String countryId){
            return CONTENT_URI.buildUpon().appendEncodedPath(countryId).build();
        }

        public static String getCountryId(Uri uri){
            return uri.getPathSegments().get(1);
        }

    }
}
