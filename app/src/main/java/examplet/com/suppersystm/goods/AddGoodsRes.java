package examplet.com.suppersystm.goods;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import examplet.com.suppersystm.R;
import examplet.com.suppersystm.bean.Goods;
import examplet.com.suppersystm.fragment.Welcome;
import examplet.com.suppersystm.login.LoginActivity;
import examplet.com.suppersystm.manager.BaseActivity;

public class AddGoodsRes extends BaseActivity{
    private TextView mText;
    private  String result1;
    private  String result2;
    //标题栏
    private ImageView mImgvTitleBack;
    private TextView mTitleLeft;
    private TextView mTitleCenter;
    private ImageView mImgvTitleAdd;
    //添加结果展示
    private ImageView mAddGoodsResImg;
    private TextView mAddGoodsResText;
    private TextView mAddGoodsNextActivity;
    private Goods mGoods;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goods_res);

        Bundle bundle=getIntent().getExtras();
        result1= (String) bundle.get("addGoodsRes1");
        result2=(String) bundle.get("addGoodsRes2");

        handler.sendEmptyMessageDelayed(0,4000);
        initView();
    }
    private void initView(){
        //标题
        mTitleCenter = (TextView) findViewById(R.id.title_center);
        mTitleCenter.setText("商品添加结果");
        //返回键及文字部分
        mImgvTitleBack = (ImageView) findViewById(R.id.imgv_title_back);
        mImgvTitleBack.setVisibility(View.INVISIBLE);
        mTitleLeft = (TextView) findViewById(R.id.title_left);
        mTitleLeft.setVisibility(View.INVISIBLE);
        mImgvTitleAdd = (ImageView) findViewById(R.id.imgv_title_add);
        mImgvTitleAdd.setVisibility(View.INVISIBLE);

        mAddGoodsResImg = (ImageView) findViewById(R.id.add_goods_res_img);
        mAddGoodsResText = (TextView) findViewById(R.id.add_goods_res_text);
        mAddGoodsNextActivity = (TextView) findViewById(R.id.add_goods_next_activity);
        mAddGoodsResText.setText(result2);
        if (result1.equals("success")){
            //glide加载图片
            Glide.with(this).load(R.drawable.goodsuccess).into(mAddGoodsResImg);
            mAddGoodsNextActivity.setText("商品管理界面");
        }else{
            Glide.with(this).load(R.drawable.goodserror).into(mAddGoodsResImg);
            mAddGoodsNextActivity.setText("商品添加界面");
        }
    }
    //活动线程
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            if (result1.equals("success")){
                //实现页面跳转
//
                Intent intent=new Intent(AddGoodsRes.this, GoodsActivity.class);
                //设置当前界面为top
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                super.handleMessage(msg);
            }else{
                Intent intent=new Intent(AddGoodsRes.this,AddGoods.class);
                //设置当前界面为top
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
                finish();
                super.handleMessage(msg);
            }
        }
    };


}
