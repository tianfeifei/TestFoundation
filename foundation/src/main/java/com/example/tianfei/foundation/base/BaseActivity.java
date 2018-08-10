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
 * Activity 基础类
 * Created by zhangyunfei on 16/7/26.
 */
public class BaseActivity extends FragmentActivity implements IMvpView {
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
        LogUtil.d(getThisClassName(), "## onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogUtil.d(getThisClassName(), "## onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        LogUtil.d(getThisClassName(), "## onNewIntent");
        super.onNewIntent(intent);
    }

    @Override
    protected void onStart() {
        LogUtil.d(getThisClassName(), "## onStart");
        super.onStart();
    }

    @Override
    protected void onStop() {
        LogUtil.d(getThisClassName(), "## onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        LogUtil.d(getThisClassName(), "## onDestroy begin");
        super.onDestroy();
        LogUtil.d(getThisClassName(), "## onDestroy end");
    }

    @Override
    protected void onResume() {
        LogUtil.d(getThisClassName(), "## onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        LogUtil.d(getThisClassName(), "## onPause");
        super.onPause();
    }
}
