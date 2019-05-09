package examplet.com.suppersystm.me;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import examplet.com.suppersystm.MainActivity;
import examplet.com.suppersystm.R;
import examplet.com.suppersystm.fragment.MoreFragment;
import examplet.com.suppersystm.manager.BaseActivity;

public class PersonalInfomation extends BaseActivity implements View.OnClickListener{
    //标题栏
    private ImageView mImgvTitleBack;
    private TextView mTitleCenter;
    private  TextView mTitleLeft;
    private ImageView mImgvTitleAdd;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_infomation);
        initView();
    }
    private void initView(){
        mTitleCenter = (TextView) findViewById(R.id.title_center);
        mTitleCenter.setText("个人信息");
        //返回键及文字部分隐藏
        mImgvTitleBack = (ImageView) findViewById(R.id.imgv_title_back);
        mImgvTitleBack.setOnClickListener(this);
        mTitleLeft = (TextView) findViewById(R.id.title_left);
        mImgvTitleAdd = (ImageView) findViewById(R.id.imgv_title_add);
        mImgvTitleAdd.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgv_title_back:
                finish();

                break;
        }
    }
}
