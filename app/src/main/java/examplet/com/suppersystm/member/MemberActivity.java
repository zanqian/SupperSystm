package examplet.com.suppersystm.member;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import examplet.com.suppersystm.bean.Member;
import examplet.com.suppersystm.bean.User;
import examplet.com.suppersystm.http.HttpUtils;
import examplet.com.suppersystm.manager.BaseActivity;
import examplet.com.suppersystm.staff.SaveUserHelper;
import examplet.com.suppersystm.staff.StaffActivity;

public class MemberActivity extends BaseActivity implements View.OnClickListener {
    private EditText mSearchWriteHomeFragment;
    private Button mBtnSearchHomeFragment;
    //标题栏
    private ImageView mImgvTitleBack;
    private TextView mTitleLeft;
    private TextView mTitleCenter;
    private ImageView mImgvTitleAdd;

    private LinearLayout mSearchMemberBar;
    private ListView mMemberItem;
    //member
    private static  final int SUCCESS=1;
    private static final int ERROR = 2;
    private static final int FAILURE = 0;
    private String address="http://10.0.2.2:8080/MGraduation3/";
    private List<Member> mMember=new ArrayList<Member>();
    //会员存储
    private SaveMemberHelper saveMemberHelper;
    SQLiteDatabase db_search_member;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        saveMemberHelper=new SaveMemberHelper(this,"MyMember1.db",null,1);
        initData();
        initView();
        MemberList();
    }
    //会员list点击
    private void MemberList() {
        mMemberItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Member member=new Member();
                member.setId(mMember.get(i).getId());
                member.setCode(mMember.get(i).getCode());
                member.setName(mMember.get(i).getName());
                member.setIdCard(mMember.get(i).getIdCard());
                member.setBirthday(mMember.get(i).getBirthday());
                member.setIntegral(mMember.get(i).getIntegral());
                member.setIntime(mMember.get(i).getIntime());
                Intent intent=new Intent(MemberActivity.this,MemberDetail.class);
                intent.putExtra("member",member);
                startActivity(intent);
            }
        });
    }
    //get事件
    private void initData(){
        new Thread(){
            public void run(){
                int statusCode;
                HttpURLConnection connection=null;
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
                        System.out.println("member"+result);
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
    //主线程
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case SUCCESS:
                    JSONAnalysis(msg.obj.toString());
                    break;
                case ERROR:
                    Toast.makeText(MemberActivity.this,"获取数据失败",Toast.LENGTH_SHORT).show();
                    break;
                case FAILURE:
                    Toast.makeText(MemberActivity.this,"获取数据失败",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };
    //json数据解析
    private void JSONAnalysis(String jsonData) {
        db_search_member = saveMemberHelper.getWritableDatabase();
        if (jsonData != null) {
            try {
                JSONArray jsonArray=new JSONArray(jsonData);
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject temp=(JSONObject) jsonArray.get(i);
                    Member member=new Member();
                    member.setId(temp.getString("id"));
                    member.setCode(temp.getString("code"));
                    member.setName(temp.getString("name"));
                    member.setIdCard(temp.getString("idcard"));
                    member.setBirthday(temp.getString("birthday"));
                    member.setIntegral(temp.getString("integral"));
                    member.setIntime(temp.getString("intime"));
                    mMember.add(member);
//                    System.out.println(users.getName());
                    AddMemberSQL(member);
                }
                mMemberItem.setAdapter(new ItemMemberAdapter());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }//添加进本地数据库
    public void AddMemberSQL(Member member){
        String id=member.getId();
        Cursor cursor=db_search_member.query("user1",null,"_id=?",new String[]{id},null,null,null);
//        Log.d("这里是AddStaffSQL",id);
        if (cursor.getCount()==0){
            Log.e("进来了","------"+member.getId());
            ContentValues values=new ContentValues();
            values.put("_id",member.getId());
            values.put("code",member.getCode());
            values.put("name",member.getName());
            values.put("idCard",member.getIdCard());
            values.put("phone",member.getPhone());
            values.put("birthday",member.getBirthday());
            values.put("intime",member.getIntime());
            db_search_member.insert("member1",null,values);
            values.clear();

        }else {
            while (cursor.moveToNext()){
                System.out.println("存储数"+cursor.getCount());
            }
        }
    }
    private void initView(){
        //搜索框
        mSearchMemberBar = (LinearLayout) findViewById(R.id.search_member_bar);
        mSearchMemberBar.setOnClickListener(this);
//        mSearchWriteHomeFragment = (EditText) findViewById(R.id.search_write_home_fragment);

//        mBtnSearchHomeFragment = (Button) findViewById(R.id.btn_search_home_fragment);
//        mBtnSearchHomeFragment.setOnClickListener(this);
        mTitleCenter = (TextView) findViewById(R.id.title_center);
        mTitleCenter.setText("会员管理");
        //返回键及文字部分隐藏
        mImgvTitleBack = (ImageView) findViewById(R.id.imgv_title_back);
//        mImgvTitleBack.setVisibility(View.GONE);
        mImgvTitleBack.setOnClickListener(this);
//        mTitleLeft = (TextView) findViewById(R.id.title_left);
//        mTitleLeft.setVisibility(View.GONE);
        mImgvTitleAdd = (ImageView) findViewById(R.id.imgv_title_add);
//        mImgvTitleAdd.setVisibility(View.GONE);
        mImgvTitleAdd.setOnClickListener(this);
        mMemberItem = (ListView) findViewById(R.id.member_item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgv_title_back:
                this.finish();
                break;
            case R.id.imgv_title_add:
                Toast.makeText(this,"请在这里跳转至添加会员",Toast.LENGTH_SHORT).show();
                break;
            case R.id.search_member_bar:
                Toast.makeText(this,"点击sous",Toast.LENGTH_SHORT).show();
        }
    }
    //会员适配器
    public class ItemMemberAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return mMember.size();
        }

        @Override
        public Object getItem(int i) {
            return mMember.get(i);
        }

        @Override
        public View getView(int position, View converView, ViewGroup viewGroup) {
            View view=View.inflate(MemberActivity.this,R.layout.item_list_member,null);
            TextView mMemberName = (TextView) findViewById(R.id.member_name);
            TextView mMemberIdCard = (TextView) findViewById(R.id.member_idCard);
            TextView mMembero = (TextView) findViewById(R.id.member_);
            Member member=mMember.get(position);
            mMemberName.setText(member.getName());
            mMemberIdCard.setText(member.getIdCard());
            mMembero.setText(member.getIdCard());
            return view;


        }

        @Override
        public long getItemId(int i) {
            return i;
        }
    }
}
