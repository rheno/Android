package com.example.androidintentservice;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView textResult;
	
	private MyBroadcastReceiver myBroadcastReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textResult = (TextView)findViewById(R.id.result);

		//prepare MyParcelable passing to intentMyIntentService
		MyParcelable myParcelable = new MyParcelable();
		myParcelable.blogName = "Android-er";
		myParcelable.blogAddress = "http://android-er.blogspot.com/";
		
		//Start MyIntentService
		Intent intentMyIntentService = new Intent(this, MyIntentService.class);
		intentMyIntentService.putExtra(MyIntentService.EXTRA_KEY_IN, myParcelable);
		startService(intentMyIntentService);
		
		myBroadcastReceiver = new MyBroadcastReceiver();
		
		//register BroadcastReceiver
		IntentFilter intentFilter = new IntentFilter(MyIntentService.ACTION_MyIntentService);
		intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
		registerReceiver(myBroadcastReceiver, intentFilter);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//un-register BroadcastReceiver
		unregisterReceiver(myBroadcastReceiver);
	}

	public class MyBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String result = intent.getStringExtra(MyIntentService.EXTRA_KEY_OUT);
			textResult.setText(result);
		}

	}

}
