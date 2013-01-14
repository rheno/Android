package com.rheno.resthandling;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class DownloadTask extends AsyncTask<String, Void, String> {


	private String response;

	private MainActivity mainActivity;

	public DownloadTask(MainActivity mainActivity) {
		this.setMainActivity(mainActivity);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		Log.d("TEST", "TEST Pre Execute");
	}

	@Override
	protected String doInBackground(String... urls) {
		// TODO Auto-generated method stub
		Log.d("TEST", "TEST Background Process");

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userid", "unik_apps"));
		String responseJSON = HttpRest.getInstance().getJSONResponsePost(
				urls[0], params);

		return responseJSON;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		setResponse(result);
		Log.d("TEST", "TEST Rheno " + result);
		if (getResponse().equals("")) {
			Log.d("TEST", "TEST Kosong");
		} else {
			Log.d("TEST", "TEST Result :" + getResponse());
			// Parse JSON

			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(getResponse());
				getMainActivity().getTextView().setText(
						jsonObject.getString("message"));
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public MainActivity getMainActivity() {
		return mainActivity;
	}

	public void setMainActivity(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}

}
