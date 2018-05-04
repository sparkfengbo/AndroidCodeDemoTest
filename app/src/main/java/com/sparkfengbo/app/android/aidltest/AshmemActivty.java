package com.sparkfengbo.app.android.aidltest;

import com.sparkfengbo.app.R;
import com.sparkfengbo.app.android.base.TLog;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.MemoryFile;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

import java.io.FileInputStream;
import java.io.IOException;


/**
 * 关于匿名内存的文章
 *
 * <a href = "https://blog.csdn.net/luoshengyang/article/details/6651971">Android系统匿名共享内存Ashmem（Anonymous Shared Memory）简要介绍和学习计划</>
 * <a href = "https://blog.csdn.net/luoshengyang/article/details/6664554">Android系统匿名共享内存Ashmem（Anonymous Shared Memory）驱动程序源代码分析</>
 * <a href = "https://blog.csdn.net/luoshengyang/article/details/6666491">Android系统匿名共享内存Ashmem（Anonymous Shared Memory）在进程间共享的原理分析</>
 */
public class AshmemActivty extends Activity {

    private IMemoryService mIMemoryService;

    private ParcelFileDescriptor mParcelFileDescriptor;

    private byte[] mInfo = new byte[1024];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ashmem_activty);


        Intent intent = new Intent(this, InnerAshmemService.class);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            mIMemoryService =  IMemoryService.Stub.asInterface(service);

            try {
                mIMemoryService.setValue("String send from client(AshmemActivty)");

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

        @Override
        public void onServiceDisconnected(ComponentName name) {


        }
    };
}
