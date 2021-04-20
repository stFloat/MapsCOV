package com.dongwanghan.mapscov;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

public class InitApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(getApplicationContext());

    }
}
