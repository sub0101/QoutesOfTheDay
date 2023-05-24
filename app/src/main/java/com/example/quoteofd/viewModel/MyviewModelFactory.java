package com.example.quoteofd.viewModel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MyviewModelFactory extends ViewModelProvider.NewInstanceFactory {

    Application application;

    public MyviewModelFactory(Application application ) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MviewModel( application);
    }
}
