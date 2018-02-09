package com.sparkfengbo.app.android.aidltest;

import com.sparkfengbo.app.firstpage.TLog;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InnerMessengerService extends Service {

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg != null) {
                if (msg.getData() != null && msg.getData().getString("msg") != null) {
                    TLog.e(msg.getData().getString("msg"));
                }

                Messenger client = msg.replyTo;
                Message message  = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putString("msg", "String from InnerMessengerService");
                message.setData(bundle);
                try {
                    client.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private final Messenger mMessenger = new Messenger(new MessengerHandler());


    @Override
    public void onCreate() {
        TLog.e("InnerMessengerService onCreate");

        super.onCreate();

    }

    @Override
    public IBinder onBind(Intent intent) {
        TLog.e("InnerMessengerService onBind");
        return mMessenger.getBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
