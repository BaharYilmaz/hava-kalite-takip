package com.example.deneme.ui.air_quality;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AirQualityViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AirQualityViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}