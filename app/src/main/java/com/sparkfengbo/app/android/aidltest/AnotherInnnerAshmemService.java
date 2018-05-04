package com.sparkfengbo.app.android.aidltest;

import com.sparkfengbo.app.android.base.TLog;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AnotherInnnerAshmemService extends Service {

    public static final String SERVICE_NAME = "AnothoerAshmemService";
    private IBinder mAnotherAshmemStub;

    public AnotherInnnerAshmemService() {

        mAnotherAshmemStub = new AnotherAshmemStub();
        try {
            Class<?> serviceManagerCls = Class.forName("android.os.ServiceManager");

            Class[] args = new Class[]{String.class, IBinder.class};
            Method addServiceMethod = serviceManagerCls.getMethod("addService", args);
            addServiceMethod.setAccessible(true);

            Object[] realArgs = new Object[]{SERVICE_NAME, mAnotherAshmemStub};

            /**
             * 这里会报错 E/ServiceManager: add_service('AnothoerAshmemService',97) uid=10147 - PERMISSION DENIED
             *
             * 这也是Android增加了一个验证，防止第三方app通过反射随意通过ServiceManager注册服务
             */
            addServiceMethod.invoke(null, realArgs);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
