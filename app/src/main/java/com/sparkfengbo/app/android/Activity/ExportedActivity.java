package com.sparkfengbo.app.android.Activity;



import com.sparkfengbo.app.R;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.util.Log;

public class ExportedActivity extends Activity {

    Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Log.e("fengbo", "ExportedActivity#onCreate");

        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("fengbo", "ExportedActivity#TikTik");
                mHandler.postDelayed(this, 1000);
            }
        }, 1000);
    }

    @Override
    protected void onNewIntent(final Intent intent) {
        super.onNewIntent(intent);
        Log.e("fengbo", "ExportedActivity#onNewIntent");
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("fengbo", "ExportedActivity#onDestroy");
    }
}
