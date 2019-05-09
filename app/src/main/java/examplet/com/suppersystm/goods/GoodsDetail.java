package examplet.com.suppersystm.goods;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import examplet.com.suppersystm.R;
import examplet.com.suppersystm.bean.Goods;
import examplet.com.suppersystm.manager.ActivityCollector;
import examplet.com.suppersystm.manager.BaseActivity;

public class GoodsDetail extends BaseActivity implements View.OnClickListener{
    //标题栏
    private ImageView mImgvTitleBack;
    private TextView mTitleLeft;
    private TextView mTitleCenter;
    private ImageView mImgvTitleAdd;

    private TextView mName;
    private TextView mCode;
    private TextView mStock;
    private TextView mPrice;
    private TextView mBid;
    private TextView mSupplierId;
    private TextView mShelfId;

    private Goods mGoods;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        //标题不符
        mTitleCenter = (TextView) findViewById(R.id.title_center);
        mTitleCenter.setText("商品详情");
        //返回键及文字部分隐藏
        mImgvTitleBack = (ImageView) findViewById(R.id.imgv_title_back);
//        mImgvTitleBack.setVisibility(View.GONE);
        mImgvTitleBack.setOnClickListener(this);
        mImgvTitleAdd = (ImageView) findViewById(R.id.imgv_title_add);
        Glide.with(this).load(R.drawable.edit1).into(mImgvTitleAdd);
        mImgvTitleAdd.setOnClickListener(this);

        mName = (TextView) findViewById(R.id.name);
        mCode = (TextView) findViewById(R.id.code);
        mStock = (TextView) findViewById(R.id.stock);
        mPrice = (TextView) findViewById(R.id.price);
        mBid = (TextView) findViewById(R.id.bid);
        mSupplierId = (TextView) findViewById(R.id.supplierId);
        mShelfId = (TextView) findViewById(R.id.shelfId);

        Bundle bundle=getIntent().getExtras();
        mGoods=(Goods) bundle.get("goodsDetail");


        mName.setText(mGoods.getName());
        mCode.setText(mGoods.getCode());
        mStock.setText(mGoods.getStock());
        mPrice.setText(mGoods.getPrice());
        mBid.setText(mGoods.getBid());
        mSupplierId.setText(mGoods.getSupplierId());
        mShelfId.setText(mGoods.getShelfId());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgv_title_back:
                finish();
                break;
            case R.id.imgv_title_add:
                Intent intent=new Intent(GoodsDetail.this,EditGoods.class);
                intent.putExtra("goodsDetail",mGoods);

                startActivity(intent);
//                Toast.makeText(GoodsDetail.this,"编辑商品",Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
