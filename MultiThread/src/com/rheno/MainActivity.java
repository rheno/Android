package com.rheno;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {

	private Button btn;
	
	private EditText editText1;
	
	private static final int WHAT_MSG = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btn = (Button) findViewById(R.id.button1);
		editText1 = (EditText) findViewById(R.id.editText1);
		btn.setOnClickListener(this);
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button1:

			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Message msg = new Message();
					msg.what = WHAT_MSG;
					msg.obj = getJSONString("http://preview.europeana.eu/api/v2/search.json?wskey=5n3PY2cB6&query=Plano+del+campo+exterior+de+Melilla+&qf=Catalunya&start=1&rows=12&profile=portal");
					handler.sendMessage(msg);
				}
			}).start();

			break;

		default:
			break;
		}
	}

	private String getJSONString(String url) {
		String json = "";
		try {
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 10000); // Connection
																				// timeout
			HttpConnectionParams.setSoTimeout(httpParameters, 9000); // Socket
																		// timeout

			HttpClient client = new DefaultHttpClient(httpParameters);
			HttpGet get = new HttpGet(url);
			HttpResponse responseGET = client.execute(get);
			HttpEntity resEntity = responseGET.getEntity();

			if (resEntity != null) {
				json = EntityUtils.toString(resEntity);
			}

		} catch (Exception e) {
			json = e.getMessage();
		}
		return json;
	}
	
	private Handler handler = new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case WHAT_MSG:
				String json = (String)msg.obj; 
				editText1.setText(json);
				break;

			default:
				break;
			}
		};
	};

}
