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

/**
 * Created by Balraj on 12/11/16.
 */

public class EditActivity extends FragmentActivity {
    private final String LOG_TAG = EditActivity.class.getSimpleName();
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

        mContentResolver = EditActivity.this.getContentResolver();

        Intent intent = getIntent();
        final String _id = intent.getStringExtra(CountryContract.CountryColumns.COUNTRY_ID);
        String name = intent.getStringExtra(CountryContract.CountryColumns.COUNTRY_NAME);
        String population = intent.getStringExtra(CountryContract.CountryColumns.COUNTRY_POPULATION);
        String capital = intent.getStringExtra(CountryContract.CountryColumns.COUNTRY_CAPITAL);
        String area = intent.getStringExtra(CountryContract.CountryColumns.COUNTRY_AREA);
        String lang = intent.getStringExtra(CountryContract.CountryColumns.COUNTRY_LANG);

        mNameTextView.setText(name);
        mPopulationTextView.setText(population);
        mCapitalTextView.setText(capital);
        mAreaTextView.setText(area);
        mLangTextView.setText(lang);

        mButton = (Button) findViewById(R.id.saveButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(CountryContract.CountryColumns.COUNTRY_NAME, mNameTextView.getText().toString());
                values.put(CountryContract.CountryColumns.COUNTRY_POPULATION, mPopulationTextView.getText().toString());
                values.put(CountryContract.CountryColumns.COUNTRY_CAPITAL, mCapitalTextView.getText().toString());
                values.put(CountryContract.CountryColumns.COUNTRY_AREA, mAreaTextView.getText().toString());
                values.put(CountryContract.CountryColumns.COUNTRY_LANG, mLangTextView.getText().toString());
                Uri uri = CountryContract.Country.buildCountryUri(_id);
                int recordsUpdated = mContentResolver.update(uri, values, null, null);
                Log.d(LOG_TAG, "number of records update = " + recordsUpdated);
                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
