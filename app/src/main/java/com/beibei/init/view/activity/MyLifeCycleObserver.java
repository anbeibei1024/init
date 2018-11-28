package com.beibei.init.view.activity;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

/**
 * project_name:   init
 * package_name:   com.beibei.init.view.activity
 * author:   beibei
 * create_time:    2018/9/19 17:15
 * class_desc:
 * remarks:
 */
public class MyLifeCycleObserver implements LifecycleObserver {

    private static final String TAG="MyLifeCycleObserver";

    public MyLifeCycleObserver() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onActivityResume(){
        Log.d(TAG,"onActivityResume");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onActivityPause(){
        Log.d(TAG,"onActivityPause");
    }
}