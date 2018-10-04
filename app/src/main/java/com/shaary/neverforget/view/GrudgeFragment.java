package com.shaary.neverforget.view;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.shaary.neverforget.R;
import com.shaary.neverforget.controller.DatePickerFragment;
import com.shaary.neverforget.controller.PictureUtils;
import com.shaary.neverforget.model.Grudge;
import com.shaary.neverforget.model.GrudgePit;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */

//Communicates with DatePickerFragment
public class GrudgeFragment extends Fragment {

    private static final String ARG_GRUDGE_ID = "grudge_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_CONTACT = 1;
    private static final int REQUEST_PHOTO = 2;
    private static final String TAG = GrudgeFragment.class.getSimpleName();

    private Grudge grudge;
    private File photoFile;
    private Callbacks callbacks;

    @BindView(R.id.grudge_title) EditText titleField;
    @BindView(R.id.grudge_description_text) EditText descriptionField;
    @BindView(R.id.grudge_date_button) Button dateButton;
    @BindView(R.id.grudge_send_button) Button sendButton;
    @BindView(R.id.grudge_victim_button) Button victimButton;
    @BindView(R.id.grudge_remind) CheckBox remindCheckBox;
    @BindView(R.id.grudge_revenge) CheckBox revengeCheckBox;
    @BindView(R.id.grudge_forgive) CheckBox forgiveCheckBox;
    @BindView(R.id.grudge_forgiven_text) TextView forgiveText;
    @BindView(R.id.grudge_image_view) ImageView grudgeImage;
    @BindView(R.id.grudge_photo_button) ImageButton photoButton;


    public static GrudgeFragment newInstance(UUID grudgeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_GRUDGE_ID, grudgeId);

        GrudgeFragment fragment = new GrudgeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public interface Callbacks {
        void onGrudgeUpdated(Grudge grudge);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callbacks = (Callbacks) context;
    }

    //TODO: make the class smaller
    //TODO: add scroll view to the layout
    //TODO: add request for using camera and storage and contact list

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grudge, container, false);
        ButterKnife.bind(this, view);

        titleField.setText(grudge.getTitle());
        titleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                grudge.setTitle(s.toString());
                updateGrudge();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        descriptionField.setText(grudge.getDescription());
        descriptionField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                grudge.setDescription(s.toString());
                updateGrudge();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        final Intent pickContact = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);
        victimButton.setOnClickListener(v -> startActivityForResult(pickContact, REQUEST_CONTACT));
        if (grudge.getVictim() != null) {
            victimButton.setText(grudge.getVictim());
        }

        updateDate();

        //Shows DatePickerFragment on a day of the grudge
        dateButton.setOnClickListener(v -> {
            FragmentManager fragmentManager = getFragmentManager();
            DatePickerFragment dialog = DatePickerFragment
                    .newInstance(grudge.getDate());

            //Sets this fragment as a receiver of a picked date
            dialog.setTargetFragment(GrudgeFragment.this, REQUEST_DATE);
            dialog.show(fragmentManager, DIALOG_DATE);
        });

        sendButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, getGrudgeReport());
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.grudge_report_subject));
            intent = Intent.createChooser(intent, getString(R.string.send_report));
            startActivity(intent);
        });

        remindCheckBox.setChecked(grudge.isRemind());
        remindCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            grudge.setRemind(isChecked);
            updateGrudge();
        });

        //If isRevengeTrue changes view of the grudge
        revengeCheckBox.setChecked(grudge.isRevenge());
        revengeCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            grudge.setRevenge(isChecked);
            updateGrudge();
        });

        forgiveCheckBox.setChecked(grudge.isForgiven());
        //TODO: DRY the code
        if(grudge.isForgiven()) {
            forgiveText.setVisibility(View.VISIBLE);
            revengeCheckBox.setEnabled(false);
        }
        forgiveCheckBox.setOnCheckedChangeListener(((buttonView, isChecked) -> {grudge.setForgiven(isChecked);
            if(grudge.isForgiven()) {
                forgiveText.setVisibility(View.VISIBLE);
                revengeCheckBox.setEnabled(false);
            } else  {
                forgiveText.setVisibility(View.INVISIBLE);
                revengeCheckBox.setEnabled(true);
            }}));



        //TODO: make 2 options: choose from contact list or write the name down
        //Checks if there are apps to open the intent
        PackageManager packageManager = getActivity().getPackageManager();
        if (packageManager.resolveActivity(pickContact, PackageManager.MATCH_DEFAULT_ONLY) == null) {
            victimButton.setEnabled(false);
        }

        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        boolean canTakePhoto = photoFile != null &&
                captureImage.resolveActivity(packageManager) != null;
        photoButton.setEnabled(canTakePhoto);

        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = FileProvider.getUriForFile(getActivity(),
                        "com.shaary.android.grudgeintent.fileprovider",
                        photoFile);
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                List<ResolveInfo> cameraActivities = getActivity()
                        .getPackageManager().queryIntentActivities(captureImage, PackageManager.MATCH_DEFAULT_ONLY);

                for (ResolveInfo activity : cameraActivities) {
                    getActivity().grantUriPermission(activity.activityInfo.packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

        updateGrudgeImage();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID grudgeId = (UUID) getArguments().getSerializable(ARG_GRUDGE_ID);
        grudge = GrudgePit.get(getActivity()).getGrudge(grudgeId);
        photoFile = GrudgePit.get(getActivity()).getPhotoFile(grudge);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            grudge.setDate(date);
            updateGrudge();
            updateDate();
        } else if (requestCode == REQUEST_CONTACT && data != null){
            Uri contactUri = data.getData();
            //Specify which fields you want to query to return values for
            String[] queryFields = new String[] {
                    ContactsContract.Contacts.DISPLAY_NAME
            };
            //Performs query - the contactUri is like a "where" clause
            Cursor cursor = getActivity().getContentResolver()
                            .query(contactUri, queryFields, null, null, null);

            try {
                //Double check the results
                if (cursor.getCount() == 0) {
                    return;
                }
                //Pull out the first column of the first row of data
                cursor.moveToFirst();
                String victim = cursor.getString(0);
                grudge.setVictim(victim);
                updateGrudge();
                victimButton.setText(victim);
            } finally {
                cursor.close();
            }

        } else if (requestCode == REQUEST_PHOTO) {
            Uri uri = FileProvider.getUriForFile(getActivity(),
                    "com.shaary.android.grudgeintent.fileprovider",
                    photoFile);
            getActivity().revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            updateGrudge();
            updateGrudgeImage();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        GrudgePit.get(getActivity())
                .updateGrudge(grudge);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }

    private void updateDate() {
        dateButton.setText(grudge.getFormattedDate());
    }

    private void updateGrudge() {
        GrudgePit.get(getActivity()).updateGrudge(grudge);
        callbacks.onGrudgeUpdated(grudge);
    }

    private void updateGrudgeImage() {
        if (photoFile == null || !photoFile.exists()) {
            grudgeImage.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(photoFile.getPath(), getActivity());
            grudgeImage.setImageBitmap(bitmap);
        }
    }

    private String getGrudgeReport() {
        String forgiven = "";
        if(grudge.isForgiven()) {
            forgiven = getString(R.string.grudge_forgiven_report);
        } else {
            forgiven = getString(R.string.grudge_not_forgiven_report);
        }

        String description = grudge.getDescription();
        if (description == null) {
            description = "you were mean to me ";
        }
        String date = grudge.getFormattedDate();
        String name = grudge.getVictim();
        if (name == null) {
            //TODO: come up with more polite name :D
            name = "***";
        }
        String report = getString(R.string.grudge_message_format, name, description, date, forgiven);
        return report;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_grudge, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.grudge_delete:
                GrudgePit.get(getActivity()).deleteGrudge(grudge);
                getActivity().finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
