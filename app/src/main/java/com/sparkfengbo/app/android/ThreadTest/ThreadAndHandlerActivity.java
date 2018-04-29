package com.sparkfengbo.app.android.ThreadTest;

import com.sparkfengbo.app.R;
import com.sparkfengbo.app.android.annotations.FBBindColor;
import com.sparkfengbo.app.android.annotations.FBBindContentView;
import com.sparkfengbo.app.android.annotations.FBBindString;
import com.sparkfengbo.app.android.annotations.FBBindView;
import com.sparkfengbo.app.android.annotations.FbAnnotInject;
import com.sparkfengbo.app.android.annotations.FbBindListener;
import com.sparkfengbo.app.android.base.TLog;
import com.sparkfengbo.app.libfbannotation.c21annotation.mydemo.FBEnumTest;
import com.sparkfengbo.app.libfbannotation.c21annotation.mydemo.FBFloatRange;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

@FBBindContentView(value = R.layout.activity_main)
public class ThreadAndHandlerActivity extends Activity {
    @FBBindView(R.id.tv_1)
    @FBBindColor(Color.GREEN)
    @FBBindString(R.string.test_1)
    private TextView mTextView1;

    /**
     * 注意，这里FBBindView，FBBindColor这两个注解都是添加了@Documented元注解，但FBBindString没有添加
     * 所以AS在mTextView2按F1查看注释的时候是看不到FBBindString信息的，但是能看到FBBindView，FBBindColor的信息
     */
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


    ThreadLocal<JsonTestMetaData> local = new ThreadLocal<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FbAnnotInject.inject(this);

        JsonTestMetaData data = new JsonTestMetaData();
        data.json_str = "main_thread";
        local.set(data);

        TLog.e(local.get().json_str);

        new Thread(new Runnable() {
            @Override
            public void run() {
                JsonTestMetaData data = new JsonTestMetaData();
                data.json_str = "other_thread";
                local.set(data);

                TLog.e(local.get().json_str);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (local.get() != null) {
                    TLog.e(local.get().json_str);
                } else {
                    TLog.e("local.get() is null");

                }
            }
        }).start();

        SharedPreferences sharedPreferences = this.getSharedPreferences("setting", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("fengbo", "Hello fengbo");
        editor.commit();
        TLog.e(local.get().json_str);

        HandlerThread thread  = new HandlerThread("new_handler_thread");
        thread.start();
        Handler handler = new Handler(thread.getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences editor = this.getSharedPreferences("setting", MODE_PRIVATE);
        String testStr = editor.getString("fengbo", "null");
        TLog.e(testStr);
    }

    @FbBindListener(values = {R.id.tv_1, R.id.tv_2, R.id.tv_3})
    private void onClick(View view) {
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TLog.d("ANR???");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
