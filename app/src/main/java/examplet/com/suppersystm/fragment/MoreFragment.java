package examplet.com.suppersystm.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import examplet.com.suppersystm.R;
import examplet.com.suppersystm.adapter.ItemLvMoreFragAdapter;
import examplet.com.suppersystm.bean.Account;
import examplet.com.suppersystm.staff.SaveUserHelper;
import examplet.com.suppersystm.login.LoginActivity;
import examplet.com.suppersystm.me.AboutApp;
import examplet.com.suppersystm.me.PersonalInfomation;
import examplet.com.suppersystm.me.UpdatePassword;


/**
 * Created by pc on 2019/3/9.
 */

public class MoreFragment extends Fragment implements AdapterView.OnItemClickListener{
    private ListView mLvMoreFrag;
    //标题栏
    private ImageView mImgvTitleBack;
    private TextView mTitleCenter;
    private  TextView mTitleLeft;
    private ImageView mImgvTitleAdd;

    private ImageView mUserHeader;

    private TextView mUserName;
    private TextView mUserAcc;
    private TextView mUserPosition;
    private Account account;
    private SaveUserHelper dbHepler;
    private String vName,vId,vType;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable   Bundle savedInstanceState){
        View v=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_more,null);
//
//        Bundle bundle=getActivity().getIntent().getExtras();
//        account=(Account) bundle.get("account");
        initView(v);
//个人信息展示部分
//        String name=account.getUsername();
//        dbHepler=new SaveUserHelper(getActivity(),"User.db",null,1);
//        SQLiteDatabase db=dbHepler.getWritableDatabase();
//        Cursor cursor=db.query("saveuser",null,"username=?", new String[]{name},null,null,null);
//        if (cursor.moveToFirst()){
//            String userName=cursor.getString(cursor.getColumnIndex("username"));
//            String type=cursor.getString(cursor.getColumnIndex("type"));
//            Log.d("MoreFragment","user name is"+userName);
//            Log.d("MoreFragment","user type is"+type);
//        }else{
//            System.out.println(cursor+"MoreFragmentC");
//        }
//        cursor.close();
        return v;

    }
    @Override
    public void setMenuVisibility(boolean menuVisibility){
        super.setMenuVisibility(menuVisibility);
        if (this.getView()!=null){
            this.getView().setVisibility(menuVisibility ? View.VISIBLE : View.GONE);
        }
    }
    public void initView(View view) {
//        System.out.println(account.getType());
        mLvMoreFrag=(ListView) view.findViewById(R.id.lv_more_frag);
        mLvMoreFrag.setOnItemClickListener(this);
        mLvMoreFrag.setAdapter(new ItemLvMoreFragAdapter(getActivity()));
        mTitleCenter = (TextView) view.findViewById(R.id.title_center);
        mTitleCenter.setText("我的");
        //返回键及文字部分隐藏
        mImgvTitleBack = (ImageView) view.findViewById(R.id.imgv_title_back);
        mImgvTitleBack.setVisibility(View.GONE);
        mTitleLeft = (TextView) view.findViewById(R.id.title_left);
        mTitleLeft.setVisibility(View.GONE);
        mImgvTitleAdd = (ImageView) view.findViewById(R.id.imgv_title_add);
        mImgvTitleAdd.setVisibility(View.GONE);
        //用户头像固定为这个
        mUserHeader = (ImageView) view.findViewById(R.id.user_header);
        Glide.with(this).load(R.drawable.user_head).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(mUserHeader);
        //获取个人信息展示
//        mUserName = (TextView) view.findViewById(R.id.user_name);
//        mUserName.setText(account.getName());
////        mUserName.setText(mAccount.toString());
//        mUserAcc = (TextView) view.findViewById(R.id.user_acc);
//        mUserAcc.setText(account.getUsername());
//        mUserPosition = (TextView) view.findViewById(R.id.user_position);
//        mUserPosition.setText(account.getType());

    }
//退出登陆

    private void showAlterDialog(){
        final AlertDialog.Builder alterDiaglog=new AlertDialog.Builder(getContext());
        alterDiaglog.setIcon(R.drawable.user_head);
        alterDiaglog.setTitle("你确定退出超市系统 ？");
        alterDiaglog.setMessage("               退出仍会接收消息");
        alterDiaglog.setNeutralButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(),"取消",Toast.LENGTH_SHORT).show();
            }
        });
        alterDiaglog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent(getActivity(), LoginActivity.class);
                //设置当前界面为top
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
//                System.exit(0);

                Toast.makeText(getContext(),"确定",Toast.LENGTH_SHORT).show();
            }
        });
        alterDiaglog.show();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                Intent intent=new Intent(getActivity(), PersonalInfomation.class);
                startActivity(intent);
                Toast.makeText(this.getContext(),"这里是"+position,Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Intent intent1=new Intent(getActivity(), UpdatePassword.class);
                startActivity(intent1);
                Toast.makeText(getContext(),"这里是"+position,Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(getContext(),"这里是"+position,Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Intent intent3=new Intent(getActivity(), AboutApp.class);
                startActivity(intent3);
                Toast.makeText(getContext(),"这里是"+position,Toast.LENGTH_SHORT).show();
                break;
            case 4:
                showAlterDialog();
                Toast.makeText(getContext(),"这里是"+position,Toast.LENGTH_SHORT).show();
                break;
            default:
                break;

        }
    }

}
