package examplet.com.suppersystm.goods;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.PrivateKey;

import examplet.com.suppersystm.R;
import examplet.com.suppersystm.bean.Goods;
import examplet.com.suppersystm.http.HttpUtils;
import examplet.com.suppersystm.manager.BaseActivity;
import examplet.com.suppersystm.manager.ClearEditText;

public class AddGoods extends BaseActivity implements View.OnClickListener{
    private ImageView mImgvTitleBack;
    private TextView mTitleLeft;
    private TextView mTitleCenter;
    private ImageView mImgvTitleAdd;
    //商品详情
    private ClearEditText mAddGoodsName;
    private ClearEditText mAddGoodsCode;
    private ClearEditText mAddGoodsStock;
    private ClearEditText mAddGoodsPrice;
    private ClearEditText mAddGoodsBid;
    private ClearEditText mAddGoodsShelfId;
    private ClearEditText mAddGoodsSupplierId;


    private Button mAddGoodsSave;
    private Button mAddGoodsCancel;
    //商品添加
    private String code;
    private  String name;
    private  String stock;
    private  String bid;
    private  String price;
    private  String supplierId;
    private  String shelfId;

    private static final int SUCCESS=1;
    private static final int ERROR=2;
    private static final int FAILURE=0;
    private String result1,result2;
    private Goods mGoods;
    private String address= "http://10.0.2.2:8080/MGraduation3/AddGoodsActionApp";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goods);
        initView();


    }

    private void initView(){
        mTitleCenter = (TextView) findViewById(R.id.title_center);
        mTitleCenter.setText("商品上架");
        mImgvTitleAdd = (ImageView) findViewById(R.id.imgv_title_add);
        mImgvTitleAdd.setVisibility(View.GONE);
        mImgvTitleBack = (ImageView) findViewById(R.id.imgv_title_back);
        mImgvTitleBack.setOnClickListener(this);

        //商品详细信息添加
        mAddGoodsName = (ClearEditText) findViewById(R.id.add_goods_name);
        mAddGoodsCode = (ClearEditText) findViewById(R.id.add_goods_code);
        mAddGoodsStock = (ClearEditText) findViewById(R.id.add_goods_stock);
        mAddGoodsPrice = (ClearEditText) findViewById(R.id.add_goods_price);
        mAddGoodsBid = (ClearEditText) findViewById(R.id.add_goods_bid);
        mAddGoodsShelfId = (ClearEditText) findViewById(R.id.add_goods_shelfId);
        mAddGoodsSupplierId = (ClearEditText) findViewById(R.id.add_goods_supplierId);
        mAddGoodsSave = (Button) findViewById(R.id.add_goods_save);
        mAddGoodsSave.setOnClickListener(this);
        mAddGoodsCancel = (Button) findViewById(R.id.add_goods_cancel);
        mAddGoodsCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgv_title_back:
                 finish();
                break;
            case R.id.add_goods_cancel:
                //全部清空
                initCancel();
                break;
            case R.id.add_goods_save:
//                initHttp();
                addGoods();
                //保存信息

                break;
        }
    }
    //取消时间
    public void initCancel(){
        mAddGoodsName.setText("");
        mAddGoodsCode.setText("");
        mAddGoodsStock.setText("");
        mAddGoodsPrice.setText("");
        mAddGoodsBid.setText("");
        mAddGoodsShelfId.setText("");
        mAddGoodsSupplierId.setText("");
    }
    private void addGoods(){
        code=mAddGoodsCode.getText().toString().trim();
        name=mAddGoodsName.getText().toString().trim();
        stock=mAddGoodsStock.getText().toString().trim();
        price=mAddGoodsPrice.getText().toString().trim();
        bid=mAddGoodsBid.getText().toString().trim();
        supplierId=mAddGoodsSupplierId.getText().toString().trim();
        shelfId=mAddGoodsShelfId.getText().toString().trim();

        if (TextUtils.isEmpty(code)||TextUtils.isEmpty(name)||
                TextUtils.isEmpty(stock)||TextUtils.isEmpty(price)||
                TextUtils.isEmpty(bid)||TextUtils.isEmpty(supplierId)||
                TextUtils.isEmpty(shelfId)){
            Toast.makeText(AddGoods.this,"商品信息不能为空~",Toast.LENGTH_SHORT).show();
        }else {
        initHttp();
        }
    }
//    商品添加线程
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
                        String data="code="+ URLEncoder.encode(code,"UTF-8")+
                                "&name="+URLEncoder.encode(name,"UTF-8")+
                                "&stock="+URLEncoder.encode(stock,"UTF-8")+
                                "&bid="+URLEncoder.encode(bid,"UTF-8")+
                                "&price="+URLEncoder.encode(price,"UTF-8")+
                                "&supplierId="+URLEncoder.encode(supplierId,"UTF-8")+
                                "&shelfId="+URLEncoder.encode(shelfId,"UTF-8");
                        //添加请求头
//                        connection.setRequestProperty("Content-Length",data.length()+"");
//                        connection.setRequestProperty("charset","UTF-8");
//                        connection.setRequestProperty("User-Agent","Mozilla/4.0(compatible;MSIE 5.0;" +
//                                "Wiindow NT;DigExt)");
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
                            System.out.println("AddGoods SUCCESS"+result);
                        }else {
                            Message msg=new Message();
                            msg.what=ERROR;
                            handler.sendMessage(msg);
                            System.out.println("AddGoods ERROR"+connection.getResponseCode()+msg);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        Message msg=new Message();
                        msg.what=FAILURE;
                        handler.sendMessage(msg);
                        System.out.println("AddGoods FALURE" + msg);
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

                    Intent intent=new Intent(AddGoods.this,AddGoodsRes.class);
                    intent.putExtra("addGoodsRes1",result1);
                    intent.putExtra("addGoodsRes2",result2);

                    System.out.println("---addGoodsRes---"+result1+"----"+result2);
                    startActivity(intent);
                    Toast.makeText(AddGoods.this,"save",Toast.LENGTH_SHORT).show();

                    break;
                case ERROR:
                    Toast.makeText(AddGoods.this,"网络开小差",Toast.LENGTH_SHORT).show();
                    break;
                case FAILURE:
                    Toast.makeText(AddGoods.this,"网络开小差",Toast.LENGTH_SHORT).show();
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
                Log.d(result1,"AddGoods 添加后的结果值"+result1+"----2--"+result2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
//    private void la() {
//        try {
//           final String code = mAddGoodsCode.getText().toString().trim();
//            final String name = mAddGoodsName.getText().toString().trim();
//            System.out.println(code+"-----"+name);
//
//            stock = mAddGoodsStock.getText().toString().trim();
//             price = mAddGoodsPrice.getText().toString().trim();
//             bid = mAddGoodsBid.getText().toString().trim();
//             supplierId = mAddGoodsSupplierId.getText().toString().trim();
//              shelfId = mAddGoodsShelfId.getText().toString().trim();
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
////                    String result=init(code,name);
////                    String result=init(code,name,stock,price,bid,supplierId,shelfId);
//                    Bundle bundle=new Bundle();
//                    bundle.putString("result",result);
//                    Message msg=new Message();
//                    msg.setData(bundle);
//                    msg.what=SUCCESS;
//                    msg.obj=result;
//                    handler.sendMessage(msg);
//                }
//            }).start();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//    private static String init(String code,String name,String stock,
//                               String price,String bid,String supplierId,
//                               String shelfId){
//
////        public static String init(String code,String name){
//        String result="";
//         String address= "http://10.0.2.2:8080/MGraduation3/AddGoodsAction";
//
//        try {
//            URL url = new URL(address);//初始化URL
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("POST");
//            connection.setDoInput(true);
//            connection.setDoOutput(true);
//            //超时信息
//            connection.setReadTimeout(5000);
//            connection.setConnectTimeout(5000);
//
//            //post方式不能设置缓存，需手动设置为false
//            connection.setUseCaches(false);
//            String data="code="+ URLEncoder.encode(code,"UTF-8")+
//                                "&name="+URLEncoder.encode(name,"UTF-8")+
//                                "&stock="+URLEncoder.encode(stock,"UTF-8")+
//                                "&bid="+URLEncoder.encode(bid,"UTF-8")+
//                                "&price="+URLEncoder.encode(price,"UTF-8")+
//                                "&supplierId="+URLEncoder.encode(supplierId,"UTF-8")+
//                                "&shelfId="+URLEncoder.encode(shelfId,"UTF-8");
////            String data="code"+code+"&name"+name;
//            System.out.println(data+"---name--"+name+"---code---"+code);
//
//
//            OutputStream outputStream=connection.getOutputStream();
//            outputStream.write(data.toString().trim().getBytes());
//            System.out.println("data____"+data.toString().getBytes());
//            outputStream.flush();
//            outputStream.close();
//            if (connection.getResponseCode() ==200){
//                // 获取响应的输入流对象
//                InputStream is = connection.getInputStream();
//                // 创建字节输出流对象
//                ByteArrayOutputStream message = new ByteArrayOutputStream();
//                // 定义读取的长度
//                int len = 0;
//                // 定义缓冲区
//                byte buffer[] = new byte[1024];
//                // 按照缓冲区的大小，循环读取
//                while ((len = is.read(buffer)) != -1) {
//                    // 根据读取的长度写入到os对象中
//                    message.write(buffer, 0, len);
//                }
//                // 释放资源
//                is.close();
//                message.close();
//                // 返回字符串
//                result = new String(message.toByteArray());
//                //return result;
//            }else{
//                System.out.println(connection.getResponseCode());
//                result= "1";
//                //没有网页等问题
//            }
//
//        }catch (MalformedURLException e){
//            e.printStackTrace();
//            result="2";
//        }catch (IOException e){
//            e.printStackTrace();
//            result="3";
//        }
//        Log.d("res",result);
//        return result;
//    }
