package cn.hl.smartfactory.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Message;

import com.xuexiang.xutil.app.ActivityUtils;

import cn.hl.smartfactory.R;
import cn.hl.smartfactory.core.BaseActivity;
import cn.hl.smartfactory.utils.TokenUtils;
import cn.hl.smartfactory.utils.WeakHandler;
import cn.hl.smartfactory.utils.WindowUtil;

public class SplashActivity extends BaseActivity implements WeakHandler.IHandle {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        WindowUtil.hideWindowStatusBar(getWindow());
        WeakHandler handler = new WeakHandler(this);
        handler.sendEmptyMessageDelayed(0, 2000);
    }

    @Override
    public void handleMessage(Message msg) {
        loginOrGoMainPage();
    }
    private void loginOrGoMainPage() {
        if (TokenUtils.hasToken()) {
            ActivityUtils.startActivity(MainActivity.class);
        } else {
            ActivityUtils.startActivity(LoginActivity.class);
        }
        finish();
    }

}