package com.abid.paginationinandroid.di.module;

import android.app.Activity;
import android.content.Context;

import com.abid.paginationinandroid.di.ActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by abid on 11/18/17.
 */
@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }
}
