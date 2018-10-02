package com.shaary.neverforget.model;

import android.content.Context;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GrudgePit {

    private static GrudgePit sGrudgePit;

    private List<Grudge> grudges;

    public static GrudgePit get(Context context) {
        if (sGrudgePit == null) {
            sGrudgePit = new GrudgePit(context);
        }
        return sGrudgePit;
    }

    private GrudgePit(Context context) {
        grudges = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Grudge grudge = new Grudge();
            grudge.setTitle("Grudge # " + i);
            grudge.setRemind(i % 2 == 0);
            grudge.setRevenge(i % 2 == 0);
            grudges.add(grudge);
        }
    }

    public List<Grudge> getGrudges() {
        return grudges;
    }

    public Grudge getGrudge(UUID id) {
        //TODO: Improve the code
        for (Grudge grudge : grudges) {
            if (grudge.getId().equals(id)) {
                return grudge;
            }
        }
        return null;
    }
}
