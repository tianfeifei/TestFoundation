package com.example.tianfei.foundation.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tianfei.foundation.log.LogUtil;


/**
 * fragment基础类
 * Created by zhangyunfei on 16/7/26.
 */
public class BaseFragment extends Fragment {
    private String ThisClassName;

    public void alert(final String msg) {
        if (getActivity() == null || getActivity().isFinishing())
            return;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void alert(final int sourceID) {
        if (getActivity() == null || getActivity().isFinishing())
            return;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), sourceID, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public Context getContext(){
        return getActivity();
    }

    protected String getThisClassName() {
        if (ThisClassName == null) {
            ThisClassName = getClass().getSimpleName();
        }
        return ThisClassName;
    }


    @Override
    public void onStop() {
        LogUtil.d(getThisClassName(), "## onStop");
        super.onStop();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtil.d(getThisClassName(), "## onCreate");
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.d(getThisClassName(), "## onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        LogUtil.d(getThisClassName(), "## onDestroy");
        super.onDestroy();
    }

    @Override
    public void onStart() {
        LogUtil.d(getThisClassName(), "## onStart");
        super.onStart();
    }

    @Override
    public void onPause() {
        LogUtil.d(getThisClassName(), "## onPause");
        super.onPause();
    }

    @Override
    public void onResume() {
        LogUtil.d(getThisClassName(), "## onResume");
        super.onResume();
    }


    @Override
    public void onAttach(Activity activity) {
        LogUtil.d(getThisClassName(), "## onAttach");
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        LogUtil.d(getThisClassName(), "## onDetach");
        super.onDetach();
    }
}
