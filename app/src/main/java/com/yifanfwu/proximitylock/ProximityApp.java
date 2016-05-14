package com.yifanfwu.proximitylock;

import android.app.Application;
import android.content.Context;

public class ProximityApp extends Application {

	private static Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		ProximityApp.context = getApplicationContext();
	}

	public static Context getAppContext() {
		return ProximityApp.context;
	}
}
