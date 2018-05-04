package com.sparkfengbo.app.android.aidltest;

import com.sparkfengbo.app.R;
import com.sparkfengbo.app.android.base.TLog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AnotherAshmemActivty extends Activity {

    private IMemoryService mIMemoryService;

    private ParcelFileDescriptor mParcelFileDescriptor;

    private byte[] mInfo = new byte[1024];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another_ashmem_activty);

        Intent intent = new Intent(this, AnotherInnnerAshmemService.class);
        startService(intent);


        IBinder binder = getIBinder();

        if (binder != null) {
            mIMemoryService = IMemoryService.Stub.asInterface(binder);

            if (mIMemoryService != null) {


                try {
                    mIMemoryService.setValue("String send from client(AnotherAshmemActivty)");

                    mParcelFileDescriptor =  mIMemoryService.getFileDescriptor();

                    if (mParcelFileDescriptor != null && mParcelFileDescriptor.getFileDescriptor() != null) {
                        FileInputStream fin = new FileInputStream(mParcelFileDescriptor.getFileDescriptor());

                        int length = fin.read(mInfo);
                        String result = new String(mInfo, 0, length);

                        TLog.e(result);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            TLog.e("AnotherAshmemActivty get null IBinder");
        }

    }


    private IBinder getIBinder() {
        try {
            Class<?> serviceManagerCls = null;
            serviceManagerCls = Class.forName("android.os.ServiceManager");

            Class[] args = new Class[]{String.class};
            Method getServiceMethod = serviceManagerCls.getMethod("getService", args);
            getServiceMethod.setAccessible(true);

            Object[] realArgs = new Object[]{AnotherInnnerAshmemService.SERVICE_NAME};

            return (IBinder) getServiceMethod.invoke(null, realArgs);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
