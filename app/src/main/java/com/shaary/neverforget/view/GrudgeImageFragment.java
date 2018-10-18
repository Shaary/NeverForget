package com.shaary.neverforget.view;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shaary.neverforget.R;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class GrudgeImageFragment extends DialogFragment {

    private File photoFile;
    @BindView(R.id.grudge_image) ImageView grudgeImage;
    @BindView(R.id.close_image) ImageView close;

    public static GrudgeImageFragment newInstance(File photoFile) {

        Bundle args = new Bundle();
        args.putSerializable("photoFile", photoFile);
        
        GrudgeImageFragment fragment = new GrudgeImageFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        photoFile = (File) getArguments().getSerializable("photoFile");

        View view = inflater.inflate(R.layout.fragment_grudge_image, container, false);
        ButterKnife.bind(this, view);

        if (photoFile == null || !photoFile.exists()) {
            grudgeImage = null;
        } else {
            Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getPath());
            grudgeImage.setImageBitmap(bitmap);
        }

        close.setOnClickListener(v -> getDialog().dismiss());

        return view;
    }

}
