package examplet.com.suppersystm.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.youth.banner.loader.ImageLoaderInterface;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import examplet.com.suppersystm.R;
import examplet.com.suppersystm.goods.GoodsActivity;
import examplet.com.suppersystm.http.HttpUtils;
import examplet.com.suppersystm.login.RegisterActivity;
import examplet.com.suppersystm.member.MemberActivity;
import examplet.com.suppersystm.staff.StaffActivity;

/**
 * Created by pc on 2019/3/27.
 */

public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener{
    private ImageView mImgvTitleBack;
    private TextView mTitleLeft;
    private ImageView mImgvTitleAdd;
    private TextView mTitleCenter;

    private GridView homeItemView;
    private int[] imageRes={R.drawable.vip_home,R.drawable.goods_home,R.drawable.staff_home,
            R.drawable.vip_recharge,R.drawable.supplier_home,R.drawable.more_home};
    private String[] itemName={"会员管理","商品管理","员工管理","会员充值","供应商","更多"};
    private List<Map<String, Object>> data;
    private SimpleAdapter adapter;
    //轮播图
    private Banner mBanner;
    private ArrayList<Integer> images;
    private ArrayList<String> imageTitle;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home, null);
        initData();
        initView(v);
        return v;

    }
    public void initView(View view){
         mTitleCenter=view.findViewById(R.id.title_center);
        mTitleCenter.setText("首页");
        mImgvTitleBack =view.findViewById(R.id.imgv_title_back);
        mImgvTitleBack.setVisibility(View.GONE);
        mTitleLeft =  view.findViewById(R.id.title_left);
        mTitleLeft.setVisibility(View.GONE);
        mImgvTitleAdd =  view.findViewById(R.id.imgv_title_add);
        mImgvTitleAdd.setVisibility(View.GONE);

        //中间菜单部分
        homeItemView= view.findViewById(R.id.home_item);
        data=new ArrayList<Map<String,Object>>();
        adapter=new SimpleAdapter(getContext(),getDate(),R.layout.item_home,
                new String[] {"image","text"},new int[] {R.id.home_item_pic,R.id.home_item_name});
        homeItemView.setAdapter(adapter);
        homeItemView.setOnItemClickListener(this);
        //轮播图
        mBanner=(Banner) view.findViewById(R.id.banner);
        //可选样式如下:
        //1. Banner.CIRCLE_INDICATOR    显示圆形指示器
        //2. Banner.NUM_INDICATOR   显示数字指示器
        //3. Banner.NUM_INDICATOR_TITLE 显示数字指示器和标题
        //4. Banner.CIRCLE_INDICATOR_TITLE  显示圆形指示器和标题
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        mBanner.setImageLoader(new GliderImageLoder());
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        mBanner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        mBanner.setBannerTitles(imageTitle);
        //设置轮播样式（没有标题默认为右边,有标题时默认左边）
        //可选样式:
        //Banner.LEFT   指示器居左
        //Banner.CENTER 指示器居中
        //Banner.RIGHT  指示器居右
        mBanner.setIndicatorGravity(Banner.TEXT_ALIGNMENT_CENTER);
        //设置是否允许手动滑动轮播图
        mBanner.setViewPagerIsScroll(true);
        //设置是否自动轮播（不设置则默认自动）
        mBanner.isAutoPlay(true);
        //设置轮播图片间隔时间（不设置默认为2000）
        mBanner.setDelayTime(3000);
        //设置图片资源:可选图片网址/资源文件，默认用Glide加载,也可自定义图片的加载框架
        //所有设置参数方法都放在此方法之前执行
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.setImages(images)
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
//                        Toast.makeText(getActivity(), "你点了第" + (position + 1) + "张轮播图", Toast.LENGTH_SHORT).show();
                    }
                })
                .start();



    }
    private List<Map<String, Object>> getDate() {
        for(int i=0;i<imageRes.length;i++){
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("image", imageRes[i]);
            map.put("text", itemName[i]);
            data.add(map);

        }
        return data;
    }

    @Override
    public void setMenuVisibility(boolean menuVisibility) {
        super.setMenuVisibility(menuVisibility);
        if (this.getView() != null) {
            this.getView().setVisibility(menuVisibility ? View.VISIBLE : View.GONE);
        }
    }

    private void initData(){
        images = new ArrayList<>();
        images.add(R.drawable.supper2);
        images.add(R.drawable.supper1);
        images.add(R.drawable.supper3);
        images.add(R.drawable.supper4);
        images.add(R.drawable.supper5);
        //设置图片标题:自动对应
        imageTitle = new ArrayList<>();
        imageTitle.add("更舒适的环境，更美好的体验");
        imageTitle.add("十大星级品牌联盟");
        imageTitle.add("实打实大优惠");
        imageTitle.add("我不干了");
        imageTitle.add("嗨购5折不要停，12.12趁现在");
    }
    //首页菜单点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
//                Toast.makeText(getContext(),"ll1a",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(),MemberActivity.class);
                startActivity(intent);
                break;
            case 1:
                Intent intent1 = new Intent(getContext(),GoodsActivity.class);
                startActivity(intent1);
                break;
            case 2:
                Intent intent2 = new Intent(getContext(),StaffActivity.class);
                startActivity(intent2);
                break;
            case 3:
                Intent intent3 = new Intent(getContext(),RegisterActivity.class);
                startActivity(intent3);
                break;
            case 4:
                Intent intent4 = new Intent(getContext(),RegisterActivity.class);
                startActivity(intent4);
                break;
            default:
                Toast.makeText(getContext(),"aaaa",Toast.LENGTH_SHORT);
                break;
        }
    }
//轮播图加载器
    private class GliderImageLoder extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context)
                    .load(path)
                    .into(imageView);
        }
    }
}
