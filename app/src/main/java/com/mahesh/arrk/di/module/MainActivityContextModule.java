package com.mahesh.arrk.di.module;

import android.content.Context;

import com.mahesh.arrk.di.qualifier.ActivityContext;
import com.mahesh.arrk.di.scopes.ActivityScope;
import com.mahesh.arrk.ui.MainActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityContextModule {
    private MainActivity mainActivity;

    public Context context;

    public MainActivityContextModule(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        context = mainActivity;
    }

    @Provides
    @ActivityScope
    public MainActivity providesMainActivity() {
        return mainActivity;
    }

    @Provides
    @ActivityScope
    @ActivityContext
    public Context provideContext() {
        return context;
    }

}
