package com.mahesh.arrk.di.component;

import com.mahesh.arrk.di.scopes.ActivityScope;
import com.mahesh.arrk.ui.DetailActivity;

import dagger.Component;

@Component(dependencies = ApplicationComponent.class)
@ActivityScope
public interface DetailActivityComponent {

    void inject(DetailActivity detailActivity);
}
