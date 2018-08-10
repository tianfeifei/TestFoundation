package com.example.tianfei.foundation.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.example.tianfei.foundation.log.LogUtil;
import com.example.tianfei.foundation.mvp.IMvpView;


/**
 * Activity 基础类 FragmentActivity
 * Created by zhangyunfei on 16/7/26.
 */
public class BaseFragmentActivity extends FragmentActivity implements IMvpView {
    private static final String TAG = BaseFragmentActivity.class.getSimpleName();
    private String ThisClassName;

    public void alert(final String msg) {
        if (isFinishing()) return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void alert(final int sourceID) {
        if (isFinishing()) return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), sourceID, Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected String getThisClassName() {
        if (ThisClassName == null) {
            ThisClassName = getClass().getSimpleName();
        }
        return ThisClassName;
    }

    @Override
    public Context getContext() {
        return this;
    }

    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.d(TAG, String.format("## %s onActivityResult", getThisClassName()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(TAG, String.format("## %s onCreate", getThisClassName()));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtil.d(TAG, String.format("## %s onNewIntent", getThisClassName()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.d(TAG, String.format("## %s onStart", getThisClassName()));
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.d(TAG, String.format("## %s onStop", getThisClassName()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d(TAG, String.format("## %s onDestroy", getThisClassName()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d(TAG, String.format("## %s onResume", getThisClassName()));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.d(TAG, String.format("## %s onPause", getThisClassName()));
    }
}
