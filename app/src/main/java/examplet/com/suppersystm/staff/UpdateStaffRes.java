package examplet.com.suppersystm.staff;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import examplet.com.suppersystm.R;
import examplet.com.suppersystm.bean.User;
import examplet.com.suppersystm.goods.AddGoods;
import examplet.com.suppersystm.goods.AddGoodsRes;
import examplet.com.suppersystm.goods.GoodsActivity;

public class UpdateStaffRes extends AppCompatActivity {
    private  String result1;
    private  String result2;
    //标题栏
    private ImageView mImgvTitleBack;
    private TextView mTitleLeft;
    private TextView mTitleCenter;
    private ImageView mImgvTitleAdd;
    private ImageView mStaffResImg;
    private TextView mStaffResText;
    private TextView mStaffNextActivity;
    private User mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_staff_res);
        Bundle bundle=getIntent().getExtras();
        result1= (String) bundle.get("user1");
        result2=(String) bundle.get("user2");
        mUser=(User) bundle.get("user") ;
        handler.sendEmptyMessageDelayed(0,4000);
        initView();
    }
    private void initView(){
        //标题
        mTitleCenter = (TextView) findViewById(R.id.title_center);
        mTitleCenter.setText("员工修改结果");
        //返回键及文字部分
        mImgvTitleBack = (ImageView) findViewById(R.id.imgv_title_back);
        mImgvTitleBack.setVisibility(View.INVISIBLE);
        mTitleLeft = (TextView) findViewById(R.id.title_left);
        mTitleLeft.setVisibility(View.INVISIBLE);
        mImgvTitleAdd = (ImageView) findViewById(R.id.imgv_title_add);
        mImgvTitleAdd.setVisibility(View.INVISIBLE);
        mStaffResImg = (ImageView) findViewById(R.id.staff_res_img);
        mStaffResText = (TextView) findViewById(R.id.staff_res_text);
        mStaffResText.setText(result2);
        mStaffNextActivity = (TextView) findViewById(R.id.staff_next_activity);

        if (result1.equals("success")){
            //glide加载图片
            Glide.with(this).load(R.drawable.success_staff).into(mStaffResImg);
            mStaffNextActivity.setText("员工管理界面");
        }else{
            Glide.with(this).load(R.drawable.errot_staff).into(mStaffResImg);
            mStaffNextActivity.setText("员工修改界面");
        }

    }
    //活动线程
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            if (result1.equals("success")){
                //实现页面跳转
                //员工管理界面
                Intent intent=new Intent(UpdateStaffRes.this, StaffActivity.class);
                //设置当前界面为top
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                super.handleMessage(msg);
            }else{
                //员工修改页面
                Intent intent=new Intent(UpdateStaffRes.this,UpdateStaff.class);
                intent.putExtra("user",mUser);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                super.handleMessage(msg);
            }
        }
    };
}
