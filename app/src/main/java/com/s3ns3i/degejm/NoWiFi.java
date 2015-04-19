package com.s3ns3i.degejm;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NoWiFi extends DialogFragment {

    public static enum Option{
        CLOSE, WAIT
    }

    private Option option;

    public NoWiFi(Option option){
        this.option = option;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.noWiFiError)
                .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(option) {
                            case CLOSE:
                                getActivity().finish() ;
                                break;
                            case WAIT:
                                break;
                        }
                    }
                });

        return builder.create();
    }

}
