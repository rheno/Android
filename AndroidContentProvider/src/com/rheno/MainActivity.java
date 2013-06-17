package com.rheno;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;

import android.widget.Toast;

public class MainActivity extends Activity {

	private CountriesDBAdapter dbHelper;
	private SimpleCursorAdapter dataAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		dbHelper = new CountriesDBAdapter(this);
		dbHelper.open();

		// Clean all data
		dbHelper.deleteAllCountries();
		// Add some data
		dbHelper.insertSomeCountries();

		// Generate ListView from SQLite Database
		displayListView();
	}

	private void displayListView() {

		Cursor cursor = dbHelper.fetchAllCountries();

		// The desired columns to be bound
		String[] columns = new String[] { CountriesDBAdapter.KEY_CODE,
				CountriesDBAdapter.KEY_NAME, CountriesDBAdapter.KEY_CONTINENT,
				CountriesDBAdapter.KEY_REGION };

		// the XML defined views which the data will be bound to
		int[] to = new int[] { R.id.code, R.id.name, R.id.continent,
				R.id.region, };

		// create the adapter using the cursor pointing to the desired data
		// as well as the layout information
		dataAdapter = new SimpleCursorAdapter(this, R.layout.country_info,
				cursor, columns, to, 0);

		ListView listView = (ListView) findViewById(R.id.listView1);
		// Assign adapter to ListView
		listView.setAdapter(dataAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> listView, View view,
					int position, long id) {
				// Get the cursor, positioned to the corresponding row in the
				// result set
				Cursor cursor = (Cursor) listView.getItemAtPosition(position);

				// Get the state's capital from this row in the database.
				String countryCode = cursor.getString(cursor
						.getColumnIndexOrThrow("code"));
				Toast.makeText(getApplicationContext(), countryCode,
						Toast.LENGTH_SHORT).show();

			}
		});

		EditText myFilter = (EditText) findViewById(R.id.myFilter);
		myFilter.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable s) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				dataAdapter.getFilter().filter(s.toString());
			}
		});

		dataAdapter.setFilterQueryProvider(new FilterQueryProvider() {
			public Cursor runQuery(CharSequence constraint) {
				return dbHelper.fetchCountriesByName(constraint.toString());
			}
		});

	}

}
