package examplet.com.suppersystm.me;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import examplet.com.suppersystm.R;
import examplet.com.suppersystm.manager.BaseActivity;

public class FunctionIntr extends BaseActivity {
    //标题栏
    private ImageView mImgvTitleBack;
    private TextView mTitleCenter;
    private  TextView mTitleLeft;
    private ImageView mImgvTitleAdd;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_intr);
        initView();
    }
    private void initView() {
        //标题栏
        mTitleCenter = (TextView) findViewById(R.id.title_center);
        mTitleCenter.setVisibility(View.INVISIBLE);
        //返回键及文字部分隐藏
        mImgvTitleBack = (ImageView) findViewById(R.id.imgv_title_back);
        mImgvTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mTitleLeft = (TextView) findViewById(R.id.title_left);
        mTitleLeft.setText("功能介绍");
        mImgvTitleAdd = (ImageView) findViewById(R.id.imgv_title_add);
        mImgvTitleAdd.setVisibility(View.GONE);
    }
}
