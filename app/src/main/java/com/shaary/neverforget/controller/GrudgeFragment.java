package com.shaary.neverforget.controller;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.shaary.neverforget.R;
import com.shaary.neverforget.model.Grudge;
import com.shaary.neverforget.model.GrudgePit;
import com.shaary.neverforget.view.DeleteImageAlertDialog;
import com.shaary.neverforget.view.ExplanationDialog;
import com.shaary.neverforget.view.GrudgeImageFragment;
import com.shaary.neverforget.view.InfoFragment;
import com.shaary.neverforget.view.VictimChooserDialogFragment;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
    public static final int REQUEST_PHOTO_AND_STORAGE = 3;
    private static final int REQUEST_TIME = 5;
    private static final int REQUEST_DELETE_IMAGE = 6;

    private final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

    private static final String TAG = GrudgeFragment.class.getSimpleName();

    private Grudge grudge;
    private File photoFile;
    private Callbacks callbacks;

    @BindView(R.id.grudge_title) EditText titleField;
    @BindView(R.id.grudge_description_text) EditText descriptionField;
    @BindView(R.id.grudge_date_button) Button dateButton;
    @BindView(R.id.grudge_time_button) Button timeButton;
    @BindView(R.id.grudge_send_button) Button sendButton;
    @BindView(R.id.grudge_victim_button) Button victimButton;
    @BindView(R.id.grudge_remind) CheckBox remindCheckBox;
    @BindView(R.id.grudge_revenge) CheckBox revengeCheckBox;
    @BindView(R.id.grudge_revenged) CheckBox revengedCheckBox;
    @BindView(R.id.grudge_forgive) CheckBox forgiveCheckBox;
    @BindView(R.id.grudge_forgiven_text) TextView forgiveText;
    @BindView(R.id.grudge_image_view) ImageView grudgeImage;
    @BindView(R.id.grudge_photo_button) ImageButton photoButton;
    @BindView(R.id.grudge_scroll_view) ScrollView layout;


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
    //TODO: add reminder

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView: language " + Locale.getDefault().getLanguage());
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

        victimButton.setOnClickListener(v -> openVictimChooserDialog());

        if (grudge.getVictim() != null) {
            if (!grudge.getVictim().isEmpty()) {
                victimButton.setText(grudge.getVictim());
            }
        }

        if (grudge.getGender() == null) {
            grudge.setGender(getString(R.string.gender_female));
        }

        updateDate();
        updateTime();

        //Shows DatePickerFragment on a day of the grudge
        dateButton.setOnClickListener(v -> {
            FragmentManager fragmentManager = getFragmentManager();
            DatePickerFragment dialog = DatePickerFragment
                    .newInstance(grudge.getDate());

            //Sets this fragment as a receiver of a picked date
            dialog.setTargetFragment(GrudgeFragment.this, REQUEST_DATE);
            dialog.show(fragmentManager, DIALOG_DATE);
        });

        timeButton.setOnClickListener(v -> {
            DialogFragment newFragment = TimePickerFragment
                    .newInstance(grudge.getTime());
            newFragment.setTargetFragment(GrudgeFragment.this, REQUEST_TIME);
            newFragment.show(getFragmentManager(), "timePicker");

        });
        Log.d(TAG, "onCreateView: time " + grudge.getTime());

        //Sends intent to message sending apps
        sendButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, getGrudgeReport());
            Log.d(TAG, "onCreateView: grudge report " + getGrudgeReport());
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.grudge_report_subject));
            intent = Intent.createChooser(intent, getString(R.string.send_report));
            startActivity(intent);

            //If victim's name == null asks if it was left blank intentionally
            if (grudge.getVictim() == null || grudge.getVictim().isEmpty()) {
                TSnackbar snackbar = TSnackbar.make(getActivity()
                                .findViewById(R.id.fragment_grudge_relative_layout),
                        R.string.no_name_string, Snackbar.LENGTH_LONG);

                snackbar.setAction(R.string.enter_the_name, v1 -> openVictimChooserDialog());
                snackbar.show();
            }
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
            revengedCheckBox.setEnabled(isChecked);
            setLayout();
            updateGrudge();
        });

        //Can check Revenged only if you had checked Revenge first
        revengedCheckBox.setEnabled(grudge.isRevenge());
        revengedCheckBox.setChecked(grudge.isRevenged());
        if(grudge.isRevenged()) {
            Log.d(TAG, "onCreateView: revenged " + grudge.isRevenged());
            //Can't uncheck Revenge if already revenged
            revengeCheckBox.setEnabled(false);
        }
        revengedCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            grudge.setRevenged(isChecked);
            revengeCheckBox.setEnabled(!isChecked);
            setLayout();
            updateGrudge();
        });

        forgiveCheckBox.setChecked(grudge.isForgiven());
        if (grudge.isForgiven()) {
            forgiveText.setVisibility(View.VISIBLE);
            revengeCheckBox.setEnabled(false);
            revengedCheckBox.setEnabled(false);
        }

        forgiveCheckBox.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            grudge.setForgiven(isChecked);
            forgiveText.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
            if (!grudge.isRevenged()) {
                revengeCheckBox.setEnabled(!isChecked);
            }
            revengedCheckBox.setEnabled(!isChecked);
            setLayout();
            updateGrudge();}));

        //Checks if there are apps to open the intent
        PackageManager packageManager = getActivity().getPackageManager();

        //Checks for camera
        boolean canTakePhoto = photoFile != null &&
                captureImage.resolveActivity(packageManager) != null;
        photoButton.setEnabled(canTakePhoto);
        photoButton.setOnClickListener(v -> requestPermissions(new String[] {Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PHOTO_AND_STORAGE));

        grudgeImage.setLongClickable(true);
        grudgeImage.setOnClickListener(v -> {
            FragmentManager fragmentManager = getFragmentManager();
            GrudgeImageFragment fragment = GrudgeImageFragment.newInstance(photoFile);
            fragment.show(fragmentManager, "zoomedGrudge");
        });
        grudgeImage.setOnLongClickListener(v -> {
            DeleteImageAlertDialog dialog = new DeleteImageAlertDialog();
            dialog.setTargetFragment(GrudgeFragment.this, REQUEST_DELETE_IMAGE);
            dialog.show(getFragmentManager(), "deleteImage");
            return false;
        });
        updateGrudgeImage();
        setLayout();
        return view;
    }

    private void setLayout() {
        int imagePath = GrudgeFragmentHelper.getLayoutId(grudge);

        Glide.with(this).load(imagePath).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                layout.setBackground(resource);
            }
        });

    }

    private void openVictimChooserDialog() {
        VictimChooserDialogFragment dialogFragment = VictimChooserDialogFragment.newInstance(grudge);
        dialogFragment.setTargetFragment(this, REQUEST_CONTACT);
        dialogFragment.show(getFragmentManager(), "victim");
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
        } else if (requestCode == REQUEST_TIME) {
            int hour = (int) data.getSerializableExtra(TimePickerFragment.EXTRA_HOUR);
            int minute = (int) data.getSerializableExtra(TimePickerFragment.EXTRA_MINUTE);
            grudge.setTime(hour, minute);
            Log.d(TAG, "onActivityResult: setTime " + grudge.getTime());
            updateTime();

        } else if (requestCode == REQUEST_CONTACT && data != null){
            String name = data.getStringExtra("victim");
            grudge.setGender(data.getStringExtra("gender"));

            grudge.setVictim(name);
            updateGrudge();
            if (name != null && name.length() > 0) {
                Log.d(TAG, "onActivityResult if: set name " + name);
                victimButton.setText(name);
            } else {
                Log.d(TAG, "onActivityResult else: set name " + name);
                victimButton.setText(R.string.grudge_choose_victim_button_text);
            }
        } else if (requestCode == REQUEST_PHOTO) {
            Uri uri = FileProvider.getUriForFile(getActivity(),
                    "com.shaary.android.grudgeintent.fileprovider",
                    photoFile);
            getActivity().revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            updateGrudge();
            updateGrudgeImage();
        } else if (requestCode == REQUEST_DELETE_IMAGE) {
            String delete = data.getStringExtra("delete");
            if (delete.equals("delete")) {
                photoFile.delete();
                updateGrudgeImage();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //Prevents soft keyboard from popping up when open the fragment
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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

    private void updateTime() {
        timeButton.setText(grudge.getTime());
    }

    private void updateGrudge() {
        GrudgePit.get(getActivity()).updateGrudge(grudge);
        callbacks.onGrudgeUpdated(grudge);
    }

    private void updateGrudgeImage() {
        if (photoFile == null || !photoFile.exists()) {
            grudgeImage.setImageDrawable(null);
        } else {
            Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getPath());
            grudgeImage.setImageBitmap(bitmap);
        }
    }

    private String getGrudgeReport() {
        String forgiven;
        if(grudge.isForgiven()) {
            forgiven = getString(R.string.grudge_forgiven_report);
        } else {
            forgiven = getString(R.string.grudge_not_forgiven_report);
        }

        String description = grudge.getDescription();
        if (description == null) {
            description = getString(R.string.grudge_description);
        }
        String date = grudge.getFormattedDate();
        String time = grudge.getTime();
        String name = grudge.getVictim();
        if (name == null) {
            name = getString(R.string.default_name);
        }

        return getString(R.string.grudge_message_format, name, description, date, time, forgiven);
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

            case R.id.grudge_info:
                InfoFragment infoFragment = new InfoFragment();
                infoFragment.show(getFragmentManager(), "info");
        }
        return super.onOptionsItemSelected(item);
    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Looks like it works but I have a buggy feeling
        if (grantResults.length > 0) {
            switch (requestCode) {
                case REQUEST_PHOTO_AND_STORAGE:
                    boolean showCameraRationale = shouldShowRequestPermissionRationale(permissions[0]);
                    boolean showStorageRationale = shouldShowRequestPermissionRationale(permissions[1]);
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
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
                    } else {
                        //Checks if one of the permissions was denied
                        if (!showCameraRationale && !showStorageRationale) {
                            Snackbar snackbar = Snackbar.make(getActivity()
                                            .findViewById(R.id.fragment_grudge_relative_layout),
                                    "One or both permission is not granted", Snackbar.LENGTH_LONG);
                            snackbar.setAction("Open settings", v1 -> openSettings());
                            snackbar.show();
                        } else {
                            ExplanationDialog dialog = ExplanationDialog.newInstance(new String[]{Manifest.permission.CAMERA,
                                    Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PHOTO_AND_STORAGE);
                            dialog.show(getFragmentManager(), "explanation");
                        }
                    }
                    break;
            }
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
