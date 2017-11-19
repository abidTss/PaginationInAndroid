package com.abid.paginationinandroid.di.component;

import android.app.Application;
import android.content.Context;

import com.abid.paginationinandroid.DemoApplication;
import com.abid.paginationinandroid.data.DataManager;
import com.abid.paginationinandroid.data.DbHelper;
import com.abid.paginationinandroid.data.SharedPrefsHelper;
import com.abid.paginationinandroid.di.ApplicationContext;
import com.abid.paginationinandroid.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by abid on 11/18/17.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(DemoApplication demoApplication);

    @ApplicationContext
    Context getContext();

    Application getApplication();

    DataManager getDataManager();

    SharedPrefsHelper getPreferenceHelper();

    DbHelper getDbHelper();
}
