package examplet.com.suppersystm.staff;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import examplet.com.suppersystm.R;
import examplet.com.suppersystm.bean.Goods;
import examplet.com.suppersystm.bean.User;
import examplet.com.suppersystm.goods.GoodsDetail;
import examplet.com.suppersystm.goods.SearchGoods;
import examplet.com.suppersystm.manager.BaseActivity;
import examplet.com.suppersystm.manager.ClearEditText;
import examplet.com.suppersystm.manager.ListViewForScrollView;

public class SearchStaff extends BaseActivity implements View.OnClickListener{
    private ImageView mBackSearchStaff;
    private ClearEditText mSearchWriteStaff;
    private TextView mBtnSearchStaff;
    private TextView mSearchStaffTip;
    private ListViewForScrollView mStaffSearchList;
    private TextView mStaffSearchClear;

    //存储
    private SaveUserHelper saveUserHelper;
    private RecordsUserHelper recordsUserHelper;
    SQLiteDatabase db_user,db_records;
    Cursor cursor;
    SimpleCursorAdapter adapter;
    private List<User> mUser=new ArrayList<User>();
    String searchString;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_staff);
        initView();
        initData();
        initListener();
    }
    private void initData() {
        saveUserHelper=new SaveUserHelper(this,"MyStaff1.db",null,1);
        recordsUserHelper=new RecordsUserHelper(this);
        initializeData();
        cursor=recordsUserHelper.getReadableDatabase().query("recordsUser1",null,null,null,null,null,null);
        System.out.println("搜索记录的数据表"+cursor.getCount());
        adapter=new SimpleCursorAdapter(this,android.R.layout.simple_list_item_2,cursor
                ,new String[]{"name","type"},new int[]{android.R.id.text1, android.R.id.text2}
                , CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

//        adapter=new SimpleCursorAdapter(this,R.layout.list_search_item,cursor
//                ,new String[]{"_id","code"},new int[]{R.id.search_staff_text1,R.id.search_staff_text2}
//                , CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        mStaffSearchList.setAdapter(adapter);
    }
    /**
     * 初始化数据
     */
    private void initializeData() {
//        deleteData();
        db_user = saveUserHelper.getWritableDatabase();
        cursor=db_user.query("user1 ",null,null,null,null,null,null);
        System.out.println("user1结果"+cursor.getCount());
//        cursor.close();
    }
    /**
     * 避免重复初始化数据
     */
    private void deleteData() {
        db_user = saveUserHelper.getWritableDatabase();
        db_user.execSQL("delete from user1");
        db_user.close();
    }
    public void refrsh(){
        mUser.clear();
    }
    /**
     * 初始化事件监听
     */
    private void initListener() {
        /**
         * 清除历史纪录
         */
        mStaffSearchClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteRecords();
            }
        });
        /*
        * 搜索按钮保存搜索纪录，隐藏软键盘
            */
        mBtnSearchStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //隐藏键盘
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                //保存搜索记录
                insertRecords(mSearchWriteStaff.getText().toString().trim());
                refrsh();
                searchString = mSearchWriteStaff.getText().toString();
                queryData(searchString);
                if (cursor.getCount()==0){
                    Toast.makeText(SearchStaff.this,"无员工记录！",Toast.LENGTH_SHORT).show();
                }else {
                    refreshListView();
                }
            }
        });
        /**
         * EditText对键盘搜索按钮的监听，保存搜索纪录，隐藏软件盘
         */
        // TODO:搜索及保存搜索纪录
        mSearchWriteStaff.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {

                if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    //隐藏键盘
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    //保存搜索记录
                    insertRecords(mSearchWriteStaff.getText().toString().trim());
                }
                return false;
            }
        });
        /**
         * EditText搜索框对输入值变化的监听，实时搜索
         */
        // 使用TextWatcher实现对实时搜索
        mSearchWriteStaff.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (mSearchWriteStaff.getText().toString().equals("")) {
                    mSearchStaffTip.setText("搜索历史");
                    mStaffSearchClear.setVisibility(View.VISIBLE);
                    cursor = recordsUserHelper.getReadableDatabase().query("recordsUser1",null,null,null,null,null,"_id desc");
                    System.out.println("搜索历史"+cursor.getCount());
                    refreshListView();
                } else {
                    mSearchStaffTip.setText("搜索结果");
                    mStaffSearchClear.setVisibility(View.GONE);
//                    searchString = mSearchWriteGoods.getText().toString();
//                    queryData(searchString);
                }
            }
        });
        /**
         * ListView的item点击事件
         */
        // TODO: 2017/8/10 5、listview的点击 做你自己的业务逻辑 保存搜索纪录
        mStaffSearchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(mSearchStaffTip.getText().equals("搜索历史")){
                    String name = ((TextView) view.findViewById(android.R.id.text1)).getText().toString();
                    mSearchWriteStaff.setText(name);
                }else {
                    User user=new User();
                    user.setId(mUser.get(position).getId());
                    user.setName(mUser.get(position).getName());
                    user.setIdCard(mUser.get(position).getIdCard());
                    user.setPhone(mUser.get(position).getPhone());
                    user.setPlace(mUser.get(position).getPlace());
                    user.setNatives(mUser.get(position).getNatives());
                    user.setEdu(mUser.get(position).getEdu());
                    user.setCode(mUser.get(position).getCode());
                    user.setWork(mUser.get(position).getWork());
                    user.setBirthday(mUser.get(position).getBirthday());
                    user.setIntime(mUser.get(position).getIntime());
                    user.setType(mUser.get(position).getType());

                    Intent intent = new Intent(SearchStaff.this, StaffDetail.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                    System.out.println("Detail" +user);
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
            db_records = recordsUserHelper.getWritableDatabase();
            db_records.execSQL("insert into recordsUser1 values(null,null,?,?,null,null,null,null,null,null,null,null)", new String[]{username, ""});
            db_records.close();
        }
    }
    /**
     * 搜索数据库中的数据
     *
     * @param searchData
     */
    private void queryData(String searchData) {
        cursor =saveUserHelper .getReadableDatabase()
                .rawQuery("select * from user1 where code like '%" + searchData + "%' or code like '%" + searchData + "%'", null);
        System.out.println(cursor.getCount());
        if (cursor.moveToFirst()){
            do {
                User user=new User();
                user.setId(cursor.getString(cursor.getColumnIndex("_id")));
                user.setName(cursor.getString(cursor.getColumnIndex("name")));
                user.setCode(cursor.getString(cursor.getColumnIndex("code")));
                user.setIdCard(cursor.getString(cursor.getColumnIndex("idCard")));
                user.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
                user.setPlace(cursor.getString(cursor.getColumnIndex("place")));
                user.setNatives(cursor.getString(cursor.getColumnIndex("natives")));
                user.setEdu(cursor.getString(cursor.getColumnIndex("edu")));
                user.setWork(cursor.getString(cursor.getColumnIndex("work")));
                user.setBirthday(cursor.getString(cursor.getColumnIndex("birthday")));
                user.setIntime(cursor.getString(cursor.getColumnIndex("intime")));
                user.setType(cursor.getString(cursor.getColumnIndex("type")));
                mUser.add(user);
//                cursor.close();

            }while (cursor.moveToNext());
        }else{
            System.out.println(cursor+"------"+"SearchUser");
        }
//        cursor.close();

//            refreshListView();
    }
    /**
     * 检查是否已经存在此搜索纪录
     *
     * @param records
     * @return
     */
    private boolean hasDataRecords(String records) {

        cursor = recordsUserHelper.getReadableDatabase()
                //记录查询条件
                .rawQuery("select _id from recordsUser1 where _id= ? or code=?"
                        , new String[]{records});

        return cursor.moveToNext();
    }


    private void initView(){
        mBackSearchStaff = (ImageView) findViewById(R.id.back_search_staff);
        mBackSearchStaff.setOnClickListener(this);
        mSearchWriteStaff = (ClearEditText) findViewById(R.id.search_write_staff);
        mBtnSearchStaff = (TextView) findViewById(R.id.btn_search_staff);
        mBtnSearchStaff.setOnClickListener(this);
        mSearchStaffTip = (TextView) findViewById(R.id.search_staff__tip);
        mStaffSearchList = (ListViewForScrollView) findViewById(R.id.staff_search_list);
        mStaffSearchClear = (TextView) findViewById(R.id.staff_search_clear);
    }
    /**
     * 删除历史纪录
     */
    private void deleteRecords() {
        db_records =recordsUserHelper.getWritableDatabase();
        db_records.execSQL("delete from recordsUser1");

        cursor = recordsUserHelper.getReadableDatabase().rawQuery("select * from recordsUser1", null);
        if (mSearchWriteStaff.getText().toString().equals("")) {
            refreshListView();
        }
    }
    /**
     * 刷新listview
     */
    private void refreshListView() {
        Log.d("刷新页面",adapter.toString());
        adapter.notifyDataSetChanged();
        adapter.swapCursor(cursor);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db_records != null) {
            db_records.close();
        }
        if ( db_user != null) {
            db_user.close();
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_search_staff:
                finish();
                break;
            case R.id.btn_search_staff:
                Toast.makeText(SearchStaff.this,"搜索员工信息 ", Toast.LENGTH_SHORT).show();
//                break;
            default:
                break;
        }
    }
}
