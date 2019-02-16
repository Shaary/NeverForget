package com.shaary.neverforget.viewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;

import com.shaary.neverforget.BR;
import com.shaary.neverforget.model.Grudge;
import com.shaary.neverforget.model.GrudgePit;

public class BasicFragmentVM extends BaseObservable {
    //TODO: use two way binding for edit text
    GrudgePit grudgePit;
    private Grudge grudge;

    public BasicFragmentVM(@NonNull Context context, long id) {
        grudgePit = GrudgePit.getInstance(context);
        grudge = grudgePit.getGrudge(id);
    }

    @Bindable
    public String getDescription() {
        return grudge.getDescription();
    }

    public void setDescription(String description) {
        grudge.setDescription(description);
        grudgePit.updateGrudge(grudge);
        notifyPropertyChanged(BR.description);
    }

    @Bindable
    public String getTitle() {
        return grudge.getTitle();
    }

    public void setTitle(String title) {
        grudge.setTitle(title);
        grudgePit.updateGrudge(grudge);
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getName() {
        return grudge.getVictim();
    }

    public void setName(String name) {
        grudge.setVictim(name);
        grudgePit.updateGrudge(grudge);
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getTime() {
        return grudge.getTime();
    }

    public void setTime(String time) {
        grudge.setTime(time);
        grudgePit.updateGrudge(grudge);
        notifyPropertyChanged(BR.time);
    }

//    @Bindable
//    public String getFormattedDate() {
//        return grudge.getFormattedDate();
//    }
//
//    public void getDate(Date date) {
//        grudge.setDate(date);
//        grudgePit.updateGrudge(grudge);
//        notifyPropertyChanged(BR.date);
//    }
}
