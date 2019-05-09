
package examplet.com.suppersystm.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import examplet.com.suppersystm.R;
import examplet.com.suppersystm.adapter.ItemLvAboutAppAdapter;
import examplet.com.suppersystm.adapter.ItemLvMoreFragAdapter;
import examplet.com.suppersystm.manager.BaseActivity;
import examplet.com.suppersystm.manager.ClearEditText;

public class AboutApp extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener{
    //标题栏
    private ImageView mImgvTitleBack;
    private TextView mTitleCenter;
    private  TextView mTitleLeft;
    private ImageView mImgvTitleAdd;

    private ListView mAboutAppList;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        initView();
    }

    private void initView() {
        //标题栏
        mTitleCenter = (TextView) findViewById(R.id.title_center);
        mTitleCenter.setVisibility(View.INVISIBLE);
        //返回键及文字部分隐藏
        mImgvTitleBack = (ImageView) findViewById(R.id.imgv_title_back);
        mImgvTitleBack.setOnClickListener(this);
        mTitleLeft = (TextView) findViewById(R.id.title_left);
        mTitleLeft.setText("关于");
        mImgvTitleAdd = (ImageView) findViewById(R.id.imgv_title_add);
        mImgvTitleAdd.setVisibility(View.GONE);
        //list
        mAboutAppList = (ListView) findViewById(R.id.about_app_list);
        mAboutAppList.setOnItemClickListener(this);
        mAboutAppList.setAdapter(new ItemLvAboutAppAdapter(this));

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                Toast.makeText(AboutApp.this,"已经是最新版本啦~",Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Intent intent=new Intent(AboutApp.this,FunctionIntr.class);
                startActivity(intent);
                break;
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgv_title_back:
                finish();
                break;

        }
    }
}
