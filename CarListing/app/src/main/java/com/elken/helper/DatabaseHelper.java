package com.elken.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.elken.model.Car;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rhenobudiasa on 9/12/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper{



    public static final String TABLE_NAME = "car";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_MODEL = "model";
    public static final String COLUMN_COMPANY = "company";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_TYPE + " INTEGER," +
                    COLUMN_MODEL + " TEXT," +
                    COLUMN_COMPANY + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "CarList.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public long insert(int types, String model, String company){

        ContentValues values = new ContentValues();
        values.put(COLUMN_TYPE, types);
        values.put(COLUMN_MODEL, model);
        values.put(COLUMN_COMPANY, company);


        return getWritableDatabase().insert(TABLE_NAME, null, values);
    }

    public int update(int id, int types, String model, String company){

        ContentValues values = new ContentValues();
        values.put(COLUMN_TYPE, types);
        values.put(COLUMN_MODEL, model);
        values.put(COLUMN_COMPANY, company);


        String[] arg = {String.valueOf(id)};
        return getWritableDatabase().update(TABLE_NAME, values, "id = ?", arg);
    }

    public List<Car> readAll(){

        String countQuery = "SELECT  * FROM " + TABLE_NAME;

        Cursor cursor = getReadableDatabase().rawQuery(countQuery, null);

        List<Car> carList = new ArrayList<>();
        Car car;
        while(cursor.moveToNext()) {

            car = new Car();
            car.setId(cursor.getInt(0));
            car.setTypes(cursor.getInt(1));
            car.setModel(cursor.getString(2));
            car.setCompany(cursor.getString(3));

            carList.add(car);

        }

        cursor.close();

        return carList;
    }

    public Car getSingleCar(int id){
        String countQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE id = " + id;

        Cursor cursor = getReadableDatabase().rawQuery(countQuery, null);

        if(cursor == null){
            return null;
        }

        cursor.moveToFirst();

        Car car = new Car();
        car.setId(cursor.getInt(0));
        car.setTypes(cursor.getInt(1));
        car.setModel(cursor.getString(2));
        car.setCompany(cursor.getString(3));

        return car;
    }

    public List<Car> getFilterCarBy(int pos){
        String countQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE type = " + pos;

        Cursor cursor = getReadableDatabase().rawQuery(countQuery, null);

        List<Car> carList = new ArrayList<>();
        Car car;
        while(cursor.moveToNext()) {

            car = new Car();
            car.setId(cursor.getInt(0));
            car.setTypes(cursor.getInt(1));
            car.setModel(cursor.getString(2));
            car.setCompany(cursor.getString(3));

            carList.add(car);

        }

        cursor.close();

        return carList;
    }


}
