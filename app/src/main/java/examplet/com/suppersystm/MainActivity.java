package examplet.com.suppersystm;

import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import examplet.com.suppersystm.bean.Account;
import examplet.com.suppersystm.fragment.ClassFragment;
import examplet.com.suppersystm.fragment.HomeFragment;
import examplet.com.suppersystm.fragment.MoreFragment;
import examplet.com.suppersystm.http.NetWorkStateReceiver;
import examplet.com.suppersystm.manager.BaseActivity;

//主要代码 超市管理APP
public class MainActivity extends BaseActivity{
    private SharedPreferences pref;
    private Button mButtonEdit;



    private FrameLayout mLayoutFragActivityMain;
    private RadioGroup mRgTabsActivityMain;
    private RadioButton mRbHomeActivityMain;
    private RadioButton mRbClassActivityMain;
    private RadioButton mRbSearchActivityMain;
    private RadioButton mRbMoreActivityMain;
    private Account mAccount;


    public final  int NUM_ITEMS=3;
    private FragmentManager manager;
//    private FragmentTransaction transaction;
//    private HomeFragment mHomeFragment = new HomeFragment();
//    private MoreFragment mMoreFragment = new MoreFragment();
//    private ClassFragment mClassFragment= new ClassFragment();
    NetWorkStateReceiver netWorkStateReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//          requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉title
//        pref= PreferenceManager.getDefaultSharedPreferences(this);
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.hide();
//        }
//        manager=getSupportFragmentManager();//获取碎片管理器
//        setContentView(R.layout.fragment_home);
        initView();
        //登陆后展示首页
        mRgTabsActivityMain.check(R.id.rb_home_activity_main);
        initData();

//        登录后才接收
//        Bundle bundle = getIntent().getExtras();
//        mAccount = (Account) bundle.get("account");
//        System.out.println(mAccount.getType());
    }
    protected void initView(){
        mLayoutFragActivityMain = (FrameLayout) findViewById(R.id.layout_frag_activity_main);
        mRgTabsActivityMain = (RadioGroup) findViewById(R.id.rg_tabs_activity_main);
        mRbHomeActivityMain = (RadioButton) findViewById(R.id.rb_home_activity_main);
        mRbClassActivityMain = (RadioButton) findViewById(R.id.rb_news_activity_main);
//        mRbSearchActivityMain = (RadioButton) findViewById(R.id.rb_search_activity_main);
        mRbMoreActivityMain = (RadioButton) findViewById(R.id.rb_more_activity_main);

        //监听事件为底部绑定状态改变监听
        mRgTabsActivityMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group ,int checkedId) {
                int index = 0;
                switch (checkedId) {
                    case R.id.rb_home_activity_main:
                        index = 0;
                        break;
                    case R.id.rb_news_activity_main:
                        index = 1;
                        break;
                    case R.id.rb_more_activity_main:
                        index = 2;
                        break;
                }
                //通过fragments这个adapter还有index来替换帧布局中的内容
                Fragment fragment = (Fragment) fragments.instantiateItem(mLayoutFragActivityMain,index);
                //一开始将帧布局中 的内容设置为第一个
                fragments.setPrimaryItem(mLayoutFragActivityMain, 0, fragment);
                fragments.finishUpdate(mLayoutFragActivityMain);
            }
        });
    }
    @Override
    protected  void onStart(){
        super.onStart();

    }
    //用adapter来管理四个Fragment界面的变化。注意，我这里用的Fragment都是v4包里面的
    FragmentStatePagerAdapter fragments = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
        //进行初始化fragment
        @Override
        public Fragment getItem(int i){
            Fragment fragmentNow=null;
            switch (i){
                case 0:
                    fragmentNow=new HomeFragment();//首页
                    break;
                case 1:
                    fragmentNow=new ClassFragment();//分类
                    break;
                case 2:
                    fragmentNow=new MoreFragment();//更多
                    break;
                default:
                    break;
            }
            return fragmentNow;

        }
    };
    protected void initData(){

    }
    //在onResume()方法注册
    @Override
    protected void onResume() {
        if (netWorkStateReceiver== null) {
            netWorkStateReceiver = new NetWorkStateReceiver();
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netWorkStateReceiver, filter);
        System.out.println("注册");
        super.onResume();
    }

    //onPause()方法注销
    @Override
    protected void onPause() {
        unregisterReceiver(netWorkStateReceiver);
        System.out.println("注销");
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((System.currentTimeMillis()-exitTime) > 2000){
            exitTime = System.currentTimeMillis();
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
        } else {
            mHandler.postDelayed(mFinish, 0);
        }
        //return super.onKeyDown(keyCode, event);
        return false;
    }
    private long exitTime = 0;

    private Handler mHandler = new Handler();
    private Runnable mFinish = new Runnable() {
        @Override
        public void run() {
            finish();
        }
    };

//    protected  void initListteners(){
//        mRgTabsActivityMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
//            @Override
//            public void onCheckedChanged(RadioGroup group,int checkedId){
//                Fragment fragmentNow=null;
//                switch (checkedId){
//                    case R.id.rb_home_activity_main:
//                        fragmentNow=mHomeFragment;
//                        break;
//                    case R.id.rb_class_activity_main:
//                        fragmentNow=mClassFragment;
//                        break;
//                    case R.id.rb_more_activity_main:
//                        fragmentNow=mMoreFragment;
//                        break;
//                }
//                changeFragment(fragmentNow);
//            }
//        });
//        mRbHomeActivityMain.setChecked(true);
//    }
//    private void changeFragment(Fragment fragmentNow){
//        //遍历Fragment
//        FragmentTransaction ft=manager.beginTransaction();
//        for (Fragment fragment:mFragment){
//            if (fragment!=fragmentNow){
//                ft.hide(fragment);//不是当前的fragment把他隐藏
//
//            }
//        }
//            if(!fragmentNow.isAdded()){
//                ft.add(R.id.layout_frag_activity_main,fragmentNow);//没有就添加
//            }
//        ft.show(fragmentNow);
//    }

}

