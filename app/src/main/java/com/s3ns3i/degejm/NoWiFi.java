package com.s3ns3i.degejm;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NoWiFi extends DialogFragment {

//    private static final String OPTION = "option";
//    public static final String WAIT = "wait";
//    public static final String CLOSE = "close";

    public NoWiFi(){}

    public static NoWiFi newInstance(/*String option*/) {
        NoWiFi fragment = new NoWiFi();
//        Bundle bundle = new Bundle();
//        bundle.putString(OPTION, option);
//        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.noWiFiError)
                .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
//                        if(CLOSE.equals(savedInstanceState.getString(CLOSE)))
//                            getActivity().finish();
//                        else if(WAIT.equals(savedInstanceState.getString(WAIT)))
//                            return;
                    }
                });

        return builder.create();
    }

}
