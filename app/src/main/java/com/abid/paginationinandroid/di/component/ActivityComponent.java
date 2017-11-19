package com.abid.paginationinandroid.di.component;

import com.abid.paginationinandroid.MainActivity2;
import com.abid.paginationinandroid.di.PerActivity;
import com.abid.paginationinandroid.di.module.ActivityModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by abid on 11/18/17.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class,modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity2 mainActivity);
}
