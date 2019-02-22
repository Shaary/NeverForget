package com.shaary.neverforget.viewModel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;

import com.shaary.neverforget.BR;
import com.shaary.neverforget.controller.BasicFragment;
import com.shaary.neverforget.controller.DatePickerFragment;
import com.shaary.neverforget.controller.TimePickerFragment;
import com.shaary.neverforget.model.Grudge;
import com.shaary.neverforget.model.GrudgePit;

import java.util.Date;

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
        if (grudge.getName() == null || grudge.getName().isEmpty()) {
            return "OFFENDER'S NAME";
        }
        return grudge.getName();
    }

    public void setName(String name) {
        grudge.setName(name);
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
    public String getDate() {
        return grudge.getFormattedDate();
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

    public void handleActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == BasicFragment.REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            grudge.setDate(date);
            repository.updateGrudge(grudge);
            notifyPropertyChanged(BR.date);
        } else if (requestCode == BasicFragment.REQUEST_TIME) {
            int hour = (int) data.getSerializableExtra(TimePickerFragment.EXTRA_HOUR);
            int minute = (int) data.getSerializableExtra(TimePickerFragment.EXTRA_MINUTE);
            grudge.setTime(hour, minute);
            repository.updateGrudge(grudge);
            notifyPropertyChanged(BR.time);
        } else if (requestCode == BasicFragment.REQUEST_CONTACT && data != null){
            String name = data.getStringExtra("victim");
            grudge.setGender(data.getStringExtra("gender"));
            grudge.setName(name);
            repository.updateGrudge(grudge);
            notifyPropertyChanged(BR.name);
        } else if (requestCode == BasicFragment.REQUEST_PHOTO) {
            Uri uri = FileProvider.getUriForFile(context,
                    "com.shaary.android.grudgeintent.fileprovider",
                    repository.getPhotoFile(grudge));
            context.revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            repository.updateGrudge(grudge);
            //updateGrudgeImage();
        } else if (requestCode == BasicFragment.REQUEST_DELETE_IMAGE) {
            String delete = data.getStringExtra("delete");
            if (delete.equals("delete")) {
//                photoFile.delete();
//                updateGrudgeImage();
            }
        }
    }

    //TODO: move onclick to view model
    //TODO: set picture onclick
    //TODO: set background

}
