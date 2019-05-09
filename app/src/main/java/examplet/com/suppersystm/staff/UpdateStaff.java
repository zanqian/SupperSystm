package examplet.com.suppersystm.staff;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import examplet.com.suppersystm.bean.User;
import examplet.com.suppersystm.goods.AddGoodsRes;
import examplet.com.suppersystm.goods.EditGoods;
import examplet.com.suppersystm.http.HttpUtils;
import examplet.com.suppersystm.manager.BaseActivity;
import examplet.com.suppersystm.manager.ClearEditText;

public class UpdateStaff extends BaseActivity implements View.OnClickListener {
    //标题栏
    private ImageView mImgvTitleBack;
    private TextView mTitleLeft;
    private TextView mTitleCenter;
    private ImageView mImgvTitleAdd;
    //修改的信息
    private TextView      mUdateStaffCode;
    private ClearEditText mUpdateStaffName;
    private ClearEditText mUpdateStaffIdcard;
    private ClearEditText mUpdateStaffPhone;
    private ClearEditText mUpdateStaffNatives;
    private ClearEditText mUpdateStaffPlace;
    private ClearEditText mUpdateStaffEdu;
    private ClearEditText mUpdateStaffWork;
    private ClearEditText mUpdateStaffBirthday;
    private TextView      mUdateStaffIntime;
    private RadioGroup mRadioGroup;
    private RadioButton mUpdateStaffType1;
    private RadioButton mUpdateStaffType2;
    private RadioButton mUpdateStaffType3;
    private RadioButton mUpdateStaffType4;

    private User mUser;
    private String mId,mCode,mName,mIdcard,mPhone,mNatives,mPlace,mEdu,mWork,mBirthday,mType;
    private static final int SUCCESS=1;
    private static final int ERROR=2;
    private static final int FAILURE=0;
    private String result1,result2;
    private String address= "http://10.0.2.2:8080/MGraduation3/UpdateUserAjaxActionApp";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_staff);
        initView();
    }
    private void initView(){
        //标题
        mTitleCenter = (TextView) findViewById(R.id.title_center);
        mTitleCenter.setText("修改员工信息");
        mImgvTitleAdd = (ImageView) findViewById(R.id.imgv_title_add);
        Glide.with(this).load(R.drawable.staff_update).into(mImgvTitleAdd);
        mImgvTitleAdd.setOnClickListener(this);
        mImgvTitleBack = (ImageView) findViewById(R.id.imgv_title_back);
        mImgvTitleBack.setOnClickListener(this);
        //修改的信息
        mUdateStaffCode = (TextView) findViewById(R.id.udate_staff_code);
        mUpdateStaffName = (ClearEditText) findViewById(R.id.update_staff_name);
        mUpdateStaffIdcard = (ClearEditText) findViewById(R.id.update_staff_idcard);
        mUpdateStaffPhone = (ClearEditText) findViewById(R.id.update_staff_phone);
        mUpdateStaffNatives = (ClearEditText) findViewById(R.id.update_staff_natives);
        mUpdateStaffPlace = (ClearEditText) findViewById(R.id.update_staff_place);
        mUpdateStaffEdu = (ClearEditText) findViewById(R.id.update_staff_edu);
        mUpdateStaffWork = (ClearEditText) findViewById(R.id.update_staff_work);
        mUpdateStaffBirthday = (ClearEditText) findViewById(R.id.update_staff_birthday);
        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        mUpdateStaffType1 = (RadioButton) findViewById(R.id.update_staff_type1);
        mUpdateStaffType2 = (RadioButton) findViewById(R.id.update_staff_type2);
        mUpdateStaffType3 = (RadioButton) findViewById(R.id.update_staff_type3);
        mUpdateStaffType4 = (RadioButton) findViewById(R.id.update_staff_type4);
        mUdateStaffIntime = (TextView) findViewById(R.id.udate_staff_intime);
        //信息展示到页面
        Bundle bundle=getIntent().getExtras();
        mUser=(User) bundle.get("user");
        mUdateStaffCode.setText(mUser.getCode());
        mUpdateStaffName.setText(mUser.getName());
        mUpdateStaffIdcard.setText(mUser.getIdCard());
        mUpdateStaffPhone.setText(mUser.getPhone());
        mUpdateStaffNatives.setText(mUser.getNatives());
        mUpdateStaffPlace.setText(mUser.getPlace());
        mUpdateStaffEdu.setText(mUser.getEdu());
        mUpdateStaffWork.setText(mUser.getWork());
        mUpdateStaffBirthday.setText(mUser.getBirthday());
        mUdateStaffIntime.setText(mUser.getIntime());

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (mUpdateStaffType1.isChecked()){
                    mType="老板";
                }else if (mUpdateStaffType2.isChecked()){
                    mType="经理";
                }else if (mUpdateStaffType3.isChecked()){
                    mType="营业员";
                }else if (mUpdateStaffType4.isChecked()){
                    mType="普通员工";
                }
            }
        });
        String type1=mUser.getType().toString().trim();
        CheckRadio(type1);

    }
    //判断信息是否为空
    private void isEmpty(){
        mId=mUser.getId();
        mCode=mUdateStaffCode.getText().toString().trim();
        mName=mUpdateStaffName.getText().toString().trim();
        mIdcard=mUpdateStaffIdcard.getText().toString().trim();
        mPhone=mUpdateStaffPhone.getText().toString().trim();
        mNatives=mUpdateStaffNatives.getText().toString().trim();
        mPlace=mUpdateStaffPlace.getText().toString().trim();
        mEdu=mUpdateStaffEdu.getText().toString().trim();
        mWork=mUpdateStaffWork.getText().toString().trim();
        mBirthday=mUpdateStaffBirthday.getText().toString().trim();

        Log.d("输入结果",mId+mName+mBirthday+mType);
        if (mCode.isEmpty()||mName.isEmpty()||mIdcard.isEmpty()|| mPhone.isEmpty()||mNatives.isEmpty()||mPlace.isEmpty()|| mEdu.isEmpty()||mWork.isEmpty()||mBirthday.isEmpty()||mType.isEmpty()){
            Toast.makeText(UpdateStaff.this,"信息不能为空",Toast.LENGTH_SHORT).show();
        }else {
        showAlterDialog();
        }
    }
    //
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
                    System.out.println("输入"+mId+mType);
                    String data="id="+ URLEncoder.encode(mId,"UTF-8")+
                            "&name="+URLEncoder.encode(mName,"UTF-8")+
                            "&idcard="+URLEncoder.encode(mIdcard,"UTF-8")+
                            "&phone="+URLEncoder.encode(mPhone,"UTF-8")+
                            "&natives="+URLEncoder.encode(mNatives,"UTF-8")+
                            "&place="+URLEncoder.encode(mPlace,"UTF-8")+
                            "&edu="+URLEncoder.encode(mEdu,"UTF-8")+
                            "&work="+URLEncoder.encode(mWork,"UTF-8")+
                            "&birthday="+URLEncoder.encode(mBirthday,"UTF-8")+
                            "&type="+URLEncoder.encode(mType,"UTF-8");
                    System.out.println("上传的data"+data);
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
                        System.out.println("Editstaff SUCCESS"+result);
                    }else {
                        Message msg=new Message();
                        msg.what=ERROR;
                        handler.sendMessage(msg);
                        System.out.println("Editstaff ERROR"+connection.getResponseCode()+msg);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Message msg=new Message();
                    msg.what=FAILURE;
                    handler.sendMessage(msg);
                    System.out.println("Editstaff FALURE" + msg);
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
                    Toast.makeText(UpdateStaff.this,"确定",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(UpdateStaff.this,UpdateStaffRes.class);
                    intent.putExtra("user",mUser);
                    intent.putExtra("user1",result1);
                    intent.putExtra("user2",result2);
                    System.out.println("---EditGoodsRes--"+result1+"----"+result2);
                    startActivity(intent);
                    break;
                case ERROR:
                    Toast.makeText(UpdateStaff.this,"网络开小差",Toast.LENGTH_SHORT).show();
                    break;
                case FAILURE:
                    Toast.makeText(UpdateStaff.this,"网络开小差",Toast.LENGTH_SHORT).show();
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
                Log.d(result1,"UpdateStaff  修改后的结果值"+result1+"----2--"+result2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void showAlterDialog(){
        final AlertDialog.Builder alterDiaglog=new AlertDialog.Builder(UpdateStaff.this);
        alterDiaglog.setIcon(R.drawable.user_head);
        alterDiaglog.setTitle("确定修改员工信息吗？");
        alterDiaglog.setNeutralButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                initHttp();
            }
        });
        alterDiaglog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(UpdateStaff.this,"取消",Toast.LENGTH_SHORT).show();
            }
        });
        alterDiaglog.show();
    }

    //设置radiobutton默认选中
    private void CheckRadio(String data){
        if (data.equals("老板")){
            mUpdateStaffType1.setChecked(true);
        }else if (data.equals("经理")){
            mUpdateStaffType2.setChecked(true);
        }else if (data.equals("营业员")){
            mUpdateStaffType3.setChecked(true);
        }else {
            mUpdateStaffType4.setChecked(true);
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgv_title_back:
                finish();
                break;
            case R.id.imgv_title_add:
//                showAlterDialog();
                isEmpty();
//                Toast.makeText(EditGoods.this,"aaa确定",Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
