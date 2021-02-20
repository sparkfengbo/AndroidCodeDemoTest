package com.sparkfengbo.app.android.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sparkfengbo.app.R;

public class TestFragment3 extends Fragment {
    private static final String TAG = "TestFragment3";

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        Log.d("fengbo", TAG+"#onAttach");
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("fengbo", TAG+"#onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, final Bundle savedInstanceState) {
        Log.d("fengbo", TAG+"#onCreateView");
        return inflater.inflate(R.layout.fragment_test_3, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("fengbo", TAG+"#onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("fengbo", TAG+"#onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("fengbo", TAG+"#onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("fengbo", TAG+"#onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("fengbo", TAG+"#onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("fengbo", TAG+"#onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("fengbo", TAG+"#onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("fengbo", TAG+"#onDetach");
    }
}
