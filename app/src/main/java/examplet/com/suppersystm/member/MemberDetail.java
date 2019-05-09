package examplet.com.suppersystm.member;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import examplet.com.suppersystm.R;
import examplet.com.suppersystm.manager.BaseActivity;
import examplet.com.suppersystm.staff.StaffDetail;
import examplet.com.suppersystm.staff.UpdateStaff;

public class MemberDetail extends BaseActivity implements View.OnClickListener{
    //标题栏
    private ImageView mImgvTitleBack;
    private TextView mTitleLeft;
    private TextView mTitleCenter;
    private ImageView mImgvTitleAdd;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_detail);
        initView();
    }

    private void initView() {
        //标题不符
        mTitleCenter = (TextView) findViewById(R.id.title_center);
        mTitleCenter.setText("会员信息");
        //返回键及文字部分隐藏
        mImgvTitleBack = (ImageView) findViewById(R.id.imgv_title_back);
//        mImgvTitleBack.setVisibility(View.GONE);
        mImgvTitleBack.setOnClickListener(this);
        mImgvTitleAdd = (ImageView) findViewById(R.id.imgv_title_add);
//        Glide.with(this).load(R.drawable.edit_staff1).into(mImgvTitleAdd);
        mImgvTitleAdd.setVisibility(View.INVISIBLE);
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
