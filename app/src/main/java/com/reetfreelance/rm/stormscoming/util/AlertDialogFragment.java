package com.reetfreelance.rm.stormscoming.util;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.reetfreelance.rm.stormscoming.R;

public class AlertDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context=getActivity();
        AlertDialog.Builder builder=new AlertDialog.Builder(context);

        builder.setTitle(R.string.errorDialogTitle)
                .setMessage(R.string.connectionErrorMessage)
                .setPositiveButton(R.string.okButtonLabel,null);

        final AlertDialog alertDialog=builder.create();

        //change color of ok button in dialog
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                alertDialog
                        .getButton(AlertDialog.BUTTON_POSITIVE)
                        .setTextColor(getResources()
                                .getColor(R.color.colorAlertDialogButtonText));
            }
        });
        return alertDialog;
    }
}
