package com.feicuiedu.hunttreasure;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.feicuiedu.hunttreasure.commons.ActivityUtils;
import com.feicuiedu.hunttreasure.user.LoginActivity;
import com.feicuiedu.hunttreasure.user.RegisterActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MainActivity extends AppCompatActivity {
    public static final String MATION_INSTANCE = "OFF_MAIN_ACTIVITY";
    private ActivityUtils activityUtils;
    private Unbinder bind;
    private BroadcastReceiver mReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityUtils=new ActivityUtils(this);
        bind = ButterKnife.bind(this);
        IntentFilter intentFilter=new IntentFilter(MATION_INSTANCE);
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver,intentFilter);
    }

    /**
     * 点击事件（登录）（注册）
     */
    @OnClick({R.id.btn_Register, R.id.btn_Login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_Register:
                activityUtils.startActivity(RegisterActivity.class);
                break;
            case R.id.btn_Login:
                activityUtils.startActivity(LoginActivity.class);
                break;
        }
    }

    /**
     * 摧毁文件
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //关闭黄油刀
        bind.unbind();
    }
}
