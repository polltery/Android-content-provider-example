package com.example.balraj.contentproviderapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.balraj.contentproviderapp.Country;
import com.example.balraj.contentproviderapp.CountryContract;
import com.example.balraj.contentproviderapp.R;

import java.util.List;


/**
 * Created by Balraj on 12/11/16.
 */

public class CountryCustomAdapter extends ArrayAdapter<Country> {
    private LayoutInflater mLayoutInflater;
    private static FragmentManager sFragmentManager;

    public CountryCustomAdapter(Context context, FragmentManager fragmentManager) {
        super(context, android.R.layout.simple_list_item_2);
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sFragmentManager = fragmentManager;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view;
        if(convertView == null) {
            view = mLayoutInflater.inflate(R.layout.custom_country, parent, false);
        } else {
            view = convertView;
        }

        final Country country = getItem(position);
        final int _id = country.getId();
        final String name = country.getName();
        final String population = country.getPopulation();
        final String capital = country.getCapital();
        final String area = country.getArea();
        final String lang = country.getLang();

        ((TextView) view.findViewById(R.id.country_name)).setText(name);
        ((TextView) view.findViewById(R.id.country_population)).setText(population);
        ((TextView) view.findViewById(R.id.country_capital)).setText(capital);
        ((TextView) view.findViewById(R.id.country_area)).setText(area);
        ((TextView) view.findViewById(R.id.country_lang)).setText(lang);

        Button editButton = (Button) view.findViewById(R.id.edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditActivity.class);
                intent.putExtra(CountryContract.CountryColumns.COUNTRY_ID, String.valueOf(_id));
                intent.putExtra(CountryContract.CountryColumns.COUNTRY_NAME, name);
                intent.putExtra(CountryContract.CountryColumns.COUNTRY_POPULATION, population);
                intent.putExtra(CountryContract.CountryColumns.COUNTRY_CAPITAL, capital);
                intent.putExtra(CountryContract.CountryColumns.COUNTRY_AREA, area);
                intent.putExtra(CountryContract.CountryColumns.COUNTRY_LANG, lang);
                getContext().startActivity(intent);
            }
        });

        Button deleteButton = (Button) view.findViewById(R.id.delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CountryDialog dialog = new CountryDialog();
                Bundle args = new Bundle();
                args.putString(CountryDialog.DIALOG_TYPE, CountryDialog.DELETE_RECORD);
                args.putInt(CountryContract.CountryColumns.COUNTRY_ID, _id);
                args.putString(CountryContract.CountryColumns.COUNTRY_NAME, name);
                args.putString(CountryContract.CountryColumns.COUNTRY_POPULATION, population);
                args.putString(CountryContract.CountryColumns.COUNTRY_CAPITAL, capital);
                args.putString(CountryContract.CountryColumns.COUNTRY_AREA, area);
                args.putString(CountryContract.CountryColumns.COUNTRY_LANG, lang);
                dialog.setArguments(args);
                dialog.show(sFragmentManager, "delete-record");
            }
        });

        return view;
    }

    public void setData(List<Country> country) {
        clear();
        if(country != null) {
            for(Country countryItem : country) {
                add(countryItem);
            }
        }
    }
}

