package com.shaary.neverforget.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.shaary.neverforget.R;

public class ExplanationDialog extends DialogFragment {

    public static ExplanationDialog newInstance(String[] permissions, int requestCode) {
        ExplanationDialog dialog = new ExplanationDialog();

        Bundle bundle = new Bundle();
        bundle.putInt("requestCode", requestCode);
        bundle.putStringArray("permissions", permissions);
        dialog.setArguments(bundle);

        return dialog;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        int requestCode = getArguments().getInt("requestCode");
        String message = getString(R.string.explanation_default_message);
        String title = getString(R.string.explanation_default_title);
        switch (requestCode) {
            case 3:
                message = getString(R.string.explanation_photo_and_storage_message);
                title = getString(R.string.explanation_photo_and_storage_title);
                break;
            case 4:
                message = getString(R.string.explanation_sms_message);
                title = getString(R.string.explanation_sms_title);
                break;
        }

        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok_button_text, (dialog, which) ->
                        requestPermissions(getArguments().getStringArray("permissions"), requestCode))
                .setNegativeButton(R.string.cancel_button_text, (dialog, which) -> dismiss())
                .setCancelable(false);
        return builder.create();
    }
}
