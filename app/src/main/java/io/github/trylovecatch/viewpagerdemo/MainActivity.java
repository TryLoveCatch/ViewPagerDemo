package io.github.trylovecatch.viewpagerdemo;

import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ColorAnimationView mColorAnimationView;
    private ViewPager mViewPager;
    private LinearLayout mLinDots;


    private ColorAnimationView.Color[] mColors;
//    private Integer[] mColors;
    private List<View> mArrViews;
    private List<CheckBox> mArrDots;
    private int mCurrentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
    }

    private void initView(){
        mColorAnimationView = (ColorAnimationView) findViewById(R.id.color_animation_view);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mLinDots = (LinearLayout) findViewById(R.id.lin_dot);

        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mArrViews.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mArrViews.get(position));
                return mArrViews.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mArrViews.get(position));
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("MainActivity", "onPageScrolled====position: " + position +
                        ", positionOffset: " + positionOffset + ", positionOffsetPixels: " + positionOffsetPixels);
                handleBgAnim(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                Log.e("MainActivity", "onPageSelected====position: " + position);
                handleDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e("MainActivity", "onPageScrollStateChanged====state: " + state);
                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE: // 什么也没做
                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING: // 滑动中
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING: // 设置完毕
                        break;
                    default:
                        break;
                }
            }
        });


        LinearLayout.LayoutParams tParams;
        int tSize = getResources().getDimensionPixelSize(R.dimen.dp_6);
        int tMargin = getResources().getDimensionPixelSize(R.dimen.dp_10);
        for (CheckBox tView : mArrDots) {
            tParams = new LinearLayout.LayoutParams(tSize, tSize);
            tParams.rightMargin = tMargin;
            mLinDots.addView(tView, tParams);
        }

        mColorAnimationView.initData(mColors);
    }

    private void initData(){
        mArrViews = new ArrayList<>();

        View tView ;
        for(int i = 0;i<5;i++) {
            tView = LayoutInflater.from(this).inflate(R.layout.activity_guide_page_01, null);
            ((TextView)tView.findViewById(R.id.guide_page_item_txt_msg))
                    .setText("fjdsalkjfldsafjlkdsafdsafsadgrewqewqr324354fdsabfdsafdsafsdafs" + (i+1));
            ((TextView)tView.findViewById(R.id.guide_page_item_txt_title))
                    .setText("Title" + (i + 1));
            mArrViews.add(tView);
        }

        mArrDots = new ArrayList<>();
        CheckBox tCheckbox;
        for (int i = 0; i < mArrViews.size(); i++) {
            tCheckbox = new CheckBox(this);
            tCheckbox.setEnabled(false);
            tCheckbox.setBackgroundResource(R.drawable.guide_page_dot_selector);
            mArrDots.add(tCheckbox);
        }
        mArrDots.get(0).setChecked(true);

        mColors = new ColorAnimationView.Color[mArrViews.size()];
        ColorAnimationView.Color tColor = new ColorAnimationView.Color();
        tColor.startColor = Color.parseColor("#c1e0ff");
        tColor.endColor = Color.parseColor("#f5fdff");
        mColors[0] = tColor;
        tColor = new ColorAnimationView.Color();
        tColor.startColor = Color.parseColor("#cbeeff");
        tColor.endColor = Color.parseColor("#f5fdff");
        mColors[1] = tColor;
        tColor = new ColorAnimationView.Color();
        tColor.startColor = Color.parseColor("#d4fff8");
        tColor.endColor = Color.parseColor("#f4fffd");
        mColors[2] = tColor;
        tColor = new ColorAnimationView.Color();
        tColor.startColor = Color.parseColor("#e5fed8");
        tColor.endColor = Color.parseColor("#f4ffff");
        mColors[3] = tColor;
        tColor = new ColorAnimationView.Color();
        tColor.startColor = Color.parseColor("#f7fed1");
        tColor.endColor = Color.parseColor("#fffff8");
        mColors[4] = tColor;

//        mColors = new Integer[mArrViews.size()];
//        mColors[0] = Color.parseColor("#ff0000");
//        mColors[1] = Color.parseColor("#ffff00");
//        mColors[2] = Color.parseColor("#00ff00");
//        mColors[3] = Color.parseColor("#0000ff");
//        mColors[4] = Color.parseColor("#00ffff");
    }

    private void handleDots(int pPosition) {
        mArrDots.get(mCurrentPosition).setChecked(false);
        mArrDots.get(pPosition).setChecked(true);
        mCurrentPosition = pPosition;
    }

    private void handleBgAnim(int pPosition, float pPositionOffset) {
        int count = mArrViews.size() - 1;
        if (count != 0) {
            float length = (pPosition + pPositionOffset) / count;
            int progress = (int) (length * ColorAnimationView.DURATION);
            mColorAnimationView.seek(progress);
        }
    }
}
