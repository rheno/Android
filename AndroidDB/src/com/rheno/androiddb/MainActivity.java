package com.rheno.androiddb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity implements OnClickListener {

	private ListView listData;
	private Button addBtn;
	private EditText inputText;
	ArrayAdapter<String> arrDataModel = null;

	private DBWrapper dbWrapper;

	private boolean isEdit = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		dbWrapper = new DBWrapper(this);

		inputText = (EditText) findViewById(R.id.input_text);
		listData = (ListView) findViewById(R.id.listData);
		addBtn = (Button) findViewById(R.id.add_btn);

		addBtn.setOnClickListener(this);

		arrDataModel = new ArrayAdapter<String>(this, R.layout.listview_item,
				R.id.textView1);
		for (int i = 0; i < dbWrapper.queryAllData().size(); i++) {

			arrDataModel.add(dbWrapper.queryAllData().get(i).getContent());
		}

		listData.setAdapter(arrDataModel);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.add_btn:
			dbWrapper.insertData(inputText.getText().toString());
			// listData.setAdapter(arrDataModel);
			int position = dbWrapper.queryAllData().size();
			arrDataModel.add(dbWrapper.queryAllData().get(position - 1)
					.getContent());
			arrDataModel.notifyDataSetChanged();
			inputText.setText("");
			break;
		default:
			break;
		}
	}

	public void onEdit(View v) {
		int position = listData.getPositionForView(v);
		Intent intent = new Intent(this, EditActivity.class);
		intent.putExtra("id", dbWrapper.queryAllData().get(position).getId());
		intent.putExtra("content", dbWrapper.queryAllData().get(position).getContent());
		startActivity(intent);
		finish();

	}

	public void onDelete(View v) {
		int position = listData.getPositionForView(v);
		arrDataModel
				.remove(dbWrapper.queryAllData().get(position).getContent());
		dbWrapper.deleteData(dbWrapper.queryAllData().get(position).getId());
		arrDataModel.notifyDataSetChanged();
		inputText.setText("");
	}

}
