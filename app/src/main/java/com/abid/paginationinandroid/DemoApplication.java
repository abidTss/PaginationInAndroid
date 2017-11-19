package com.abid.paginationinandroid;

import android.app.Application;
import android.content.Context;

import com.abid.paginationinandroid.data.DataManager;
import com.abid.paginationinandroid.di.component.ApplicationComponent;
import com.abid.paginationinandroid.di.component.DaggerApplicationComponent;
import com.abid.paginationinandroid.di.module.ApplicationModule;

import javax.inject.Inject;

/**
 * Created by abid on 11/18/17.
 */

public class DemoApplication extends Application {
    protected ApplicationComponent applicationComponent;

    @Inject
    DataManager dataManager;

    public static DemoApplication get(Context context) {
        return (DemoApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        applicationComponent.inject(this);
    }

    public ApplicationComponent getComponent(){
        return applicationComponent;
    }
}
