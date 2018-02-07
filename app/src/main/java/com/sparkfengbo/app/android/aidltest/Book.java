package com.sparkfengbo.app.android.aidltest;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fengbo on 2018/2/6.
 */

public class Book implements Parcelable {

    public int id;
    public String content;

    public Book(int id, String content) {
        this.id = id;
        this.content = content;
    }

    protected Book(Parcel in) {
        id = in.readInt();
        content = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(content);
    }
}
