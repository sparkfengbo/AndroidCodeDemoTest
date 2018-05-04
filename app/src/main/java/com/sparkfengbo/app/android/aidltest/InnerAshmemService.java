package com.sparkfengbo.app.android.aidltest;

import com.sparkfengbo.app.android.base.TLog;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.MemoryFile;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InnerAshmemService extends Service {

    private MemoryFile mMemoryFile = null;

    private Binder mBinder = new IMemoryService.Stub() {

        @Override
        public ParcelFileDescriptor getFileDescriptor() {
            if (mMemoryFile == null) {
                return null;
            }

            ParcelFileDescriptor pfd = null;

            /**
             * compileSDK 25 MemoryFile的API有修改，getFileDescriptor方法为hide，只能通过反射的方法获得。
             */
            try {
                Class<?> memoryFileCls = Class.forName("android.os.MemoryFile");
                Method getFileDescriptor = memoryFileCls.getMethod("getFileDescriptor", new Class<?>[]{});
                getFileDescriptor.setAccessible(true);


                Class<?> parcelFileDescriptorCls = Class.forName("android.os.ParcelFileDescriptor");
                Constructor pflConstructor = parcelFileDescriptorCls.getConstructor(FileDescriptor.class);
                pflConstructor.setAccessible(true);

                FileDescriptor fileDescriptor = (FileDescriptor) getFileDescriptor.invoke(mMemoryFile, new Object[]{});

                pfd = (ParcelFileDescriptor) pflConstructor.newInstance(fileDescriptor);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }

            return pfd;
        }

        @Override
        public void setValue(String value) throws RemoteException {
            if (mMemoryFile != null && value != null) {

                value += " String append from server(InnerAshmemService)";

                byte[] buffer = value.getBytes();
                TLog.e("InnerAshmemService setValue buffer.length : " + buffer.length);
                try {
                    mMemoryFile.writeBytes(buffer, 0, 0, buffer.length);
                    TLog.e("set value in service done");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                TLog.e("set value in service mMemoryFile is null");
            }
        }
    };

    public InnerAshmemService() {
        try {
            mMemoryFile = new MemoryFile("Ashmem", 500);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
