package examplet.com.suppersystm.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import examplet.com.suppersystm.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText mRegisterUsername;
    private EditText mRegisterPassword;
    private EditText mRegisterPassword1;
    private Button mRegisterBack;
    private Button mRegister;
    private TextView mTitleCenter;
    private ImageView mImgvTitleBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mRegisterUsername = (EditText) findViewById(R.id.register_username);
        mRegisterPassword = (EditText) findViewById(R.id.register_password);
        mRegisterPassword1 = (EditText) findViewById(R.id.register_password1);
        mRegister = (Button) findViewById(R.id.register);
        mRegister.setOnClickListener(this);

        mTitleCenter = (TextView) findViewById(R.id.title_center);
        mTitleCenter.setText("注册");
        mImgvTitleBack = (ImageView) findViewById(R.id.imgv_title_back);
        mImgvTitleBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgv_title_back:
                finish();
                break;
            case R.id.register:
                Toast.makeText(this,"注册", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
