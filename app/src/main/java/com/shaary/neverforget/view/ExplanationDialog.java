package com.shaary.neverforget.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class ExplanationDialog extends DialogFragment {

    public static ExplanationDialog newInstance(String[] permissions, int requestCode) {
        ExplanationDialog dialog = new ExplanationDialog();

        Bundle bundle = new Bundle();
        bundle.putInt("requestCode", requestCode);
        bundle.putStringArray("permissions", permissions);
        dialog.setArguments(bundle);

        return dialog;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        int requestCode = getArguments().getInt("requestCode");
        String message = "It'll do you good";
        String title = "We need power";
        switch (requestCode) {
            case 1:
                title = "Allow access to read contacts?";
                message = "This will save you from typing the name. But the app will work without it";
                break;
            case 3:
                title = "Allow access to take and save photo?";
                message = "This will allow you to take and save the image of your grudge";
                break;
            case 4:
                title = "Allow access to send messages?";
                message = "This will allow you to send grudges to your victims";
                break;
        }

        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) ->
                        requestPermissions(getArguments().getStringArray("permissions"), requestCode))
                .setNegativeButton("CANCEL", (dialog, which) -> dismiss())
                .setCancelable(false);
        return builder.create();
    }
}
