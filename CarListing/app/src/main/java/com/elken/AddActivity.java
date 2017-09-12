package com.elken;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.elken.adapter.CarListAdapter;
import com.elken.constants.Constant;
import com.elken.helper.DatabaseHelper;
import com.elken.model.Car;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {


    private Spinner spinner;
    private EditText modelEditText;
    private EditText companyEditText;
    private Button button;

    private DatabaseHelper db;
    private boolean isInsert = true;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        spinner = (Spinner) findViewById(R.id.spinner_type);
        modelEditText = (EditText) findViewById(R.id.model_text);
        companyEditText = (EditText) findViewById(R.id.company_text);
        button = (Button) findViewById(R.id.add_button);


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Constant.CAR_TYPE);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);


        db = new DatabaseHelper(this);

        id = getIntent().getIntExtra("id",-1);

        if(id != -1){
            setEditText(id);
            isInsert = false;
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long result = 0;


                if(isInsert) {
                    result = db.insert(spinner.getSelectedItemPosition(), modelEditText.getText().toString(), companyEditText.getText().toString());
                }else{
                    result = db.update(id, spinner.getSelectedItemPosition(), modelEditText.getText().toString(), companyEditText.getText().toString());
                }

                if(result > 0  ){
                    Toast.makeText(AddActivity.this, "Save Data Success", Toast.LENGTH_LONG).show();
                    finish();
                }

            }
        });


    }

    private void setEditText(int id){
        db = new DatabaseHelper(this);
        Car car = db.getSingleCar(id);

        spinner.setSelection(car.getTypes());
        modelEditText.setText(car.getModel());
        companyEditText.setText(car.getCompany());


    }




    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }
}
