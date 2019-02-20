package com.shaary.neverforget.viewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.shaary.neverforget.BR;
import com.shaary.neverforget.model.Grudge;
import com.shaary.neverforget.model.GrudgePit;

public class BasicFragmentVM extends BaseObservable {

    private GrudgePit repository;
    private Grudge grudge;
    private Context context;

    public final ObservableField<String> description = new ObservableField<>();
    public final ObservableField<String> title = new ObservableField<>();

    public BasicFragmentVM(@NonNull Context context, long id) {
        repository = GrudgePit.getInstance(context);
        grudge = repository.getGrudge(id);
        context = context.getApplicationContext();
    }

    @Bindable
    public String getDescription() {
        return grudge.getDescription();
    }

    public void setDescription(String description) {
        this.description.set(description);
        grudge.setDescription(description);
        repository.updateGrudge(grudge);
    }

    @Bindable
    public String getTitle() {
        return grudge.getTitle();
    }

    public void setTitle(String title) {
        this.title.set(title);
        grudge.setTitle(title);
        repository.updateGrudge(grudge);
    }

    @Bindable
    public String getName() {
        String name = "";
        if (grudge.getVictim() != null) {
            if (!grudge.getVictim().isEmpty()) {
                name = grudge.getVictim();
            }
        } else {
            name = "OFFENDER'S NAME";
        }
        return name;
    }

    public void setName(String name) {
        grudge.setVictim(name);
        repository.updateGrudge(grudge);
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getTime() {
        return grudge.getTime();
    }

    public void setTime(String time) {
        grudge.setTime(time);
        repository.updateGrudge(grudge);
        notifyPropertyChanged(BR.time);
    }

    @Bindable
    public boolean getAction1() {
        return grudge.isRevenge();
    }

    public void setAction1(boolean isAction1) {
        grudge.setRevenge(isAction1);
        repository.updateGrudge(grudge);
        notifyPropertyChanged(BR.action1);
        notifyPropertyChanged(BR.action1CompleteEnabled);
    }

    @Bindable
    public boolean getAction2() {
        return grudge.isForgiven();
    }

    public void setAction2(boolean isAction2) {
        grudge.setForgiven(isAction2);
        repository.updateGrudge(grudge);
        notifyPropertyChanged(BR.action1);
        notifyPropertyChanged(BR.action1CompleteEnabled);
    }

    @Bindable
    public boolean getAction1Completed() {
        return grudge.isRevenged();
    }

    public void setAction1Completed(boolean isCompleted) {
        grudge.setRevenged(isCompleted);
        repository.updateGrudge(grudge);
        notifyPropertyChanged(BR.action1);
    }

    @Bindable
    public boolean getAction1CompleteEnabled() {
        if (grudge.isForgiven()) {
            return false;
        } else if (grudge.isRevenge()) {
            return true;
        }
        return false;
    }

    //TODO: bind date
    //TODO: set date onclick
    //TODO: set time onclick
    //TODO: set name onclick
    //TODO: set picture onclick
    //TODO: set background

}
