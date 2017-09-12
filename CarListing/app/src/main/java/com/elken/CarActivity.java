package com.elken;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.elken.adapter.CarListAdapter;

import com.elken.helper.DatabaseHelper;
import com.elken.model.Car;

import java.util.ArrayList;
import java.util.List;


public class CarActivity extends AppCompatActivity {

    private List<Car> carList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CarListAdapter adapter;
    private FloatingActionButton floatingActionButton;

    private DatabaseHelper db;

    private List<Car> cars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = (RecyclerView) findViewById(R.id.car_list_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());



        floatingActionButton = (FloatingActionButton) findViewById(R.id.add_button);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(CarActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.suv:
                setCarByFilter(0);
                return true;
            case R.id.mpv:
                setCarByFilter(1);
                return true;
            case R.id.hatchback:
                setCarByFilter(2);
                return true;
            case R.id.crossover:
                setCarByFilter(3);
                return true;
            case R.id.coupe:
                setCarByFilter(4);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setCarListData(){

        db = new DatabaseHelper(this);
        cars = db.readAll();

        if(cars.size() > 0) {
            adapter = new CarListAdapter(this, cars);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }else {
            adapter = null;
        }

    }

    private void setCarByFilter(int pos){

        db = new DatabaseHelper(this);
        cars = db.getFilterCarBy(pos);


        adapter = new CarListAdapter(this, cars);
        recyclerView.setAdapter(adapter);


        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onStart() {
        setCarListData();

        super.onStart();
    }

    @Override
    protected void onStop() {
        db.close();
        super.onStop();
    }


}
