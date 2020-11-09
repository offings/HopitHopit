package com.example.hopithopit.ui.acceptable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AcceptableViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AcceptableViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is acceptable fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}