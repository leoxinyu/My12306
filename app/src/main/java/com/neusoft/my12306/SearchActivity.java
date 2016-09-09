package com.neusoft.my12306;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neusoft.my12306.adapter.MyFragmentAdapter;
import com.neusoft.my12306.fragment.MyFragment;
import com.neusoft.my12306.fragment.OrderListFragment;
import com.neusoft.my12306.fragment.SearchFragment;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private TextView titleTxt;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private Fragment searchFragment,orderListFragment,myFragment;
    private ViewPager vp;
    private MyFragmentAdapter adapter;
    private TextView preOrderTxt,orderTxt,myTxt;
    private ImageView navImgView;
    private int screenWidth;
    private int currentIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        findView();
        initFragment();
        initNavWidth();
    }

    private void findView(){
        vp = (ViewPager)findViewById(R.id.vp_id);
        titleTxt = (TextView)findViewById(R.id.title_txt_id);
        preOrderTxt = (TextView)findViewById(R.id.pre_order_txt_id);
        orderTxt = (TextView)findViewById(R.id.order_txt_id);
        myTxt = (TextView)findViewById(R.id.my_txt_id);
        navImgView = (ImageView)findViewById(R.id.nav_img_id);
        titleTxt.setText("车票预订");
    }

    private void initFragment(){
        searchFragment = new SearchFragment();
        orderListFragment = new OrderListFragment();
        myFragment = new MyFragment();
        fragmentList.add(searchFragment);
        fragmentList.add(orderListFragment);
        fragmentList.add(myFragment);
        adapter = new MyFragmentAdapter(getSupportFragmentManager(),fragmentList);
        vp.setAdapter(adapter);
        vp.setCurrentItem(0);

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)navImgView.getLayoutParams();

                if(currentIndex==0&&position==0){   //0---->1
                    lp.leftMargin = (int)positionOffset*screenWidth/3;
                }
                if(currentIndex==1&&position==1){   //1---->2
                    lp.leftMargin = (int)(currentIndex*screenWidth/3 + positionOffset*screenWidth/3);
                }
                if(currentIndex==1&&position==0){   //1---->0
                    lp.leftMargin = (int)(currentIndex*screenWidth/3 - (1-positionOffset)*screenWidth/3);
                }
                if(currentIndex==2&&position==1){   //2---->1
                    lp.leftMargin = (int)(currentIndex*screenWidth/3 - (1-positionOffset)*screenWidth/3);
                }
                navImgView.setLayoutParams(lp);

            }

            @Override
            public void onPageSelected(int position) {
                resetTextColor();
                switch (position){
                    case 0:
                        preOrderTxt.setTextColor(Color.rgb(0,128,255));
                        titleTxt.setText("车票预订");
                        break;
                    case 1:
                        orderTxt.setTextColor(Color.rgb(0,128,255));
                        titleTxt.setText("订单查询");
                        break;
                    case 2:
                        myTxt.setTextColor(Color.rgb(0,128,255));
                        titleTxt.setText("我的12306");
                    default:
                        break;

                }
                currentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //state：为0代表什么都没做，为1代表正在滑动，为2代表滑动结束
            }
        });

    }
    public MyFragmentAdapter getAdapter(){
       return  adapter;
    }

    private void initNavWidth(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)navImgView.getLayoutParams();
        lp.width = (int)screenWidth/3;
        navImgView.setLayoutParams(lp);
    }

    private void resetTextColor(){
        preOrderTxt.setTextColor(Color.WHITE);
        orderTxt.setTextColor(Color.WHITE);
        myTxt.setTextColor(Color.WHITE);
    }

}
