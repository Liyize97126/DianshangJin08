package com.bawei.dianshangjin08.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 页面基类
 */
public abstract class BaseActivity extends AppCompatActivity {
    //定义
    private Unbinder bind;
    //创建视图
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        bind = ButterKnife.bind(this);
        initView(savedInstanceState);
    }
    //销毁视图
    @Override
    protected void onDestroy() {
        super.onDestroy();
        initDestroy();
        bind.unbind();
    }
    //方法封装
    protected abstract int getLayoutId();
    protected abstract void initView(Bundle savedInstanceState);
    protected abstract void initDestroy();
}
