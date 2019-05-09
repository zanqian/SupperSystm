package examplet.com.suppersystm.me;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import examplet.com.suppersystm.R;
import examplet.com.suppersystm.goods.AddGoods;
import examplet.com.suppersystm.goods.AddGoodsRes;
import examplet.com.suppersystm.http.HttpUtils;
import examplet.com.suppersystm.login.LoginActivity;
import examplet.com.suppersystm.manager.BaseActivity;
import examplet.com.suppersystm.manager.ClearEditText;

//原密码进行判断是否一致，两次新密码是否一致，修改成功后进行再次登陆
public class UpdatePassword extends BaseActivity implements View.OnClickListener{
    //标题栏
    private ImageView mImgvTitleBack;
    private TextView mTitleCenter;
    private  TextView mTitleLeft;
    private ImageView mImgvTitleAdd;

    private ClearEditText mUpdatePasswordOld;
    private ClearEditText mUpdatePasswordNew1;
    private ClearEditText mUpdatePasswordNew2;
    private Button mUpdatePasswordBtn;
    //
    private static final int SUCCESS=1;
    private static final int ERROR=2;
    private static final int FAILURE=0;
    private String result1,result2;
    private String oldPassword;
    private String newPassword;
    private String newPassword1;
    private String address="http://10.0.2.2:8080/MGraduation3/UpdatePSWActionApp";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        initView();
    }
    //
    private void updatePassword(){
        oldPassword=mUpdatePasswordOld.getText().toString().trim();
        newPassword=mUpdatePasswordNew1.getText().toString().trim();
        newPassword1=mUpdatePasswordNew2.getText().toString().trim();
        if (TextUtils.isEmpty(oldPassword)||TextUtils.isEmpty(newPassword)||TextUtils.isEmpty(newPassword1)){
            Toast.makeText(UpdatePassword.this,"密码不能为空",Toast.LENGTH_SHORT).show();
        }else if (newPassword.equals(newPassword1)==false){
            Toast.makeText(UpdatePassword.this,"前后两次密码输入不一致",Toast.LENGTH_SHORT).show();
        }else{
            initHttp();
        }
    }

//    修改密码线程、
    private  void  initHttp(){
        new Thread(){
            public void run(){
                HttpURLConnection connection=null;
                try{
                    URL url=new URL(address);
                    connection=(HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setReadTimeout(1000);
                    connection.setConnectTimeout(1000);
                    //设置缓存
                    connection.setUseCaches(false);
                    String data="oldPassword=" + URLEncoder.encode(oldPassword,"UTF-8")+
                            "&newPassword="+URLEncoder.encode(newPassword,"UTF-8");
                    OutputStream outputStream=connection.getOutputStream();
                    outputStream.write(data.getBytes());
                    outputStream.flush();
                    outputStream.close();
                    if (connection.getResponseCode() ==200){
                        InputStream inputStream=connection.getInputStream();
                        String result= HttpUtils.readMyInputStream(inputStream);
                        Message msg=new Message();
                        msg.what=SUCCESS;
                        msg.obj=result;
                        handler.sendMessage(msg);
                        System.out.println("Updatepassword SUCCESS"+result);
                    }else {
                        Message msg=new Message();
                        msg.what=ERROR;
                        handler.sendMessage(msg);
                        System.out.println("Updatepassword  ERROR"+connection.getResponseCode()+msg);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Message msg=new Message();
                    msg.what=FAILURE;
                    handler.sendMessage(msg);
                    System.out.println("Updatepassword  FALURE" + msg);
                }finally {
                    if (connection!=null){
                        connection.disconnect();
                    }
                }
//                }
            }
        }.start();
    }
    //
    private void resultJump(){
        if (result1.equals("success")){
                final AlertDialog.Builder alterDiaglog=new AlertDialog.Builder(UpdatePassword.this);
            alterDiaglog.setIcon(R.drawable.user_head);
            alterDiaglog.setTitle(result2);
            alterDiaglog.setMessage("              请重新登录");

            alterDiaglog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent=new Intent(UpdatePassword.this, LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(UpdatePassword.this,"确定",Toast.LENGTH_SHORT).show();
                }
            });
            alterDiaglog.show();
        }else{

            final AlertDialog.Builder alterDiaglog=new AlertDialog.Builder(UpdatePassword.this);
            alterDiaglog.setIcon(R.drawable.user_head);
            alterDiaglog.setTitle(result2);
            alterDiaglog.setMessage("               请重新修改");
            alterDiaglog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
//                System.exit(0);

                    Toast.makeText(UpdatePassword.this,"确定",Toast.LENGTH_SHORT).show();
                }
            });
            alterDiaglog.show();
        }
    }


    //主线程消息处理器
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case SUCCESS:
                    JsonAnalysis(msg.obj.toString());
                    System.out.println(result1);
                    if (result1.equals("success")){

                        System.out.println("success"+result2);

                    }else {
//                        Toast.makeText(UpdatePassword.this,"error",Toast.LENGTH_SHORT).show();
                        System.out.println("error"+result2);
                    }
//
//                    Intent intent=new Intent(AddGoods.this,AddGoodsRes.class);
//                    intent.putExtra("addGoodsRes",result1);
//                    intent.putExtra("addGoodsRes2",result2);
//                    System.out.println("---addGoodsRes---"+result1+"----"+result2);
//                    startActivity(intent);
//                    Toast.makeText(AddGoods.this,"save",Toast.LENGTH_SHORT).show();

                    break;
                case ERROR:
                    Toast.makeText(UpdatePassword.this,"网络开小差",Toast.LENGTH_SHORT).show();
                    break;
                case FAILURE:
                    Toast.makeText(UpdatePassword.this,"网络开小差",Toast.LENGTH_SHORT).show();
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
                Log.d(result1,"updatepassword 添加后的结果值"+result1+"----2--"+result2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void initView(){
        //标题栏
        mTitleCenter = (TextView) findViewById(R.id.title_center);
        mTitleCenter.setText("重置密码");
        //返回键及文字部分隐藏
        mImgvTitleBack = (ImageView) findViewById(R.id.imgv_title_back);
        mImgvTitleBack.setOnClickListener(this);
        mTitleLeft = (TextView) findViewById(R.id.title_left);
        mTitleLeft.setVisibility(View.INVISIBLE);
        mImgvTitleAdd = (ImageView) findViewById(R.id.imgv_title_add);
        mImgvTitleAdd.setVisibility(View.GONE);

        mUpdatePasswordOld = (ClearEditText) findViewById(R.id.update_password_old);
        mUpdatePasswordNew1 = (ClearEditText) findViewById(R.id.update_password_new1);
        mUpdatePasswordNew2 = (ClearEditText) findViewById(R.id.update_password_new2);
        mUpdatePasswordBtn = (Button) findViewById(R.id.update_password_btn);
        mUpdatePasswordBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgv_title_back:
                finish();
                break;
            case R.id.update_password_btn:
                updatePassword();
                break;
        }
    }
}
