package com.example.balraj.contentproviderapp;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by Balraj on 12/11/16.
 */

public class AddActivity extends FragmentActivity {

    private final String LOG_TAG = AddActivity.class.getSimpleName();
    private TextView mNameTextView, mPopulationTextView, mCapitalTextView, mAreaTextView, mLangTextView;
    private Button mButton;
    private ContentResolver mContentResolver;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        mNameTextView = (TextView) findViewById(R.id.countryName);
        mPopulationTextView = (TextView) findViewById(R.id.countryPopulation);
        mCapitalTextView = (TextView) findViewById(R.id.countryCapital);
        mAreaTextView = (TextView) findViewById(R.id.countryArea);
        mLangTextView = (TextView) findViewById(R.id.countryLang);

        mContentResolver = AddActivity.this.getContentResolver();

        mButton = (Button) findViewById(R.id.saveButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid()) {
                    ContentValues values = new ContentValues();
                    values.put(CountryContract.CountryColumns.COUNTRY_NAME, mNameTextView.getText().toString());
                    values.put(CountryContract.CountryColumns.COUNTRY_POPULATION, mPopulationTextView.getText().toString());
                    values.put(CountryContract.CountryColumns.COUNTRY_CAPITAL, mCapitalTextView.getText().toString());
                    values.put(CountryContract.CountryColumns.COUNTRY_AREA, mAreaTextView.getText().toString());
                    values.put(CountryContract.CountryColumns.COUNTRY_LANG, mLangTextView.getText().toString());
                    Uri returned = mContentResolver.insert(CountryContract.URI_TABLE, values);
                    Log.d(LOG_TAG, "record id returned is " + returned.toString());
                    Intent intent = new Intent(AddActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Please ensure your have entered some valid data.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean isValid() {
        if(mNameTextView.getText().toString().length() == 0) {
            return false;
        } else {
            return true;
        }
    }

    private boolean someDataEntered() {
        if(mNameTextView.getText().toString().length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        if(someDataEntered()) {
            CountryDialog dialog = new CountryDialog();
            Bundle args = new Bundle();
            args.putString(CountryDialog.DIALOG_TYPE, CountryDialog.CONFIRM_EXIT);
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager(), "confirm-exit");
        } else {
            super.onBackPressed();
        }
    }
}

