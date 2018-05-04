package com.sparkfengbo.app.android.aidltest;

import com.sparkfengbo.app.R;
import com.sparkfengbo.app.android.annotations.FbAnnotInject;
import com.sparkfengbo.app.android.annotations.FBBindColor;
import com.sparkfengbo.app.android.annotations.FBBindContentView;
import com.sparkfengbo.app.android.annotations.FBBindString;
import com.sparkfengbo.app.android.annotations.FBBindView;
import com.sparkfengbo.app.android.base.TLog;
import com.sparkfengbo.app.libfbannotation.c21annotation.mydemo.FBEnumTest;
import com.sparkfengbo.app.libfbannotation.c21annotation.mydemo.FBFloatRange;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

/**
 *
 * 关于Binder的文章
 *
 * <a href = "https://blog.csdn.net/luoshengyang/article/details/6618363">Android进程间通信（IPC）机制Binder简要介绍和学习计划</>
 * <a href = "http://www.cnblogs.com/innost/archive/2011/01/09/1931456.html">Android深入浅出之Binder机制</>
 */
@FBBindContentView(value = R.layout.activity_main)
public class AIDLTestActivity extends Activity {
    @FBBindView(R.id.tv_1)
    @FBBindColor(Color.GREEN)
    @FBBindString(R.string.test_1)
    private TextView mTextView1;


    @FBBindView(R.id.tv_2)
    @FBBindColor(Color.BLACK)
    @FBBindString(R.string.test_2)
    private TextView mTextView2;

    @FBBindView(R.id.tv_3)
    @FBBindColor(Color.BLUE)
    @FBBindString(R.string.test_3)
    private TextView mTextView3;

    @FBFloatRange(from = 0, to = 10)
    private Float mFloatTest = 100f;

    @FBEnumTest
    private int color = 4;

    private IBookManagerInterface iBookManagerInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FbAnnotInject.inject(this);

        tryBindService();
    }

    private void tryBindService() {
        /**
         * 1.InnerAIDLService运行在remote进程，所以也是IPC
         */

        Intent intent = new Intent(this, InnerAIDLService.class);

        boolean isSuccessConnect = this.bindService(intent, mConnection, BIND_AUTO_CREATE);

        if (isSuccessConnect) {
            TLog.d("service connenct success");
        } else {
            TLog.d("service connenct fail");
        }

        /**-----------------------------------------------------------------------------------------------------------------*/

        /**
         * 2.找到AIDLServer 工程下的MyService
         *
         * 通过setComponent无法找到
         *
         * 因为是另一个app的service，需要传递book，而AIDL要求包名相同，和本APP的包名有冲突 所以setComponent找不到
         * ```
         * intent.setComponent(new ComponentName("com.sparkfengbo.app", "com.sparkfengbo.app.MyService"));
         * ```
         * 但是能够找到本app内的InnerAIDLService
         *
         * 此AIDL的Server提供工程在https://github.com/sparkfengbo/AIDLServer中
         */

//        Intent intent = new Intent("com.sparkfengbo.ng");
//        Intent dealedIntent = createExplicitFromImplicitIntent(this, intent);
//        boolean isSuccessConnect = this.bindService(dealedIntent, mConnection, BIND_AUTO_CREATE);
//
//        if (isSuccessConnect) {
//            TLog.d("service connenct success");
//        } else {
//            TLog.d( "service connenct fail");
//        }

    }

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (iBookManagerInterface == null) {
                return;
            }

            TLog.e("DeathRecipient binderDied");
            iBookManagerInterface.asBinder().unlinkToDeath(mDeathRecipient, 0);
            iBookManagerInterface = null;

            tryBindService();
        }
    };

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TLog.e("service connencted " + name.getPackageName());

            iBookManagerInterface = IBookManagerInterface.Stub.asInterface(service);

            try {
                service.linkToDeath(mDeathRecipient, 0);

                List<Book> bookList = iBookManagerInterface.getList();
                if (bookList != null && bookList.size() > 0) {
                    for (Book book : bookList) {
                        TLog.e("get book list from Server【client尚未向server插入数据】" + book.content);
                    }
                } else {
                    TLog.e("service connencted : get null or empty booklist");
                }

                iBookManagerInterface.addBook(new Book(2, "add from client : 哈222😆"));
                iBookManagerInterface.addBook(new Book(3, "add from client : 哈333😆"));


                bookList = iBookManagerInterface.getList();

                if (bookList != null && bookList.size() > 0) {
                    for (Book book : bookList) {
                        TLog.e("get book list from Server【client向server插入了数据】" + book.content);
                    }
                } else {
                    TLog.e("service connencted : get null or empty booklist");

                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("TAG", "onServiceDisconnected " + name.getPackageName());

        }
    };

    /**
     * http://blog.csdn.net/shenzhonglaoxu/article/details/42675287
     */
    public Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

        // Make sure only one match was found
//         || resolveInfo.size() != 1
        if (resolveInfo == null || resolveInfo.size() == 0) {
            TLog.e("no explicit service");
            return implicitIntent;
        }

        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);

        // Set the component to be explicit
        explicitIntent.setComponent(component);

        return explicitIntent;
    }



    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }
}
