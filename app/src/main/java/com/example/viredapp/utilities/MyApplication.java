package com.example.viredapp.utilities;

import android.app.Application;
import android.content.Context;

import com.example.viredapp.BuildConfig;
import com.facebook.stetho.InspectorModulesProvider;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.inspector.protocol.ChromeDevtoolsDomain;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
import timber.log.Timber;

public class MyApplication extends Application {
    public OkHttpClient httpClient;
    private static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        final Context context = instance;
        Stetho.initialize(
                Stetho.newInitializerBuilder(context)
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(context))
                .build()
             );
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

    }

    public static Context getContext(){
        return instance.getApplicationContext();
    }
}
