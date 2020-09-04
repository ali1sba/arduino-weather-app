package com.example.android.esiweather.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<String> mText2;

    public HomeViewModel() {
        Date currentTime = Calendar.getInstance().getTime();
        String convertedDate = DateFormat.getDateInstance(DateFormat.FULL).format(currentTime);

       // Log.d("myLog", convertedTime);
        mText = new MutableLiveData<>();
        mText.setValue(convertedDate);

    }

    public LiveData<String> getText() {

        return mText;

    }
}