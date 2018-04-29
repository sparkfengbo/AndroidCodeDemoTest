package com.sparkfengbo.app.android.aidltest;

import com.sparkfengbo.app.android.base.TLog;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InnerAIDLService extends Service {

    //CopyOnWriteArrayList支持并发读写
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

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            //方式2 : 这里进行权限验证，不通过返回false
            return super.onTransact(code, data, reply, flags);
        }
    };

    @Override
    public void onCreate() {
        TLog.e("InnerAIDLService onCreate");

        super.onCreate();
        mBooks.add(new Book(0, "first book id 0"));
        mBooks.add(new Book(1, "second book id 1"));
    }

    @Override
    public IBinder onBind(Intent intent) {
        TLog.e("InnerAIDLService onBind");
        //方式1 : 这里进行权限验证
        return mBinder;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
