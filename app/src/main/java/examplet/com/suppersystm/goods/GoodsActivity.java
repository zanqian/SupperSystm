package examplet.com.suppersystm.goods;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import examplet.com.suppersystm.R;
import examplet.com.suppersystm.bean.Goods;
import examplet.com.suppersystm.http.HttpUtils;
import examplet.com.suppersystm.manager.BaseActivity;

public class GoodsActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout searchBg;

    //标题栏
    private ImageView mImgvTitleBack;
    private TextView mTitleLeft;
    private TextView mTitleCenter;
    private ImageView mImgvTitleAdd;
    //goods
    private static  final int SUCCESS=1;
    private static final int ERROR = 2;
    private static final int FAILURE = 0;
    private ListView listGoods;
    private String  address="http://10.0.2.2:8080/MGraduation3/QueryGoodsActionApp";
    private List<Goods> mGoods=new ArrayList<Goods>();

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private SaveGoodsHelper saveGoodsHelper;
    SQLiteDatabase db_search_goods;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        saveGoodsHelper=new SaveGoodsHelper(this,"MyGoods.db",null,2);
        init();
        initView();
        GoodsListView();




//获取传来的sessionId
//        Bundle bundle=getIntent().getExtras();
//        sessionId=(String) bundle.get("sessionId");
//        System.out.println("GoodsActivity"+sessionId);

    }
    private void init(){
        //子线程更新
        new Thread(){
            public void run(){
                int statusCode;
                HttpURLConnection connection=null;
                try{
//
                    URL url=new URL(address);
                    connection=(HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    //设置连接超时，读取超时毫秒数
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    statusCode=connection.getResponseCode();
                    if (statusCode==200){
                        //下面是对获取的输入流进行读取
                        InputStream in=connection.getInputStream();
                        String result= HttpUtils.readMyInputStream(in);
//                        System.out.println("goodsre"+result);
                        Message msg=new Message();
                        msg.what=SUCCESS;
                        msg.obj=result;
                        handler.sendMessage(msg);
                        System.out.println("SUCCESS"+result);
                    }else{
                        System.out.println(statusCode);
                        Message msg=new Message();
                        msg.what=ERROR;
                        handler.sendMessage(msg);
                        System.out.println("Error"+msg);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    Message msg=new Message();
                    msg.what=FAILURE;
                    handler.sendMessage(msg);
                    System.out.println("FAILURE"+msg);
                }
                finally {
                    if (connection!=null){
                        //关闭HTTP连接
//                        connection.disconnect();
                    }
                }
            }
        }.start();
    }
    private void initView(){
        //搜索框
        searchBg=(LinearLayout) findViewById(R.id.goods_search_no);
        searchBg.setOnClickListener(this);
//        mBtnSearchHomeFragment = (Button) findViewById(R.id.btn_search_home_fragment);
//        mBtnSearchHomeFragment.setOnClickListener(this);
        mTitleCenter = (TextView) findViewById(R.id.title_center);
        mTitleCenter.setText("商品管理");
        //返回键及文字部分隐藏
        mImgvTitleBack = (ImageView) findViewById(R.id.imgv_title_back);
//        mImgvTitleBack.setVisibility(View.GONE);
        mImgvTitleBack.setOnClickListener(this);
//        mTitleLeft = (TextView) findViewById(R.id.title_left);
//        mTitleLeft.setVisibility(View.GONE);
        mImgvTitleAdd = (ImageView) findViewById(R.id.imgv_title_add);
//        mImgvTitleAdd.setVisibility(View.GONE);
        mImgvTitleAdd.setOnClickListener(this);
        listGoods=(ListView) findViewById(R.id.goods_item);

    }

    private void GoodsListView(){
        listGoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(position);
                Goods goodsDetail=new Goods();
                goodsDetail.setId(mGoods.get(position).getId());
                goodsDetail.setName(mGoods.get(position).getName());
                goodsDetail.setCode(mGoods.get(position).getCode());
                goodsDetail.setStock(mGoods.get(position).getStock());
                goodsDetail.setBid(mGoods.get(position).getBid());
                goodsDetail.setPrice(mGoods.get(position).getPrice());
                goodsDetail.setSupplierId(mGoods.get(position).getSupplierId());
                goodsDetail.setShelfId(mGoods.get(position).getShelfId());

                Intent intent=new Intent(GoodsActivity.this,GoodsDetail.class);
                intent.putExtra("goodsDetail",goodsDetail);
                startActivity(intent);
                System.out.println("goodsDetail"+goodsDetail);
                Toast.makeText(GoodsActivity.this,"this is "+mGoods.get(position).getName(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     * 避免重复初始化数据
     */
    private void deleteData() {
        db_search_goods = saveGoodsHelper.getWritableDatabase();
        db_search_goods .execSQL("delete from goods");
        db_search_goods .close();
    }
    /**
     * 初始化数据
     */
//    private void initializeData(){
//        deleteData();
//        db_search_goods=saveGoodsHelper.getWritableDatabase();
//        Cursor cursor=db_search_goods.query("goods",null,null,null,null,null,null);
//        Log.e("Login", "-------cursor------" + cursor);
//        if (cursor.getCount()==0){
//            for (int i=0;i<)
//            ContentValues values=new ContentValues();
//            values.put("id",mGoods.);
//        }
//
//    }
//    主线程创建消息处理器
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case SUCCESS:
                    JSONAnalysis(msg.obj.toString());
                    break;
                case ERROR:
                    Toast.makeText(GoodsActivity.this,"获取数据失败",Toast.LENGTH_SHORT).show();
                    break;
                case FAILURE:
                    Toast.makeText(GoodsActivity.this,"获取数据失败",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };
//    json数据解析
    protected void JSONAnalysis(String jsonData){
        db_search_goods=saveGoodsHelper.getWritableDatabase();

        if (jsonData != null){
            try{
                //将字符串转换成jsonObject对象
//                JSONObject jsonObject = new JSONObject(jsonData);
                // 通过标识(rs)，获取JSON数组
//                JSONArray jsonArray = jsonObject.getJSONArray("rs");
                JSONArray jsonArray=new JSONArray(jsonData);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject temp = (JSONObject) jsonArray.get(i);
//                    Log.d("tem",temp.getString("name"));
                    Goods goods = new Goods();
                    goods.setId(temp.getString("id"));
                    goods.setName(temp.getString("name"));
                    goods.setCode(temp.getString("code"));
                    goods.setStock(temp.getString("stock"));
                    goods.setBid(temp.getString("bid"));
                    goods.setPrice(temp.getString("price"));
                    goods.setSupplierId(temp.getString("supplierId"));
                    goods.setShelfId(temp.getString("shelfId"));
                    //这个地方可以获取到值但是适配器那位0
                    mGoods.add(goods);
                    AddGoodsSQL(goods);
                }
                listGoods.setAdapter(new ItemGoodsAdapter());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void AddGoodsSQL(Goods goods){

        String id=goods.getId();
//        Cursor cursor=db_search_goods.query("goods",null,"code=?",new String[]{code},null,null,null);
        Cursor cursor=db_search_goods.query("goods",null,"_id=?",new String[]{id},null,null,null);
//        Log.e("Login", "-------cursor------" + cursor);
        if (cursor.getCount()==0){
//            Log.e("进来了","------"+goods.getId());
            ContentValues values=new ContentValues();
            values.put("_id",goods.getId());
            values.put("name",goods.getName());
            values.put("code",goods.getCode());
            values.put("stock",goods.getStock());
            values.put("bid",goods.getBid());
            values.put("price",goods.getPrice());
            values.put("supplierId",goods.getSupplierId());
            values.put("shelfId",goods.getShelfId());
            db_search_goods.insert("goods",null,values);
            values.clear();
        }
        else{
            while (cursor.moveToNext()) {
//                Integer id1 = cursor.getInt(cursor.getColumnIndex("_id"));
//                Log.e("Login", "-------id------" + id);
//                            if (mAccount.getId() != id) {
//                                ContentValues values = new ContentValues();
//                                values.put("id", mAccount.getId());
//                                values.put("username", mAccount.getUsername());
//                                values.put("password", mAccount.getPassword());
//                                values.put("state", mAccount.getState());
//                                values.put("type", mAccount.getType());
//                                values.put("name", mAccount.getName());
//                                db.insert("saveuser",null,values);
//                                Log.e("Login", "-------values------" + values);
//                            }
            }}

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgv_title_back:

                this.finish();
//                ActivityCollector.finishAll();
                break;
            case R.id.imgv_title_add:
                Toast.makeText(this,"请在这里跳转至添加商品",Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(this,AddGoods.class);
                startActivity(intent);
                break;
            case R.id.goods_search_no:
                Toast.makeText(this,"点击sous",Toast.LENGTH_SHORT).show();
                Intent intent1=new Intent(this,SearchGoods.class);
                startActivity(intent1);
        }
    }

    //商品适配器
    public class ItemGoodsAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            Log.d("AAA", ""+mGoods.size());
            return mGoods.size();
        }

        @Override
        public Object getItem(int position) {
            return mGoods.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=View.inflate(GoodsActivity.this,R.layout.item_list_goods,null);
            TextView mGoodsName = view.findViewById(R.id.goods_name);
            TextView mGoodsPrice =  view.findViewById(R.id.goods_price);
            TextView mGoodsStock= view.findViewById(R.id.goods_stock);
            Goods goods=mGoods.get(position);
            mGoodsName.setText(goods.getName());
            mGoodsPrice.setText(goods.getPrice());
            mGoodsStock.setText(goods.getStock());
            return view;
        }
    }
}
