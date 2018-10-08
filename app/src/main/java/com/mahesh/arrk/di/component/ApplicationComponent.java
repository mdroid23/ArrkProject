package com.mahesh.arrk.di.component;

import android.content.Context;

import com.mahesh.arrk.MyApplication;
import com.mahesh.arrk.di.module.ContextModule;
import com.mahesh.arrk.di.module.RetrofitModule;
import com.mahesh.arrk.di.qualifier.ApplicationContext;
import com.mahesh.arrk.di.scopes.ApplicationScope;
import com.mahesh.arrk.retrofit.APIInterface;

import dagger.Component;

@ApplicationScope
@Component(modules = {ContextModule.class, RetrofitModule.class})
public interface ApplicationComponent {

    public APIInterface getApiInterface();

    @ApplicationContext
    public Context getContext();

    public void injectApplication(MyApplication myApplication);
}
