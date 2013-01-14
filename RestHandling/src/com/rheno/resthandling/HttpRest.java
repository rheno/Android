package com.rheno.resthandling;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class HttpRest {

	public static final HttpRest insttance = new HttpRest();
	public HttpRest(){

	}

	public static HttpRest getInstance()
	{
		return insttance;
	}

	public void getResponsePost(final List<NameValuePair> params,String url)
	{
		try {

			HttpClient client = new DefaultHttpClient();  
			String postURL = url;
			HttpPost post = new HttpPost(postURL); 
			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
			post.setEntity(ent);
			HttpResponse responsePOST = client.execute(post);  
			HttpEntity resEntity = responsePOST.getEntity();
			synchronized (resEntity) {
				if (resEntity != null) {					
					Log.i("RESPONSE","RESPONSE = "+EntityUtils.toString(resEntity));
				}
			}

		} catch (Exception e) {
			Log.d("RESPONSE", "RESPONSE"+e.getMessage());
		}
	}

	public static void getResponseGet(String urlString)
	{
		try {	
			HttpClient client = new DefaultHttpClient();  
			HttpGet post = new HttpGet(urlString);
			HttpResponse responseGET = client.execute(post);  
			HttpEntity resEntity = responseGET.getEntity();

			if (resEntity != null) {
				Log.d("RESPONSE", "RESPONSE"+EntityUtils.toString(resEntity));
			}

		} catch (Exception e) {
			Log.d("RESPONSE", "RESPONSE"+e.getMessage());
		}
	}

	public String getJSONResponsePost(String postURL,final List<NameValuePair> params)
	{
		final String json = "";
		try {	
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 3000); // Connection timeout 
			HttpConnectionParams.setSoTimeout(httpParameters, 2000); // Socket timeout

			HttpClient client = new DefaultHttpClient(httpParameters);  
			HttpPost post = new HttpPost(postURL); 
			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
			post.setEntity(ent);
			HttpResponse responsePOST = client.execute(post);  
			HttpEntity resEntity = responsePOST.getEntity();

			if (resEntity != null) {
				return EntityUtils.toString(resEntity);
			}

		}catch (Exception e) {
			Log.d("RESPONSE", "RESPONSE ERROR"+e.getMessage());
		}
		return json;
	}

	public String getJSONResponseGet(String urlString)
	{
		final String json = "";
		try {	
			HttpClient client = new DefaultHttpClient();  
			HttpGet post = new HttpGet(urlString); 
			HttpResponse responseGET = client.execute(post);  
			HttpEntity resEntity = responseGET.getEntity();

			if (resEntity != null) {
				return EntityUtils.toString(resEntity);
			}

		} catch (Exception e) {
			Log.d("RESPONSE", "RESPONSE"+e.getMessage());
		}
		return json;
	}
}
