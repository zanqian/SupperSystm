package examplet.com.suppersystm.manager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by pc on 2019/3/14.
 */
//所有活动的父类
public class BaseActivity extends AppCompatActivity {
    private TextView mTitleLeft,mTitleCenter;
    @Override
   public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("BaseActivity",getClass().getSimpleName());
        ActivityCollector.addActivity(this);

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    //标题栏操作

}
