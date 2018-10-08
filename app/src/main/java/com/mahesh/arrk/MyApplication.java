package com.mahesh.arrk;

import android.app.Activity;
import android.app.Application;

import com.mahesh.arrk.di.component.ApplicationComponent;
import com.mahesh.arrk.di.component.DaggerApplicationComponent;
import com.mahesh.arrk.di.module.ContextModule;

public class MyApplication extends Application {

    ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder().contextModule(new ContextModule(this)).build();
        applicationComponent.injectApplication(this);

    }

    public static MyApplication get(Activity activity){
        return (MyApplication) activity.getApplication();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}

