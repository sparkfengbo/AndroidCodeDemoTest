package com.sparkfengbo.app.android;

import com.sparkfengbo.app.R;
import com.sparkfengbo.app.android.aidltest.IBookManagerInterface;
import com.sparkfengbo.app.android.aidltest.InnerMessengerService;
import com.sparkfengbo.app.android.annotations.FbAnnotInject;
import com.sparkfengbo.app.android.annotations.FBBindContentView;
import com.sparkfengbo.app.firstpage.TLog;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

@FBBindContentView(value = R.layout.activity_main)
public class MessengerTestActivity extends Activity {

    private IBookManagerInterface iBookManagerInterface;

    private Messenger mClientSender;

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg != null) {
                if (msg.getData() != null && msg.getData().getString("msg") != null) {
                    TLog.e(msg.getData().getString("msg"));
                }
            }
        }
    }

    private final Messenger mClientReceive = new Messenger(new MessengerTestActivity.MessengerHandler());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FbAnnotInject.inject(this);

        tryBindService();
    }

    private void tryBindService() {
        Intent intent = new Intent(this, InnerMessengerService.class);
        boolean isSuccessConnect = this.bindService(intent, mConnection, BIND_AUTO_CREATE);
        if (isSuccessConnect) {
            TLog.d("service connenct success");
        } else {
            TLog.d("service connenct fail");
        }
    }


    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }


    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TLog.e("service connencted " + name.getPackageName());

            mClientSender = new Messenger(service);

            Message msg = Message.obtain();
            //java.lang.RuntimeException: Can't marshal non-Parcelable objects across processes.
//            msg.obj = "String from MessengerTestActivity";

            Bundle bundle = new Bundle();
            bundle.putString("msg", "String from MessengerTestActivity");
            msg.setData(bundle);
            msg.replyTo = mClientReceive;
            try {
                mClientSender.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
