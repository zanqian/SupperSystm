package examplet.com.suppersystm.staff;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import examplet.com.suppersystm.R;
import examplet.com.suppersystm.bean.Goods;
import examplet.com.suppersystm.goods.AddGoods;
import examplet.com.suppersystm.goods.AddGoodsRes;
import examplet.com.suppersystm.http.HttpUtils;
import examplet.com.suppersystm.manager.BaseActivity;
import examplet.com.suppersystm.manager.ClearEditText;

public class AddStaffActivity extends BaseActivity implements View.OnClickListener{
//标题栏
private ImageView mImgvTitleBack;
    private TextView mTitleLeft;
    private TextView mTitleCenter;
    private ImageView mImgvTitleAdd;
//详细信息
    private ClearEditText mAddStaffCode;
    private ClearEditText mAddStaffName;
    private ClearEditText mAddStaffIdcard;
    private ClearEditText mAddStaffPhone;
    private ClearEditText mAddStaffNatives;
    private ClearEditText mAddStaffAddress;
    private ClearEditText mAddStaffEdu;
    private ClearEditText mAddStaffWork;
    private ClearEditText mAddStaffBirth;
    private Spinner mAddStaffType;
    private int mType;
    private Button mAddStaffSave;
    private Button mAddStaffCancel;
    //员工添加信息
    private String code,name,idcard,phone,natives,place,edu,work,birth,type;





    private static final int SUCCESS=1;
    private static final int ERROR=2;
    private static final int FAILURE=0;
    private String result1,result2;
    private String address= "http://10.0.2.2:8080/MGraduation3/AddUserActionApp";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);
        initView();
    }



    private void Cancel(){
        mAddStaffCode.setText("");
        mAddStaffName.setText("");
        mAddStaffIdcard.setText("");
        mAddStaffPhone.setText("");
        mAddStaffNatives.setText("");
        mAddStaffAddress.setText("");
        mAddStaffEdu.setText("");
        mAddStaffWork.setText("");
        mAddStaffType.setSelection(0,true);
    }
    /**
     * 展示日期选择对话框
     */
    private void showDatePickerDialog() {
        Calendar calendar= Calendar.getInstance();
        new DatePickerDialog(AddStaffActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                mAddStaffBirth.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

    }
    private void addStaff(){
        code=mAddStaffCode.getText().toString().trim();
        name=mAddStaffName.getText().toString().trim();
        idcard=mAddStaffIdcard.getText().toString().trim();
        phone=mAddStaffPhone.getText().toString().trim();
        natives=mAddStaffNatives.getText().toString().trim();
        place=mAddStaffAddress.getText().toString().trim();
        edu=mAddStaffEdu.getText().toString().trim();
        work=mAddStaffWork.getText().toString().trim();
        birth=mAddStaffBirth.getText().toString().trim();
        System.out.println("岗位"+mType);
        switch (mType){
            case 0:
                Toast.makeText(AddStaffActivity.this,"请选择员工职位",Toast.LENGTH_SHORT).show();
                break;
            case 1:
                type="老板";
                break;
            case 2:
                type="经理";
                break;
            case 3:
                type="营业员";
                break;
            case 4:
                type="普通员工";
                break;
        }
        if (code.isEmpty()||name.isEmpty()||idcard.isEmpty()||place.isEmpty()||phone.isEmpty()||natives.isEmpty()||edu.isEmpty()||work.isEmpty()||birth.isEmpty()){
            Toast.makeText(AddStaffActivity.this,"信息不能为空",Toast.LENGTH_SHORT).show();
        }else {
            initHttp();
        }
    }
    private void initHttp(){
        new Thread(){
            @Override
            public void run() {
                HttpURLConnection connection=null;
                try{
                    URL url=new URL(address);
                    connection=(HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    //超时信息
                    connection.setReadTimeout(5000);
                    connection.setConnectTimeout(5000);
                    //post方式不能设置缓存，需手动设置为false
                    connection.setUseCaches(false);
                    String data="code="+ URLEncoder.encode(code,"UTF-8")+
                            "&name="+ URLEncoder.encode(name,"UTF-8")+
                            "&idcard="+ URLEncoder.encode(idcard,"UTF-8")+
                            "&phone="+ URLEncoder.encode(phone,"UTF-8")+
                            "&natives="+ URLEncoder.encode(natives,"UTF-8")+
                            "&place="+ URLEncoder.encode(place,"UTF-8")+
                            "&edu="+ URLEncoder.encode(edu,"UTF-8")+
                            "&work="+ URLEncoder.encode(work,"UTF-8")+
                            "&birthday="+ URLEncoder.encode(birth,"UTF-8")+
                            "&type="+ URLEncoder.encode(type,"UTF-8");
                    OutputStream outputStream=connection.getOutputStream();
                    outputStream.write(data.getBytes());
                    outputStream.flush();
                    outputStream.close();
                    System.out.println(connection.getResponseCode());
                    if (connection.getResponseCode() ==200){
                        InputStream inputStream=connection.getInputStream();
                        String result= HttpUtils.readMyInputStream(inputStream);
                        Message msg=new Message();
                        msg.what=SUCCESS;
                        msg.obj=result;
                        handler.sendMessage(msg);
                        System.out.println("AddStaff SUCCESS"+result);
                    }else {
                        Message msg=new Message();
                        msg.what=ERROR;
                        handler.sendMessage(msg);
                        System.out.println("AddStaff ERROR"+connection.getResponseCode()+msg);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Message msg=new Message();
                    msg.what=FAILURE;
                    handler.sendMessage(msg);
                    System.out.println("AddStaff FALURE" + msg);
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

                    Intent intent=new Intent(AddStaffActivity.this,AddStaffRes.class);
                    intent.putExtra("user1",result1);
                    intent.putExtra("user2",result2);

                    System.out.println("---addstaffRes---"+result1+"----"+result2);
                    startActivity(intent);
                    Toast.makeText(AddStaffActivity.this,"save",Toast.LENGTH_SHORT).show();

                    break;
                case ERROR:
                    Toast.makeText(AddStaffActivity.this,"网络开小差",Toast.LENGTH_SHORT).show();
                    break;
                case FAILURE:
                    Toast.makeText(AddStaffActivity.this,"网络开小差",Toast.LENGTH_SHORT).show();
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
                Log.d(result1,"AddStaff 添加后的结果值"+result1+"----2--"+result2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgv_title_back:
                finish();
                break;
            case R.id.add_staff_save:
                addStaff();
                break;
            case R.id.add_staff_cancel:
                Cancel();
                break;

        }
    }
    private void initView() {
        //title
        mTitleCenter = (TextView) findViewById(R.id.title_center);
        mTitleCenter.setText("新增员工");
        mImgvTitleAdd = (ImageView) findViewById(R.id.imgv_title_add);
        mImgvTitleAdd.setVisibility(View.GONE);
        mImgvTitleBack = (ImageView) findViewById(R.id.imgv_title_back);
        mImgvTitleBack.setOnClickListener(this);

        mAddStaffCode = (ClearEditText) findViewById(R.id.add_staff_code);
        mAddStaffName = (ClearEditText) findViewById(R.id.add_staff_name);
        mAddStaffIdcard = (ClearEditText) findViewById(R.id.add_staff_idcard);
        mAddStaffPhone = (ClearEditText) findViewById(R.id.add_staff_phone);
        mAddStaffNatives = (ClearEditText) findViewById(R.id.add_staff_natives);
        mAddStaffAddress = (ClearEditText) findViewById(R.id.add_staff_address);
        mAddStaffEdu = (ClearEditText) findViewById(R.id.add_staff_edu);
        mAddStaffWork = (ClearEditText) findViewById(R.id.add_staff_work);
        mAddStaffBirth = (ClearEditText) findViewById(R.id.add_staff_birth);
        mAddStaffBirth.setInputType(InputType.TYPE_NULL);
        //点击显示日期选择框 先获得焦点
        mAddStaffBirth.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    showDatePickerDialog();
                }
            }
        });
        mAddStaffBirth.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    showDatePickerDialog();
                    return true;
                }
                return false;
            }
        });
        mAddStaffType = (Spinner) findViewById(R.id.add_staff_type);
        mAddStaffType.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mType=i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mAddStaffWork.setText("nothing");
            }
        });
        mAddStaffSave = (Button) findViewById(R.id.add_staff_save);
        mAddStaffSave.setOnClickListener(this);
        mAddStaffCancel = (Button) findViewById(R.id.add_staff_cancel);
        mAddStaffCancel.setOnClickListener(this);
    }
}
