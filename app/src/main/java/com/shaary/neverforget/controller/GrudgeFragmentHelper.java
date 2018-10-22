package com.shaary.neverforget.controller;

import com.shaary.neverforget.R;
import com.shaary.neverforget.model.Grudge;

public class GrudgeFragmentHelper {

    public static int getLayoutId(Grudge grudge) {
        int imagePath = R.drawable.grudge_gradient;

        if (grudge.isForgiven()) {
            imagePath = R.drawable.forgiven_grudge_gradient;
        } else if (grudge.isRevenged()) {
            imagePath = R.drawable.grudge_gradient;
        } else if (grudge.isRevenge()) {
            imagePath = R.drawable.serous_grudge_gradient;
        }

        return imagePath;
    }
}
