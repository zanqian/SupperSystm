package examplet.com.suppersystm.goods;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import examplet.com.suppersystm.R;
import examplet.com.suppersystm.bean.Goods;
import examplet.com.suppersystm.http.HttpUtils;
import examplet.com.suppersystm.manager.BaseActivity;
import examplet.com.suppersystm.manager.ClearEditText;

public class EditGoods extends BaseActivity implements View.OnClickListener{
    //标题栏
    private ImageView mImgvTitleBack;
    private TextView mTitleLeft;
    private TextView mTitleCenter;
    private ImageView mImgvTitleAdd;
    //商品详情
    private ClearEditText mEditGoodsName;
    private TextView mEditGoodsCode;
    private ClearEditText mEditGoodsStock;
    private ClearEditText mEditGoodsPrice;
    private ClearEditText mEditGoodsBid;
    private ClearEditText mEditGoodsShelfId;
    private ClearEditText mEditGoodsSupplierId;
    private Button mEditGoodsSave;
//    private Button mEditGoodsCancel;

    private Goods mGoods;
    //商品修改
    private String id;
    private String code;
    private  String name;
    private  String stock;
    private  String bid;
    private  String price;
    private  String supplierId;
    private  String shelfId;
    //商品修改信息
    private static final int SUCCESS=1;
    private static final int ERROR=2;
    private static final int FAILURE=0;
    private String result1,result2;
    private String address= "http://10.0.2.2:8080/MGraduation3/UpdateGoodsAjaxActionApp";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_goods);
        initView();
    }
    private void initView(){
        //标题
        mTitleCenter = (TextView) findViewById(R.id.title_center);
        mTitleCenter.setText("编辑商品");
        mImgvTitleAdd = (ImageView) findViewById(R.id.imgv_title_add);
        mImgvTitleAdd.setVisibility(View.GONE);
        mImgvTitleBack = (ImageView) findViewById(R.id.imgv_title_back);
        mImgvTitleBack.setOnClickListener(this);
        //商品详情
        mEditGoodsName = (ClearEditText) findViewById(R.id.edit_goods_name);
        mEditGoodsCode = (TextView) findViewById(R.id.edit_goods_code);
        mEditGoodsStock = (ClearEditText) findViewById(R.id.edit_goods_stock);
        mEditGoodsPrice = (ClearEditText) findViewById(R.id.edit_goods_price);
        mEditGoodsBid = (ClearEditText) findViewById(R.id.edit_goods_bid);
        mEditGoodsShelfId = (ClearEditText) findViewById(R.id.edit_goods_shelfId);
        mEditGoodsSupplierId = (ClearEditText) findViewById(R.id.edit_goods_supplierId);
        mEditGoodsSave = (Button) findViewById(R.id.edit_goods_save);
        mEditGoodsSave.setOnClickListener(this);
//        mEditGoodsSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                System.out.println("什么情况");
//                Toast.makeText(EditGoods.this,"aaa确定",Toast.LENGTH_SHORT).show();
//
//            }
//        });
//        mEditGoodsCancel = (Button) findViewById(R.id.edit_goods_cancel);
//        mEditGoodsCancel.setOnClickListener(this);
        //接收传值
        Bundle bundle=getIntent().getExtras();
        mGoods=(Goods) bundle.get("goodsDetail");
        //将接收的值传入text
        mEditGoodsName.setText(mGoods.getName());
        mEditGoodsCode.setText(mGoods.getCode());
        mEditGoodsStock.setText(mGoods.getStock());
        mEditGoodsBid.setText(mGoods.getBid());
        mEditGoodsPrice.setText(mGoods.getPrice());
        mEditGoodsSupplierId.setText(mGoods.getSupplierId());
        mEditGoodsShelfId.setText(mGoods.getShelfId());

    }

    //修改商品
//    private void addGoods(){
//        id=mGoods.getId();
//        System.out.println("id" +id);
//        code=mEditGoodsCode.getText().toString().trim();
//        name=mEditGoodsName.getText().toString().trim();
//        stock=mEditGoodsStock.getText().toString().trim();
//        price=mEditGoodsPrice.getText().toString().trim();
//        bid=mEditGoodsBid.getText().toString().trim();
//        supplierId=mEditGoodsSupplierId.getText().toString().trim();
//        shelfId=mEditGoodsShelfId.getText().toString().trim();
//           initHttp();
//
//    }

    //判断是否为空
    private void isEmpty(){
        id=mGoods.getId();
        code=mEditGoodsCode.getText().toString().trim();
        name=mEditGoodsName.getText().toString().trim();
        stock=mEditGoodsStock.getText().toString().trim();
        price=mEditGoodsPrice.getText().toString().trim();
        bid=mEditGoodsBid.getText().toString().trim();
        supplierId=mEditGoodsSupplierId.getText().toString().trim();
        shelfId=mEditGoodsShelfId.getText().toString().trim();
        if (TextUtils.isEmpty(code)||TextUtils.isEmpty(name)||
                TextUtils.isEmpty(stock)||TextUtils.isEmpty(price)||
                TextUtils.isEmpty(bid)||TextUtils.isEmpty(supplierId)||
                TextUtils.isEmpty(shelfId)){
            Toast.makeText(EditGoods.this,"商品信息不能为空~",Toast.LENGTH_SHORT).show();
        }else {

           showAlterDialog();
        }
    }



    //    商品修改线程
    private void initHttp(){
        new Thread(){
            public void run(){
                HttpURLConnection connection=null;
                try {
                    URL url = new URL(address);//初始化URL
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");//请求方式
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    //超时信息
                    connection.setReadTimeout(5000);
                    connection.setConnectTimeout(5000);
                    //post方式不能设置缓存，需手动设置为false
                    connection.setUseCaches(false);
                    String data="id="+URLEncoder.encode(id,"UTF-8")+
                            "&code="+ URLEncoder.encode(code,"UTF-8")+
                            "&name="+URLEncoder.encode(name,"UTF-8")+
                            "&stock="+URLEncoder.encode(stock,"UTF-8")+
                            "&bid="+URLEncoder.encode(bid,"UTF-8")+
                            "&price="+URLEncoder.encode(price,"UTF-8")+
                            "&supplierId="+URLEncoder.encode(supplierId,"UTF-8")+
                            "&shelfId="+URLEncoder.encode(shelfId,"UTF-8");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    OutputStream outputStream=connection.getOutputStream();
                    outputStream.write(data.getBytes());
                    outputStream.flush();
                    outputStream.close();
//
                    System.out.println(connection.getResponseCode());
                    if (connection.getResponseCode() ==200){
                        InputStream inputStream=connection.getInputStream();
                        String result= HttpUtils.readMyInputStream(inputStream);
                        Message msg=new Message();
                        msg.what=SUCCESS;
                        msg.obj=result;
                        handler.sendMessage(msg);
                        System.out.println("EditGoods SUCCESS"+result);
                    }else {
                        Message msg=new Message();
                        msg.what=ERROR;
                        handler.sendMessage(msg);
                        System.out.println("EditGoods ERROR"+connection.getResponseCode()+msg);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Message msg=new Message();
                    msg.what=FAILURE;
                    handler.sendMessage(msg);
                    System.out.println("EditGoods FALURE" + msg);
                }finally {
                    if (connection!=null){
                        connection.disconnect();
                    }
                }
//                }
            }
        }.start();
    }
    //主线程消息处理器
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case SUCCESS:
                    JsonAnalysis(msg.obj.toString());
                    Toast.makeText(EditGoods.this,"确定",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(EditGoods.this,AddGoodsRes.class);
                    intent.putExtra("addGoodsRes",mGoods);
                    intent.putExtra("addGoodsRes1",result1);
                    intent.putExtra("addGoodsRes2",result2);
                    System.out.println("---EditGoodsRes--"+result1+"----"+result2);
                    startActivity(intent);
//
//                    Intent intent=new Intent(AddGoods.this,AddGoodsRes.class);
//                    intent.putExtra("addGoodsRes",result1);
//                    intent.putExtra("addGoodsRes2",result2);
//                    System.out.println("---addGoodsRes---"+result1+"----"+result2);
//                    startActivity(intent);
//                    Toast.makeText(AddGoods.this,"save",Toast.LENGTH_SHORT).show();

                    break;
                case ERROR:
                    Toast.makeText(EditGoods.this,"网络开小差",Toast.LENGTH_SHORT).show();
                    break;
                case FAILURE:
                    Toast.makeText(EditGoods.this,"网络开小差",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };
    //对获取的数据解析
    private void JsonAnalysis(String jsonData){
        if (jsonData != null){
            try{
                JSONObject jsonObject=new JSONObject(jsonData);
                result1=jsonObject.optString("result",null);
                result2=jsonObject.optString("msg",null);
                Log.d(result1,"EditGoods  修改后的结果值"+result1+"----2--"+result2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void showAlterDialog(){
        final AlertDialog.Builder alterDiaglog=new AlertDialog.Builder(EditGoods.this);
        alterDiaglog.setIcon(R.drawable.user_head);
        alterDiaglog.setTitle("确定修改商品信息吗？");
        alterDiaglog.setNeutralButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                initHttp();

            }
        });
        alterDiaglog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(EditGoods.this,"取消",Toast.LENGTH_SHORT).show();
            }
        });
    alterDiaglog.show();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgv_title_back:
                finish();
                break;
            case R.id.edit_goods_save:
//                showAlterDialog();
                    isEmpty();
//                Toast.makeText(EditGoods.this,"aaa确定",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
