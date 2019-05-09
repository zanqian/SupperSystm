package examplet.com.suppersystm.staff;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import examplet.com.suppersystm.R;
import examplet.com.suppersystm.bean.User;
import examplet.com.suppersystm.goods.EditGoods;
import examplet.com.suppersystm.goods.GoodsDetail;
import examplet.com.suppersystm.manager.BaseActivity;
import examplet.com.suppersystm.me.UpdatePassword;

public class StaffDetail extends BaseActivity implements View.OnClickListener {
    //标题栏
    private ImageView mImgvTitleBack;
    private TextView mTitleLeft;
    private TextView mTitleCenter;
    private ImageView mImgvTitleAdd;
    //员工信息
    private TextView mStaffName;
    private TextView mStaffCode;
    private TextView mStaffType;
    private TextView mStaffIdcard;
    private TextView mStaffPhone;
    private TextView mStaffNatives;
    private TextView mStaffEdu;
    private TextView mStaffPlace;
    private TextView mStaffWork;
    private TextView mStaffIntime;
    private TextView mStaffBirthday;

    private User mUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        initView();
        Bundle bundle=getIntent().getExtras();
        mUser=(User) bundle.get("user");
        mStaffName.setText(mUser.getName());
        mStaffCode.setText(mUser.getCode());
        mStaffType.setText(mUser.getType());
        mStaffIdcard.setText(mUser.getIdCard());
        mStaffPhone.setText(mUser.getPhone());
        mStaffNatives.setText(mUser.getNatives());
        mStaffEdu.setText(mUser.getEdu());
        mStaffPlace.setText(mUser.getPlace());
        mStaffWork.setText(mUser.getWork());
        mStaffIntime.setText(mUser.getIntime());
        mStaffBirthday.setText(mUser.getBirthday());
    }
    private void initView(){
        //标题不符
        mTitleCenter = (TextView) findViewById(R.id.title_center);
        mTitleCenter.setText("人员信息");
        //返回键及文字部分隐藏
        mImgvTitleBack = (ImageView) findViewById(R.id.imgv_title_back);
//        mImgvTitleBack.setVisibility(View.GONE);
        mImgvTitleBack.setOnClickListener(this);
        mImgvTitleAdd = (ImageView) findViewById(R.id.imgv_title_add);
        Glide.with(this).load(R.drawable.edit_staff1).into(mImgvTitleAdd);
        mImgvTitleAdd.setOnClickListener(this);
        //员工信息

        mStaffName = (TextView) findViewById(R.id.staff_name);
        mStaffCode = (TextView) findViewById(R.id.staff_code);
        mStaffType = (TextView) findViewById(R.id.staff_type);
        mStaffIdcard = (TextView) findViewById(R.id.staff_idcard);
        mStaffPhone = (TextView) findViewById(R.id.staff_phone);
        mStaffNatives = (TextView) findViewById(R.id.staff_natives);
        mStaffEdu = (TextView) findViewById(R.id.staff_edu);
        mStaffPlace = (TextView) findViewById(R.id.staff_place);
        mStaffWork = (TextView) findViewById(R.id.staff_work);
        mStaffIntime = (TextView) findViewById(R.id.staff_intime);
        mStaffBirthday = (TextView) findViewById(R.id.staff_birthday);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgv_title_back:
                finish();
                break;
            case R.id.imgv_title_add:
                Intent intent=new Intent(StaffDetail.this,UpdateStaff.class);
                intent.putExtra("user",mUser);
                startActivity(intent);
//                Toast.makeText(GoodsDetail.this,"编辑商品",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
