package com.rheno;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private Button btn;
	
	private Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			if (msg.arg1 == RESULT_OK && data != null) {

				String path = data.getString("absolutePath");
				Toast.makeText(MainActivity.this, "Downloaded" + path,
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(MainActivity.this, "Download failed.",
						Toast.LENGTH_LONG).show();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;
		intent = new Intent(this, DownloadService.class);
		// Create a new Messenger for the communication back
		Messenger messenger = new Messenger(handler);
		intent.putExtra("MESSENGER", messenger);
		intent.setData(Uri.parse("http://www.vogella.de/index.html"));
		intent.putExtra("urlpath", "http://www.vogella.de/index.html");
		startService(intent);
	}
	
	
}
