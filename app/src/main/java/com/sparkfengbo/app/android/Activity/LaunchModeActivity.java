package com.sparkfengbo.app.android.Activity;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sparkfengbo.app.R;
import com.sparkfengbo.app.android.fragment.TestFragment1;
import com.sparkfengbo.app.android.fragment.TestFragment2;
import com.sparkfengbo.app.android.fragment.TestFragment3;
import com.sparkfengbo.app.android.fragment.TestFragment4;

import static android.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

public class LaunchModeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("fengbo", "Activity#onCreate");
        setContentView(R.layout.activity_launch_mode);
        Button btn = (Button) findViewById(R.id.btn_1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LaunchModeActivity.this, LaunchMode2Activity.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("fengbo", "Activity#onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("fengbo", "Activity#onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("fengbo", "Activity#onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("fengbo", "Activity#onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("fengbo", "Activity#onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("fengbo", "Activity#onRestart");
    }
}
