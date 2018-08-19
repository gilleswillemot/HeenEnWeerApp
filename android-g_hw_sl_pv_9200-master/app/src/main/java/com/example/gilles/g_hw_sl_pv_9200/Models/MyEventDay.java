package com.example.gilles.g_hw_sl_pv_9200.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.applandeo.materialcalendarview.EventDay;

import java.util.Calendar;

/** MyEventDay objecten worden gebruikt in de kalender voor het bijhouden wanneer een activiteit plaatsvindt
 * a.d.h.v. calendar objecten die de kalender ook gebruikt
 * Created by Lucas on 23-11-2017.
 */
public class MyEventDay extends EventDay implements Parcelable {
    private String mNote;
    public MyEventDay(Calendar day, int imageResource, String note) {
        super(day, imageResource);
        mNote = note;
    }
    String getNote() {
        return mNote;
    }
    private MyEventDay(Parcel in) {
        super((Calendar) in.readSerializable(), in.readInt());
        mNote = in.readString();
    }
    public static final Creator<MyEventDay> CREATOR = new Creator<MyEventDay>() {
        @Override
        public MyEventDay createFromParcel(Parcel in) {
            return new MyEventDay(in);
        }
        @Override
        public MyEventDay[] newArray(int size) {
            return new MyEventDay[size];
        }
    };
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable(getCalendar());
        parcel.writeInt(getImageResource());
        parcel.writeString(mNote);
    }
    @Override
    public int describeContents() {
        return 0;
    }
}