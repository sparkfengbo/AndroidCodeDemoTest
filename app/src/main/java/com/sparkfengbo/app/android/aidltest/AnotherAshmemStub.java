package com.sparkfengbo.app.android.aidltest;

import com.sparkfengbo.app.android.base.TLog;

import android.os.MemoryFile;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by fengbo on 2018/5/4.
 */

public class AnotherAshmemStub extends IMemoryService.Stub {
    private MemoryFile mMemoryFile = null;


    public AnotherAshmemStub() {
        try {
            mMemoryFile = new MemoryFile("Ashmem", 500);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ParcelFileDescriptor getFileDescriptor() throws RemoteException {
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

            value += " String append from another server(AnotherAshmemStub)";

            byte[] buffer = value.getBytes();
            try {
                mMemoryFile.writeBytes(buffer, 0, 0, buffer.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
        }
    }
}
