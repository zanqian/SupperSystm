package examplet.com.suppersystm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import examplet.com.suppersystm.R;

/**
 * Created by pc on 2019/3/9.
 */

public class ClassFragment extends Fragment {
    private LinearLayout mLayoutRootFragmentClass;
    private EditText mEdtSearchFragmentClass;
    private Button mBtSearchFragmentClass;
    private RadioGroup mRgRedpFragentClass;
    private ListView mLvClassFragmentClass;

    private TextView mTitleCenter;
    private  TextView mTitleLeft;
    private ImageView mImgvTitleBack;
    private ImageView mImgvTitleAdd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View v= LayoutInflater.from(getActivity()).inflate(R.layout.fragment_class,null);
        initView(v);
        return v;
    }
    @Override
    public void setMenuVisibility(boolean menuVisibility){
        super.setMenuVisibility(menuVisibility);
        if (this.getView()!=null){
            this.getView().setVisibility(menuVisibility ? View.VISIBLE : View.GONE);
        }
    }

    public void initView(View view){
        mLayoutRootFragmentClass =  view.findViewById(R.id.layout_root_fragment_class);
        mLayoutRootFragmentClass = view.findViewById(R.id.layout_root_fragment_class);
        mEdtSearchFragmentClass = view.findViewById(R.id.edt_search_fragment_class);
        mBtSearchFragmentClass = view.findViewById(R.id.bt_search_fragment_class);
        mRgRedpFragentClass =  view.findViewById(R.id.rg_redp_fragent_class);
//        mLvClassFragmentClass = (ListView) view.findViewById(R.id.lv_class_fragment_class);

        //获取焦点 把焦点放到跟布局上
        mLayoutRootFragmentClass.requestFocus();

        mTitleCenter =  view.findViewById(R.id.title_center);
        mTitleCenter.setText("资讯");
        //返回键及文字部分隐藏
        mImgvTitleBack = view.findViewById(R.id.imgv_title_back);
        mImgvTitleBack.setVisibility(View.GONE);
        mTitleLeft =  view.findViewById(R.id.title_left);
        mTitleLeft.setVisibility(View.GONE);
        mImgvTitleAdd =  view.findViewById(R.id.imgv_title_add);
        mImgvTitleAdd.setVisibility(View.GONE);


    }
//    private void initPoint(){
//
//    }
//    private void initViewPager(){}
}
