package com.rheno;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	private Button btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btn = (Button) findViewById(R.id.buttonCapture);

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
			
				/* Take screenshot to get bitmap image */
				Bitmap bitmap = takeScreenshot();
				
				/* Save in the storage */
				saveBitmap(bitmap);
			}
		});
	}
	
	/**
	 * Method for take screenshot and save in memory as bitmap
	 * 
	 * @author rheno.b
	 * 
	 */

	public Bitmap takeScreenshot() {
		
		/* capture whole xml layout */
		// View rootView = findViewById(android.R.id.content).getRootView();
		
		/* capture only the relative layout (also the child) */
	    RelativeLayout rootView = (RelativeLayout) findViewById(R.id.relativeLayoutParent);
	
		
		/* Enabling drawing cache */
		rootView.setDrawingCacheEnabled(true);
		
		return rootView.getDrawingCache();
	}

	
	/**
	 * Method for save bitmap to storage
	 * 
	 * @author rheno.b
	 * 
	 */
	public void saveBitmap(Bitmap bitmap) {
		File imagePath = new File(Environment.getExternalStorageDirectory()
				+ "/Capture/screenshot.png");
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(imagePath);
			bitmap.compress(CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			Log.e("RHENO", e.getMessage(), e);
		} catch (IOException e) {
			Log.e("RHENO", e.getMessage(), e);
		}
	}

}
