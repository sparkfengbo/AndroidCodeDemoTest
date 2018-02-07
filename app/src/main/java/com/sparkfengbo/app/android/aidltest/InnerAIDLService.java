package com.sparkfengbo.app.android.aidltest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InnerAIDLService extends Service {

    private CopyOnWriteArrayList<Book> mBooks = new CopyOnWriteArrayList<Book>();

    private Binder mBinder = new IBookManagerInterface.Stub() {

        @Override
        public List<Book> getList() throws RemoteException {
            return mBooks;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBooks.add(book);
        }
    };

    @Override
    public void onCreate() {
        Log.e("fengbo", "service onCreate");

        super.onCreate();
        mBooks.add(new Book(0, "first book id 0"));
        mBooks.add(new Book(1, "second book id 1"));
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("fengbo", "service onBind");
        return mBinder;
    }

}
