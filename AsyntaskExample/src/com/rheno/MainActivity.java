package com.rheno;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button startBtn;

	private boolean isClicked = false;

	private Task task;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		startBtn = (Button) findViewById(R.id.startBtn);

		startBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (!isClicked) {
					task = new Task();
					task.execute("http://preview.europeana.eu/api/v2/search.json?wskey=5n3PY2cB6&query=Plano+del+campo+exterior+de+Melilla+&qf=Catalunya&start=1&rows=12&profile=portal");
					isClicked = true;
					System.out.println("RHENO start");
					startBtn.setText("STOP");
				} else {
					if (task != null) {
						task.cancel(true);
						isClicked = false;
						System.out.println("RHENO stop");
						startBtn.setText("START");
					}
				}
			}
		});

	}

	private class Task extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String json = "";

			for (int i = 0; i < params.length; i++) {

				try {
					HttpClient client = new DefaultHttpClient();
					HttpGet post = new HttpGet(params[i]);
					HttpResponse responseGET = client.execute(post);
					HttpEntity resEntity = responseGET.getEntity();

					if (resEntity != null) {
						json = EntityUtils.toString(resEntity);
					}

				} catch (Exception e) {
					Log.d("RESPONSE", "RESPONSE" + e.getMessage());
				}
			}

			return json;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null) {
				System.out.println("RHENO " + result);
				isClicked = false;
				startBtn.setText("START");
			}
		}

		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			super.onCancelled();
			System.out.println("RHENO Cancelled");
			isClicked = false;
			startBtn.setText("START");
		}

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (task != null) {
			task.cancel(true);
		}
	}

}
