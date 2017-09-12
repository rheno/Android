package com.elken.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elken.AddActivity;
import com.elken.R;
import com.elken.model.Car;

import java.util.List;


/**
 * Created by rhenobudiasa on 9/12/17.
 */


public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.CarViewHolder>  {

    private List<Car> carList;
    private Context context;

    public class CarViewHolder extends RecyclerView.ViewHolder {

        public TextView model;
        public TextView id;


        public CarViewHolder(View view) {
            super(view);

            id = (TextView) view.findViewById(R.id.id_text);
            model= (TextView) view.findViewById(R.id.model_text);



            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AddActivity.class);
                    intent.putExtra("id", Integer.valueOf(id.getText().toString()));
                    context.startActivity(intent);
                }
            });


        }
    }

    public CarListAdapter(Context context, List<Car> carList){
            this.context = context;
            this.carList = carList;
    }

    @Override
    public CarListAdapter.CarViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.car_list_row, parent, false);



        return new CarViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CarListAdapter.CarViewHolder holder, int position) {
            Car car = carList.get(position);


            holder.model.setText(car.getModel());
            holder.id.setText(String.valueOf(car.getId()));



    }

    @Override
    public int getItemCount() {
        return carList.size();
    }
}
