package com.shaary.neverforget.view;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.shaary.neverforget.R;
import com.shaary.neverforget.controller.GrudgeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class VictimChooserDialogFragment extends DialogFragment {

    @BindView(R.id.victim_name_edit_text) EditText editText;
    @BindView(R.id.victim_ok_button) Button okButton;
    @BindView(R.id.victim_cancel_button) Button cancelButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_victim_chooser_dialog, container, false);
        ButterKnife.bind(this, view);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("victim", name);
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                getDialog().dismiss();
            }
        });

        cancelButton.setOnClickListener(v -> getDialog().dismiss());

        return view;
    }

}
