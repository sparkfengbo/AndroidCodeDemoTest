package com.sparkfengbo.app.firstpage;

import android.app.Application;
import android.os.Process;

/**
 * Created by fengbo on 2018/2/9.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        TLog.e("BaseApplication onCreate : " +  Process.myPid());
    }
}
