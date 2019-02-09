package com.shaary.neverforget.view;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.shaary.neverforget.model.Grudge;
import com.shaary.neverforget.model.GrudgePit;

import java.util.List;

public class GrudgeViewModel extends AndroidViewModel {

    private GrudgePit repository;
    private LiveData<List<Grudge>> grudges;

    public GrudgeViewModel(@NonNull Application application) {
        super(application);
        repository = GrudgePit.getInstance(application);
        grudges = repository.getGrudgeLiveList();
    }

    public LiveData<List<Grudge>> getGrudges() {
        return grudges;
    }

    public void insert(Grudge grudge) {
        repository.addGrudge(grudge);
    }

    public void delete(Grudge grudge) {
        repository.deleteGrudge(grudge);
    }
}
