package examplet.com.suppersystm.login;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import examplet.com.suppersystm.MainActivity;
import examplet.com.suppersystm.R;
import examplet.com.suppersystm.bean.Account;
import examplet.com.suppersystm.staff.SaveUserHelper;
import examplet.com.suppersystm.http.HttpLogin;
import examplet.com.suppersystm.manager.BaseActivity;


public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEdtUserLoginActivity;
    private EditText mEdtPswLoginActivity;
    private CheckBox mCheckbox;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CheckBox mCdPswLoginActivity;
    private Button mLogin;
    //    private TextView mForgetPswLogin;
    private TextView mRegisterUserLogin;
    private TextView mTitleCenter;


    private EditText mEdtYzmLoginActivity;
    private ImageView mYzmLogin;

    private CodeUtils codeUtils;
    private String codeStr, username, password;
    Account mAccount = new Account();

    private final static int LOGIN_JUDGE = 1;
    private int id;
    private SaveUserHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        TitleLayout titleLayout=new TitleLayout();
        mEdtUserLoginActivity = (EditText) findViewById(R.id.edt_user_login_activity);
        mEdtPswLoginActivity = (EditText) findViewById(R.id.edt_psw_login_activity);
        mCheckbox = (CheckBox) findViewById(R.id.checkbox);
        //记住密码功能
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        mCdPswLoginActivity = (CheckBox) findViewById(R.id.cd_psw_login_activity);
        boolean isRemember = pref.getBoolean("cd_psw_login_activity", false);
        if (isRemember) {
            //将账号和密码都设置到文本框中
            String account = pref.getString("edt_user_login_activity", "");
            String pass = pref.getString("edt_psw_login_activity", "");
            mEdtUserLoginActivity.setText(account);
            mEdtPswLoginActivity.setText(pass);
            mCdPswLoginActivity.setChecked(true);
        }

        mLogin = (Button) findViewById(R.id.login);
        mLogin.setOnClickListener(this);
//        mForgetPswLogin = (TextView) findViewById(R.id.forget_psw_login);
        //注册
        mRegisterUserLogin = (TextView) findViewById(R.id.register_user_login);
        mRegisterUserLogin.setOnClickListener(this);


//        mTitleCenter=(TextView) findViewById(R.id.title_center);
//        mTitleCenter.setText("登 录");

        //验证码事件
        mEdtYzmLoginActivity = (EditText) findViewById(R.id.edt_yzm_login_activity);
        mYzmLogin = (ImageView) findViewById(R.id.yzm_login);
        codeUtils = CodeUtils.getInstance();
        Bitmap bitmap = codeUtils.createBitmap();
        mYzmLogin.setImageBitmap(bitmap);
        mEdtYzmLoginActivity.setOnClickListener(this);
        mYzmLogin.setOnClickListener(this);
        //动态设置密码的可见性
        mCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mEdtPswLoginActivity.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    mEdtPswLoginActivity.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        //数据库User
        dbHelper=new SaveUserHelper(this,"User.db",null,1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yzm_login:
//            点击验证码 切换
//               codeUtils=CodeUtils.getInstance();
//                Bitmap bitmap=codeUtils.createBitmap();
//                mYzmLogin.setImageBitmap(bitmap);
                VerificationChange();
                break;
            case R.id.login:
                //验证码测试
                VerificationCode();
//                mEdtPswLoginActivity.setText("");
                break;
            case R.id.register_user_login:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            default:
                Toast.makeText(this, "错误点击", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //验证码更改事件
    private void VerificationChange() {
        codeUtils = CodeUtils.getInstance();
        Bitmap bitmap = codeUtils.createBitmap();
        mYzmLogin.setImageBitmap(bitmap);
    }

    //验证码验证事件
    private void VerificationCode() {
        try {
            codeStr = mEdtYzmLoginActivity.getText().toString().trim();
            username = mEdtUserLoginActivity.getText().toString().trim();
            password = mEdtPswLoginActivity.getText().toString().trim();
            Log.e("codeSr", codeStr);
            String code = codeUtils.getCode();
            Log.e("code", code);
            if (codeStr == null || TextUtils.isEmpty(codeStr) || username.equals("") || password.equals("")) {
                Toast.makeText(this, "请输入验证码或用户名或密码", Toast.LENGTH_SHORT).show();
                return;

            } else if (code.equalsIgnoreCase(codeStr)) {
//                    Toast.makeText(this, "验证码正确", Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //使用下面类里的函数，连接servlet，返回一个result，使用handler处理这个result
                        String result = HttpLogin.LoginByPost(username, password);
                        Bundle bundle = new Bundle();
                        bundle.putString("result", result);
                        Message message = new Message();
                        message.setData(bundle);
                        message.what = LOGIN_JUDGE;
                        message.obj = result;
                        handler.sendMessage(message);
                    }
                }).start();
            } else {
                Toast.makeText(this, "验证码错误", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//登录活动

    //servlet登录线程
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LOGIN_JUDGE: {
                    Bundle bundle = new Bundle();
                    bundle = msg.getData();
                    String result = bundle.getString("result");
//                    result="success";
                    try {
                        //记住密码
//                        if (result.equals("success")) {
                        if (result.equals("false")) {


                            Log.v("result", result);
                            Toast.makeText(LoginActivity.this, "密码或用户名错误！", Toast.LENGTH_SHORT).show();
                            mEdtYzmLoginActivity.setText("");
                            VerificationChange();
                        } else if (result.equals("1") || result.equals("2") || result.equals("3")) {
                            Toast.makeText(LoginActivity.this, "网络错误！", Toast.LENGTH_SHORT).show();
                        }else if(result.equals("1") || result.equals("2")||result.equals("3")){
                            Toast.makeText(LoginActivity.this, "网络错误！", Toast.LENGTH_SHORT).show();
                        }  else {
                            try {
                                JSONObject jsonObject = new JSONObject(msg.obj.toString());

                                mAccount.setUsername(jsonObject.getString("username"));
                                mAccount.setType(jsonObject.getString("type"));
                                mAccount.setName(jsonObject.getString("name"));


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            editor = pref.edit();
                            if (mCdPswLoginActivity.isChecked()) {
                                editor.putBoolean("cd_psw_login_activity", true);
                                editor.putString("edt_user_login_activity", username);
                                editor.putString("edt_psw_login_activity", password);
                            } else {
                                editor.clear();
                            }
                            editor.apply();


                            Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                            Intent localIntent = new Intent();
                            localIntent.setClass(LoginActivity.this, MainActivity.class);
                            localIntent.putExtra("account", mAccount);
                            LoginActivity.this.startActivity(localIntent);
                            System.out.println("username" + mAccount.getName());
                            save_user();


                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
    };

    private void save_user() {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String name=mAccount.getName();
        Cursor cursor=db.query("saveuser",null,"name=?",new String[]{name},null,null,null);
        Log.e("Login", "-------cursor------" + cursor);
        if (cursor.getCount()==0){
            ContentValues values=new ContentValues();
            values.put("id", mAccount.getId());
            values.put("username", mAccount.getUsername());
            values.put("password", mAccount.getPassword());
            values.put("state", mAccount.getState());
            values.put("type", mAccount.getType());
            values.put("name", mAccount.getName());
            db.insert("saveuser",null,values);
            System.out.println("------新数据-------");
        }else{
            while (cursor.moveToNext()) {
                id = cursor.getInt(cursor.getColumnIndex("id"));
                Log.e("Login", "-------id------" + id);
                if (mAccount.getId() != id) {
                    ContentValues values = new ContentValues();
                    values.put("id", mAccount.getId());
                    values.put("username", mAccount.getUsername());
                    values.put("password", mAccount.getPassword());
                    values.put("state", mAccount.getState());
                    values.put("type", mAccount.getType());
                    values.put("name", mAccount.getName());
                    db.insert("saveuser",null,values);
                    Log.e("Login", "-------values------" + values);
                }
            }
        }

    }
}
//    //登录
//    private void loginPro(){
//        try{
//            String s=mEdtPswLoginActivity.getText().toString();//获取页面密码
//            String sy=mEdtUserLoginActivity.getText().toString();//获取页面用户名
//            Message  m = handler.obtainMessage();
//            Bundle b= new Bundle();
//            b.putString("pass",s);
//            b.putString("name",sy);
//            m.setData(b);
//            handler.sendMessage(m);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//public class WorkThread extends  Thread {
//    @Override
//    public void run() {
//        Looper.prepare();
//        handler = new Handler() {
//            @Override
//            public void handleMessage(Message m) {
//                super.handleMessage(m);
//                Bundle b = m.getData();
//                String name = b.getString("name");//根据键取值
//                String pass = b.getString("pass");
//                DBUtil db = new DBUtil(name, pass);//调用数据库查询类
////                String ret = db.QuerySQL().trim();//得到返回值
//                Account ret=db.QuerySQL();
//                String result=ret.getUsername().trim();
//                Log.d("name",name);
//                Log.d("sql",ret.getUsername());
////                    if (ret.equals("1"))//为1，页面跳转，登陆成功
////                String name1=ret.getUsername();
////                Log.d("name1",name1);
//                if(name.equals("")||pass.equals("")){
//                    Toast.makeText(LoginActivity.this,"账户或密码为空",Toast.LENGTH_SHORT).show();
//                }
//                else if(result.equals(name)){
//                    Intent localIntent = new Intent();
//                    localIntent.setClass(LoginActivity.this, MainActivity.class);
//                    LoginActivity.this.startActivity(localIntent);
//                    Toast.makeText(LoginActivity.this, "登录成功"+ret.getType(), Toast.LENGTH_SHORT).show();//显示提示框
//                    return;
//                }
//
//                    Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
//
////                if(ret.equals(name))
////                {
////
////                    Intent localIntent = new Intent();
////                    localIntent.setClass(LoginActivity.this, MainActivity.class);
////                    LoginActivity.this.startActivity(localIntent);
////                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();//显示提示框
////                    return;
////                }else if(name.equals("")||pass.equals("")){
////                    Toast.makeText(LoginActivity.this,"账户或密码为空",Toast.LENGTH_SHORT).show();
////                }
////                Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
//            }
//        };
//        Looper.loop();
//    }

//    @Override
//    public void loginNet() {
//
//    }
//
//    @Override
//    public void saveRemberState(boolean isRember) {
//
//    }
//
//    @Override
//    public void saveUserPsw(String user, String psw) {
//
//    }



