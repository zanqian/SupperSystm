package examplet.com.suppersystm.staff;

import android.content.ContentValues;
import android.content.Intent;
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

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import examplet.com.suppersystm.R;
import examplet.com.suppersystm.bean.User;
import examplet.com.suppersystm.http.HttpUtils;
import examplet.com.suppersystm.manager.BaseActivity;

public class StaffActivity extends BaseActivity implements View.OnClickListener{
    //标题栏
    private ImageView mImgvTitleBack;
    private TextView mTitleLeft;
    private TextView mTitleCenter;
    private ImageView mImgvTitleAdd;

    private LinearLayout mStaffSearchNo;
    private ListView mStaffItem;
    //staff
    private static  final int SUCCESS=1;
    private static final int ERROR = 2;
    private static final int FAILURE = 0;
    private String address="http://10.0.2.2:8080/MGraduation3/QueryUserActionApp";
    private List<User> mUser=new ArrayList<User>();
    //员工存储
    private SaveUserHelper saveUserHelper;
    SQLiteDatabase db_search_staff;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);
        saveUserHelper=new SaveUserHelper(this,"MyStaff1.db",null,1);
        init();
        initView();
        StaffListView();
    }
    private void init() {
        new Thread() {
            public void run(){
                int statusCode;
                HttpURLConnection connection = null;
                try{
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(3000);
                    connection.setReadTimeout(3000);
                    statusCode = connection.getResponseCode();
                    if (statusCode == 200) {
                        //对获取的输入流进行读取
                        InputStream in = connection.getInputStream();
                        String result = HttpUtils.readMyInputStream(in);
                        Message msg = new Message();
                        msg.what = SUCCESS;
                        msg.obj = result;
                        System.out.println("staff"+result);
                        handler.sendMessage(msg);
                        System.out.println("SUCCESS" + result);
                    } else {
                        System.out.println(statusCode);
                        Message msg = new Message();
                        msg.what = ERROR;
                        handler.sendMessage(msg);
                        System.out.println("Error" + msg);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    Message msg = new Message();
                    msg.what = FAILURE;
                    handler.sendMessage(msg);
                    System.out.println("FAILURE" + msg);
                }finally{
                    if (connection != null) {
                        //关闭HTTP连接
                            connection.disconnect();
                    }
                }
            }
        }.start();
    }
//员工信息点击事件
    private void StaffListView(){
        mStaffItem.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent,View view,int position,long id){
                User user=new User();
                user.setId(mUser.get(position).getId());
                user.setCode(mUser.get(position).getCode());
                user.setName(mUser.get(position).getName());
                user.setIdCard(mUser.get(position).getIdCard());
                user.setPhone(mUser.get(position).getPhone());
                user.setNatives(mUser.get(position).getNatives());
                user.setPlace(mUser.get(position).getPlace());
                user.setEdu(mUser.get(position).getEdu());
                user.setWork(mUser.get(position).getWork());
                user.setBirthday(mUser.get(position).getBirthday());
                user.setIntime(mUser.get(position).getIntime());
                user.setType(mUser.get(position).getType());

                Intent intent=new Intent(StaffActivity.this,StaffDetail.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });
    }
    //主线程
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case SUCCESS:
                    JSONAnalysis(msg.obj.toString());
                    break;
                case ERROR:
                    Toast.makeText(StaffActivity.this,"获取数据失败",Toast.LENGTH_SHORT).show();
                    break;
                case FAILURE:
                    Toast.makeText(StaffActivity.this,"获取数据失败",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };
    //json数据解析
    private void JSONAnalysis(String jsonData) {
        db_search_staff = saveUserHelper.getWritableDatabase();
        if (jsonData != null) {
            try {
                JSONArray jsonArray=new JSONArray(jsonData);
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject temp=(JSONObject) jsonArray.get(i);
                    User users=new User();
                    users.setId(temp.getString("id"));
                    users.setCode(temp.getString("code"));
                    users.setName(temp.getString("name"));
                    users.setIdCard(temp.getString("idcard"));
                    users.setPhone(temp.getString("phone"));
                    users.setNatives(temp.getString("natives"));
                    users.setPlace(temp.getString("place"));
                    users.setEdu(temp.getString("edu"));
                    users.setWork(temp.getString("work"));
                    users.setBirthday(temp.getString("birthday"));
                    users.setIntime(temp.getString("intime"));
                    users.setType(temp.getString("type"));
                    mUser.add(users);
//                    System.out.println(users.getName());
                    AddStaffSQL(users);
                }
                mStaffItem.setAdapter(new ItemStaffAdapter());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    //添加进本地数据库
    public void AddStaffSQL(User user){
        String id=user.getId();
        Cursor cursor=db_search_staff.query("user1",null,"_id=?",new String[]{id},null,null,null);
//        Log.d("这里是AddStaffSQL",id);
        if (cursor.getCount()==0){
            Log.e("进来了","------"+user.getId());
            ContentValues values=new ContentValues();
            values.put("_id",user.getId());
            values.put("code",user.getCode());
            values.put("name",user.getName());
            values.put("idCard",user.getIdCard());
            values.put("phone",user.getPhone());
            values.put("natives",user.getNatives());
            values.put("edu",user.getEdu());
            values.put("work",user.getWork());
            values.put("birthday",user.getBirthday());
            values.put("intime",user.getIntime());
            values.put("type",user.getType());
            db_search_staff.insert("user1",null,values);
            values.clear();

        }else {
            while (cursor.moveToNext()){
                System.out.println("存储数"+cursor.getCount());
            }
        }
    }
    private void initView(){
        //标题
        mTitleCenter = (TextView) findViewById(R.id.title_center);
        mTitleCenter.setText("员工管理");
        //返回键及文字部分隐藏
        mImgvTitleBack = (ImageView) findViewById(R.id.imgv_title_back);
//        mImgvTitleBack.setVisibility(View.GONE);
        mImgvTitleBack.setOnClickListener(this);
//        mTitleLeft = (TextView) findViewById(R.id.title_left);
//        mTitleLeft.setVisibility(View.GONE);
        mImgvTitleAdd = (ImageView) findViewById(R.id.imgv_title_add);
//        mImgvTitleAdd.setVisibility(View.GONE);
        Glide.with(this).load(R.drawable.staff_add).into(mImgvTitleAdd);
        mImgvTitleAdd.setOnClickListener(this);

        //搜索栏
        mStaffSearchNo = (LinearLayout) findViewById(R.id.staff_search_no);
        mStaffSearchNo.setOnClickListener(this);
        mStaffItem = (ListView) findViewById(R.id.staff_item);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgv_title_back:
                finish();
                break;
            case R.id.imgv_title_add:
//                Toast.makeText(this,"请在这里跳转至添加商品",Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(this,AddStaffActivity.class);
                startActivity(intent);
                break;
            case R.id.staff_search_no:
                Intent intent1=new Intent(StaffActivity.this,SearchStaff.class);
                startActivity(intent1);
                Toast.makeText(this,"搜索",Toast.LENGTH_SHORT).show();
                break;

        }
    }
    //员工适配器
    public class ItemStaffAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return mUser.size();
        }

        @Override
        public Object getItem(int position) {
            return mUser.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View converView, ViewGroup viewGroup) {
            View view=View.inflate(StaffActivity.this,R.layout.item_list_staff,null);
            TextView mStaffName = view.findViewById(R.id.staff_name);
            TextView mStaffIdCard = view.findViewById(R.id.staff_idCard);
            TextView mStaffType=view.findViewById(R.id.staff_type);
            User user=mUser.get(position);
            mStaffName.setText(user.getName());
            mStaffIdCard.setText(user.getIdCard());
            mStaffType.setText(user.getType());
            return view;
        }
    }
}
