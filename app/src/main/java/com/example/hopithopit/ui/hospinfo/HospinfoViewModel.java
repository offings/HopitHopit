package com.example.hopithopit.ui.hospinfo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HospinfoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HospinfoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is hospinfo fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}