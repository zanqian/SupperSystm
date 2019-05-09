package examplet.com.suppersystm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import examplet.com.suppersystm.R;
import examplet.com.suppersystm.login.LoginActivity;


//app初始进入的画面
public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隐藏顶部标题栏
//        getSupportActionBar().hide();
        //设置延迟时间
        handler.sendEmptyMessageDelayed(0,1000);
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            //实现页面跳转
            Intent intent=new Intent(Welcome.this, LoginActivity.class);
            startActivity(intent);
            finish();
            super.handleMessage(msg);
        }
    };
}
