package com.example.androidintentservice;

import android.app.IntentService;
import android.content.Intent;

public class MyIntentService extends IntentService {
	
	public static final String ACTION_MyIntentService = "com.example.androidintentservice.RESPONSE";
	public static final String EXTRA_KEY_IN = "EXTRA_IN";
	public static final String EXTRA_KEY_OUT = "EXTRA_OUT";
	MyParcelable parcelableIn;
	String extraOut;

	public MyIntentService() {
		super("com.example.androidintentservice.MyIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		//get input
		parcelableIn = intent.getParcelableExtra(EXTRA_KEY_IN);
		extraOut = "Passed as Parcelable:\n" 
				+ parcelableIn.blogName + "\n" 
				+ parcelableIn.blogAddress;
		
		//dummy delay for 5 sec
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	//wait 3 sec
		
		//return result
		Intent intentResponse = new Intent();
		intentResponse.setAction(ACTION_MyIntentService);
		intentResponse.addCategory(Intent.CATEGORY_DEFAULT);
		intentResponse.putExtra(EXTRA_KEY_OUT, extraOut);
		sendBroadcast(intentResponse);
	}

}
