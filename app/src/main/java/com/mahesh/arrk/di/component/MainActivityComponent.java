package com.mahesh.arrk.di.component;

import android.content.Context;

import com.mahesh.arrk.di.module.AdapterModule;
import com.mahesh.arrk.di.qualifier.ActivityContext;
import com.mahesh.arrk.di.scopes.ActivityScope;
import com.mahesh.arrk.ui.MainActivity;

import dagger.Component;


@ActivityScope
@Component(modules = AdapterModule.class, dependencies = ApplicationComponent.class)
public interface MainActivityComponent {

    @ActivityContext
    Context getContext();


    void injectMainActivity(MainActivity mainActivity);
}
