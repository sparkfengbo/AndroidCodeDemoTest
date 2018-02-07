package com.sparkfengbo.app.android;

import com.sparkfengbo.app.R;
import com.sparkfengbo.app.android.aidltest.Book;
import com.sparkfengbo.app.android.aidltest.IBookManagerInterface;
import com.sparkfengbo.app.android.aidltest.InnerAIDLService;
import com.sparkfengbo.app.android.annotations.FbAnnotInject;
import com.sparkfengbo.app.android.annotations.FBBindColor;
import com.sparkfengbo.app.android.annotations.FBBindContentView;
import com.sparkfengbo.app.android.annotations.FBBindString;
import com.sparkfengbo.app.android.annotations.FBBindView;
import com.sparkfengbo.app.firstpage.TLog;
import com.sparkfengbo.app.libfbannotation.c21annotation.mydemo.FBEnumTest;
import com.sparkfengbo.app.libfbannotation.c21annotation.mydemo.FBFloatRange;
import com.sparkfengbo.app.android.annotations.FbBindListener;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FbAnnotInject.inject(this);

        /**
         * 1.InnerAIDLService运行在remote进程，所以也是IPC
         */

//        Intent intent = new Intent(this, InnerAIDLService.class);
//        bindService(intent, mConnection, BIND_AUTO_CREATE);

        /**---------------------------*/

        /**
         * 2.找到AIDLServer（）工程下的MyService
         */

        Intent intent = new Intent("com.sparkfengbo.ng");

        /**
         * 通过setComponent无法找到
         *
         * 因为是另一个app的service，需要传递book，而AIDL要求包名相同，和本APP的包名有冲突 所以setComponent找不到
         * ```
         * intent.setComponent(new ComponentName("com.sparkfengbo.app", "com.sparkfengbo.app.MyService"));
         * ```
         * 但是能够找到本app内的InnerAIDLService
         */
//

        Intent dealedIntent = createExplicitFromImplicitIntent(this, intent);
        boolean isSuccessConnect = this.bindService(dealedIntent, mConnection, BIND_AUTO_CREATE);

        if (isSuccessConnect) {
            TLog.d("service connenct success");
        } else {
            TLog.d( "service connenct fail");
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

            IBookManagerInterface iBookManagerInterface = IBookManagerInterface.Stub.asInterface(service);

            try {
                List<Book> bookList = iBookManagerInterface.getList();
                if (bookList != null && bookList.size() > 0) {
                    for (Book book : bookList) {
                        TLog.e(" " + book.content);
                    }
                } else {
                    TLog.e("service connencted : get null or empty booklist");
                }

                iBookManagerInterface.addBook(new Book(2, "哈哈哈哈😆"));

                bookList = iBookManagerInterface.getList();

                if (bookList != null && bookList.size() > 0) {
                    for (Book book : bookList) {
                        TLog.e(" " + book.content);
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
     *
     * @param context
     * @param implicitIntent
     * @return
     */
    public Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

        // Make sure only one match was found
//         || resolveInfo.size() != 1
        if (resolveInfo == null || resolveInfo.size() == 0) {
            TLog.e( "no explicit service");
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
}
