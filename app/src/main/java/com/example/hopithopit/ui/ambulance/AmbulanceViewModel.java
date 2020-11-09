package com.example.hopithopit.ui.ambulance;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AmbulanceViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AmbulanceViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is ambulance fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}