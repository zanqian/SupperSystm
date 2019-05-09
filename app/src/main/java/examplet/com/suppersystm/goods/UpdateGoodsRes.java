package examplet.com.suppersystm.goods;

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
import examplet.com.suppersystm.bean.Goods;

public class UpdateGoodsRes extends AppCompatActivity {
    private ImageView mUpdateGoodsResImg;
    private TextView mUpdateGoodsResText;
    private TextView mUpdateGoodsNextActivity;
    //标题栏
    private ImageView mImgvTitleBack;
    private TextView mTitleLeft;
    private TextView mTitleCenter;
    private ImageView mImgvTitleAdd;


    private  String result1;
    private  String result2;
    private Goods mGoods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_goods_res);
        Bundle bundle=getIntent().getExtras();
        result1= (String) bundle.get("addGoodsRes1");
        result2=(String) bundle.get("addGoodsRes2");
        mGoods=(Goods)bundle.get("addGoodsRes");
        handler.sendEmptyMessageDelayed(0,4000);
        initView();
    }

    private void initView() {
        mUpdateGoodsResImg = (ImageView) findViewById(R.id.update_goods_res_img);
        mUpdateGoodsResText = (TextView) findViewById(R.id.update_goods_res_text);
        mUpdateGoodsNextActivity = (TextView) findViewById(R.id.update_goods_next_activity);
        //标题
        mTitleCenter = (TextView) findViewById(R.id.title_center);
        mTitleCenter.setText("商品修改结果");
        //返回键及文字部分
        mImgvTitleBack = (ImageView) findViewById(R.id.imgv_title_back);
        mImgvTitleBack.setVisibility(View.INVISIBLE);
        mTitleLeft = (TextView) findViewById(R.id.title_left);
        mTitleLeft.setVisibility(View.INVISIBLE);
        mImgvTitleAdd = (ImageView) findViewById(R.id.imgv_title_add);
        mImgvTitleAdd.setVisibility(View.INVISIBLE);
        mUpdateGoodsResText.setText(result2);
        if (result1.equals("success")){
            //glide加载图片
            Glide.with(this).load(R.drawable.goodsuccess).into( mUpdateGoodsResImg);
            mUpdateGoodsNextActivity .setText("商品管理界面");
        }else{
            Glide.with(this).load(R.drawable.goodserror).into( mUpdateGoodsResImg);
            mUpdateGoodsNextActivity.setText("商品修改界面");
        }
    }
    //活动线程
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            if (result1.equals("success")){
                //实现页面跳转
//
                Intent intent=new Intent(UpdateGoodsRes.this, GoodsActivity.class);
                //设置当前界面为top
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                super.handleMessage(msg);
            }else{
                Intent intent=new Intent(UpdateGoodsRes.this,EditGoods.class);
                //设置当前界面为top
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("goodsDetail",mGoods);
                startActivity(intent);
                finish();
                super.handleMessage(msg);
            }
        }
    };
}
