package com.rheno.androiddb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends Activity implements OnClickListener{

	private EditText editText;
	private Button saveBtn;
	private Bundle bundle;
	private long id;
	private String content;
	DBWrapper dbWrapper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_edit);
	    editText = (EditText) findViewById(R.id.editText1);
	    saveBtn = (Button) findViewById(R.id.button1);
	    saveBtn.setOnClickListener(this);
	    bundle = getIntent().getExtras();
	    id=bundle.getLong("id");
	    content = bundle.getString("content");
	    dbWrapper = new DBWrapper(this);
	    editText.setText(content);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button1: 
          
			
			dbWrapper.updateData(id, editText.getText().toString());
			System.out.println("RHENO = "+id);
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			finish();
			break;
		default:
			break;
		}
	}

}
