package com.example.myapplication.ui.notifications;

import android.annotation.SuppressLint;

import androidx.appcompat.widget.ThemedSpinnerAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.LoginPage;
import com.example.myapplication.ProfilePage;
import com.example.myapplication.ui.HelperClass;

@SuppressLint("StaticFieldLeak")
public class NotificationsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    private final LoginPage user_data = new LoginPage();


    public NotificationsViewModel() {
        String userName = user_data.getUserName();
        mText = new MutableLiveData<>();
        mText.setValue(userName);
    }

    public LiveData<String> getText() {
        return mText;
    }
}
