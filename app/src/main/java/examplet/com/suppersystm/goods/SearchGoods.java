package examplet.com.suppersystm.goods;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import examplet.com.suppersystm.R;
import examplet.com.suppersystm.bean.Goods;
import examplet.com.suppersystm.manager.BaseActivity;
import examplet.com.suppersystm.manager.ListViewForScrollView;

public class SearchGoods extends BaseActivity implements View.OnClickListener{
    private ImageView mBackSearchGoods;
    private EditText mSearchWriteGoods;
//    private Button mBtnSearchGoods;
    private TextView mBtnSearchGoods;
    private ListView mGoodsSearchList;
    private ListViewForScrollView mListView;
    private TextView mSearchGoodsTip;
    private TextView mSearchClear;

    private SaveGoodsHelper saveGoodsHelper;
    private RecordsGoodsHelper recordsGoodsHelper;
    SQLiteDatabase db_goods,db_records;
    Cursor cursor;
    SimpleCursorAdapter adapter;
    private List<Goods> mGoods=new ArrayList<Goods>();
    String searchString;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_goods);
        initView();
        initData();
        initListener();

    }
    private void initData(){
        saveGoodsHelper=new SaveGoodsHelper(this,"MyGoods.db",null,2);
        recordsGoodsHelper=new RecordsGoodsHelper(this);
        initializeData();
        // TODO:尝试从保存查询纪录的数据库中获取历史纪录并显示ery()
       cursor=recordsGoodsHelper.getReadableDatabase().query("recordsGoods",null,null,null,null,null,null);
        System.out.println("搜索记录的数据表"+cursor.getCount());


        adapter=new SimpleCursorAdapter(this,android.R.layout.simple_list_item_2,cursor
                ,new String[]{"name","code"},new int[]{android.R.id.text1, android.R.id.text2}
                , CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
             mListView.setAdapter(adapter);
    }
    /**
     * 初始化数据
     */
    private void initializeData() {
//        deleteData();
        db_goods = saveGoodsHelper.getWritableDatabase();
        cursor=db_goods.query("goods",null,null,null,null,null,null);
        System.out.println("goods结果"+cursor.getCount());
//        cursor.close();
    }
    /**
     * 避免重复初始化数据
     */
    private void deleteData() {
        db_goods = saveGoodsHelper.getWritableDatabase();
        db_goods.execSQL("delete from goods");
        db_goods.close();
    }
    public void refrsh(){
      mGoods.clear();
    }
    /**
     * 初始化事件监听
     */
    private void initListener() {
        /**
         * 清除历史纪录
         */
        mSearchClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteRecords();
            }
        });
        /*
        * 搜索按钮保存搜索纪录，隐藏软键盘
            */
        mBtnSearchGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //隐藏键盘
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                //保存搜索记录
                insertRecords(mSearchWriteGoods.getText().toString().trim());
                refrsh();
                searchString = mSearchWriteGoods.getText().toString();
                queryData(searchString);
                if (cursor.getCount()==0){
                    Toast.makeText(SearchGoods.this,"无商品记录！",Toast.LENGTH_SHORT).show();
                }else {

                    refreshListView();
                }
            }
        });
        /**
         * EditText对键盘搜索按钮的监听，保存搜索纪录，隐藏软件盘
         */
        // TODO:搜索及保存搜索纪录
        mSearchWriteGoods.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {

                if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    //隐藏键盘
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    //保存搜索记录
                    insertRecords(mSearchWriteGoods.getText().toString().trim());
                }
                return false;
            }
        });
        /**
         * EditText搜索框对输入值变化的监听，实时搜索
         */
        // 使用TextWatcher实现对实时搜索
        mSearchWriteGoods.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (mSearchWriteGoods.getText().toString().equals("")) {
                    mSearchGoodsTip.setText("搜索历史");
                    mSearchClear.setVisibility(View.VISIBLE);
                    cursor = recordsGoodsHelper.getReadableDatabase().query("recordsGoods",null,null,null,null,null,"_id desc");
                    refreshListView();
                } else {
                    mSearchGoodsTip.setText("搜索结果");
                    mSearchClear.setVisibility(View.GONE);
//                    searchString = mSearchWriteGoods.getText().toString();
//                    queryData(searchString);
                }
            }
        });
        /**
         * ListView的item点击事件
         */
        // TODO: 2017/8/10 5、listview的点击 做你自己的业务逻辑 保存搜索纪录
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(mSearchGoodsTip.getText().equals("搜索历史")){
                    String name = ((TextView) view.findViewById(android.R.id.text1)).getText().toString();
                    mSearchWriteGoods.setText(name);
                }else {
                    Goods goodsDetail = new Goods();
                    goodsDetail.setId(mGoods.get(position).getId());
                    goodsDetail.setName(mGoods.get(position).getName());
                    goodsDetail.setCode(mGoods.get(position).getCode());
                    goodsDetail.setStock(mGoods.get(position).getStock());
                    goodsDetail.setBid(mGoods.get(position).getBid());
                    goodsDetail.setPrice(mGoods.get(position).getPrice());
                    goodsDetail.setSupplierId(mGoods.get(position).getSupplierId());
                    goodsDetail.setShelfId(mGoods.get(position).getShelfId());

                    Intent intent = new Intent(SearchGoods.this, GoodsDetail.class);
                    intent.putExtra("goodsDetail", goodsDetail);
                    startActivity(intent);
                    System.out.println("goodsDetail" + goodsDetail);
                }
//
//                String name = ((TextView) view.findViewById(android.R.id.text1)).getText().toString();
//                String code= ((TextView) view.findViewById(android.R.id.text2)).getText().toString();
////                Log.e("Skylark ", username + "---" + password);
////                Toast.makeText(SearchGoods.this,"this is"+mGoods.get(position).getId() +"----"+mGoods.get(position).getName(),Toast.LENGTH_SHORT).show();
//                Toast.makeText(SearchGoods.this,"this is"+name+"----"+code,Toast.LENGTH_SHORT).show();

            }
        });
    }
    /**
     * 保存搜索纪录
     */
    private void insertRecords(String username) {
        if (!hasDataRecords(username)) {
            db_records = recordsGoodsHelper.getWritableDatabase();
            db_records.execSQL("insert into recordsGoods values(null,?,?,?,?,?,?,?)", new String[]{username, ""});
            db_records.close();
        }
    }
    /**
     * 检查是否已经存在此搜索纪录
     *
     * @param records
     * @return
     */
    private boolean hasDataRecords(String records) {

        cursor = recordsGoodsHelper.getReadableDatabase()
                //记录查询条件
                .rawQuery("select _id from recordsGoods where _id= ? or code=?"
                        , new String[]{records});

        return cursor.moveToNext();
    }

    /**
     * 搜索数据库中的数据
     *
     * @param searchData
     */
    private void queryData(String searchData) {
        cursor =saveGoodsHelper .getReadableDatabase()
                .rawQuery("select * from goods where code like '%" + searchData + "%' or _id like '%" + searchData + "%'", null);
        System.out.println(cursor.getCount());
        if (cursor.moveToFirst()){
            do {
                Goods goods = new Goods();
                goods.setId(cursor.getString(cursor.getColumnIndex("_id")));
                goods.setName(cursor.getString(cursor.getColumnIndex("name")));
                goods.setCode(cursor.getString(cursor.getColumnIndex("code")));
                goods.setStock(cursor.getString(cursor.getColumnIndex("stock")));
                goods.setBid(cursor.getString(cursor.getColumnIndex("bid")));
                goods.setPrice(cursor.getString(cursor.getColumnIndex("price")));
                goods.setSupplierId(cursor.getString(cursor.getColumnIndex("supplierId")));
                goods.setShelfId(cursor.getString(cursor.getColumnIndex("shelfId")));
                mGoods.add(goods);
//                cursor.close();

            }while (cursor.moveToNext());
        }else{
            System.out.println(cursor+"------"+"SearchGoods");
        }
//        cursor.close();

//            refreshListView();
    }

    /**
     * 删除历史纪录
     */
    private void deleteRecords() {
        db_records =recordsGoodsHelper.getWritableDatabase();
        db_records.execSQL("delete from recordsGoods");

        cursor = recordsGoodsHelper.getReadableDatabase().rawQuery("select * from recordsGoods", null);
        if (mSearchWriteGoods.getText().toString().equals("")) {
            refreshListView();
        }
    }

    /**
     * 刷新listview
     */
    private void refreshListView() {
        adapter.notifyDataSetChanged();
        adapter.swapCursor(cursor);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db_records != null) {
            db_records.close();
        }
        if ( db_goods != null) {
            db_goods.close();
        }
    }
    private void initView(){
        mBackSearchGoods = (ImageView) findViewById(R.id.back_search_goods);
        mBackSearchGoods.setOnClickListener(this);
        mSearchWriteGoods = (EditText) findViewById(R.id.search_write_goods);
//        mBtnSearchGoods = (Button) findViewById(R.id.btn_search_goods);
        mBtnSearchGoods = (TextView) findViewById(R.id.btn_search_goods);
        mBtnSearchGoods.setOnClickListener(this);

//        mGoodsSearchList = (ListViewForScrollView) findViewById(R.id.goods_search_list);
        mSearchGoodsTip = (TextView) findViewById(R.id.search_goods_tip);
        mSearchClear = (TextView) findViewById(R.id.search_clear);
        mListView=(ListViewForScrollView) findViewById(R.id.goods_search_list);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_search_goods:
                finish();
                break;
            case R.id.btn_search_goods:
                Toast.makeText(SearchGoods.this,"搜索商品", Toast.LENGTH_SHORT).show();
//                break;
            default:
                break;
        }

    }
}
