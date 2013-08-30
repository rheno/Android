package com.rheno.androidservice2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	private Button startBtn;

	private Button stopBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		startBtn = (Button) findViewById(R.id.button1);
		stopBtn = (Button) findViewById(R.id.button2);
		
		startBtn.setOnClickListener(this);
		stopBtn.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button1:
			startMyService();
			break;
		case R.id.button2:
			stopMyService();
			break;
		default:
			break;
		}
	}

	// Method to start the service
	public void startMyService() {
		startService(new Intent(getBaseContext(), MyService.class));
	}

	// Method to stop the service
	public void stopMyService() {
		stopService(new Intent(getBaseContext(), MyService.class));
	}

}
