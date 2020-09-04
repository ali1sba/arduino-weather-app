package com.example.android.esiweather.ui.values;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ValuesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ValuesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is values fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}