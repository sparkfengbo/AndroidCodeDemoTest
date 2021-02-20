package com.sparkfengbo.app.android.fragment;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.sparkfengbo.app.R;

import static android.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

public class FragmentTestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("fengbo", "Activity#onCreate");
        setContentView(R.layout.activity_fragment_test);
        FragmentManager fm = getFragmentManager();
        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Log.d("fengbo", "onBackStackChanged");
            }
        });
        FragmentTransaction ft =fm.beginTransaction();
        ft.replace(R.id.fragment_container_1, new TestFragment1());
        ft.addToBackStack("WTF");
        ft.commit();

        FragmentTransaction ft2 =fm.beginTransaction();
        ft2.addToBackStack("WTF1");
        ft2.replace(R.id.fragment_container_2, new TestFragment2());
        ft2.commit();

        findViewById(R.id.btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPush();
            }
        });


        findViewById(R.id.btn_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPop();
            }
        });
    }

    public void onPush() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction =fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_1, new TestFragment3());
        fragmentTransaction.addToBackStack("WTF");
        fragmentTransaction.commit();

        FragmentTransaction ft2 =fm.beginTransaction();
        ft2.addToBackStack("WTF");
        ft2.replace(R.id.fragment_container_2, new TestFragment4());
        ft2.commit();
    }

    public void onPop() {
        FragmentManager fm = getFragmentManager();
//        fm.popBackStack("WTF1", 0);
        fm.popBackStack("WTF1", POP_BACK_STACK_INCLUSIVE);
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
