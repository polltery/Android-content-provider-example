package com.example.balraj.contentproviderapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Balraj on 12/11/16.
 */

public class CountryDialog extends DialogFragment {
    private static final String LOG_TAG = CountryDialog.class.getSimpleName();
    private LayoutInflater mLayoutInflater;
    public static final String DIALOG_TYPE = "command";
    public static final String DELETE_RECORD = "deleteRecord";
    public static final String CONFIRM_EXIT = "confirmExit";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        mLayoutInflater = getActivity().getLayoutInflater();
        final View view = mLayoutInflater.inflate(R.layout.country_layout, null);
        String command = getArguments().getString(DIALOG_TYPE);
        if (command.equals(DELETE_RECORD)) {
            final int _id = getArguments().getInt(CountryContract.CountryColumns.COUNTRY_ID);
            String name = getArguments().getString(CountryContract.CountryColumns.COUNTRY_NAME);
            TextView popupMessage = (TextView) view.findViewById(R.id.popup_message);
            popupMessage.setText("Are you sure you want to delete " + name + " from your country list?");
            builder.setView(view).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getActivity(), "Deleting record: " + _id, Toast.LENGTH_LONG).show();
                    ContentResolver contentResolver = getActivity().getContentResolver();
                    Uri uri = CountryContract.Country.buildCountryUri(String.valueOf(_id));
                    contentResolver.delete(uri, null, null);
                    Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {

                        }
                    });
        } else if (command.equals(CONFIRM_EXIT)) {
            TextView popupMessage = (TextView) view.findViewById(R.id.popup_message);
            popupMessage.setText("Are you sure you wish to exit without saving?");
            builder.setView(view).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getActivity().finish();
                }
            })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {

                        }
                    });
        } else {
            Log.d(LOG_TAG, "Invalid command passed as parameter");
        }

        return builder.create();
    }
}

