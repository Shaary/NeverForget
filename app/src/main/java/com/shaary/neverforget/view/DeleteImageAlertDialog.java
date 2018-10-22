package com.shaary.neverforget.view;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.shaary.neverforget.R;

public class DeleteImageAlertDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete image")
                .setMessage("Do you want to delete this image?")
                .setPositiveButton(R.string.ok_button_text, (dialog, which) -> {
                    Intent intent = new Intent();
                    intent.putExtra("delete", "delete");
                    getTargetFragment().onActivityResult(
                            getTargetRequestCode(), Activity.RESULT_OK, intent);
                    dismiss();
                })
                .setNegativeButton(R.string.cancel_button_text, (dialog, which) -> dismiss())
                .setCancelable(false);
        return builder.create();
    }

}
