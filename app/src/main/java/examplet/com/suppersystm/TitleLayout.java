package examplet.com.suppersystm;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class TitleLayout extends LinearLayout {
    private ImageView mImgvTitleBack;
    private TextView mTitleLeft;
    private TextView mTitleCenter;
    private ImageView mImgvTitleAdd;


    public TitleLayout(final Context context, AttributeSet attrs){
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.title_bar,this);
        mImgvTitleBack = (ImageView) findViewById(R.id.imgv_title_back);
        mTitleLeft = (TextView) findViewById(R.id.title_left);
        mTitleCenter = (TextView) findViewById(R.id.title_center);
        mImgvTitleAdd = (ImageView) findViewById(R.id.imgv_title_add);
        mImgvTitleAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mImgvTitleBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

//                ActivityCollector.finishAll();
//                ((MainActivity)getContext()).finish();

            }
        });

    }
}
