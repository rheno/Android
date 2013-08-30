package com.example.androidintentservice;

import android.os.Parcel;
import android.os.Parcelable;

public class MyParcelable implements Parcelable {
	
	public String blogName;
	public String blogAddress;
	
	public static final Parcelable.Creator<MyParcelable> CREATOR =
			new Parcelable.Creator<MyParcelable>(){

				@Override
				public MyParcelable createFromParcel(Parcel source) {
					return new MyParcelable(source);
				}

				@Override
				public MyParcelable[] newArray(int size) {
					return new MyParcelable[size];
				}
	};
	
	public MyParcelable(){
	}
	
	public MyParcelable(Parcel source){
		readFromParcel(source);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(blogName);
		dest.writeString(blogAddress);
	}
	
	public void readFromParcel(Parcel source){
		blogName = source.readString();
		blogAddress = source.readString();
	}

}
