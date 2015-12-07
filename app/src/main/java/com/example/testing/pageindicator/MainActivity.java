package com.example.testing.pageindicator;

import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    PageIndicator pageIndicator;
    ViewPager viewPager;
    ArrayList<Integer> arrayList;
    ArrayList<View> viewList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ArrayList<View> viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
        viewList.add(new ImageView(this));
        viewList.add(new ImageView(this));
        viewList.add(new ImageView(this));
        arrayList=new ArrayList<>();

        arrayList.add(R.mipmap.a);
        arrayList.add(R.mipmap.b);
        arrayList.add(R.mipmap.c);
        arrayList.add(R.mipmap.d);
        arrayList.add(R.mipmap.e);
        pageIndicator = ((PageIndicator) findViewById(R.id.pageIndicator));
        viewPager= ((ViewPager) findViewById(R.id.viewPage));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("onPageScrolled","position=="+position+"positionOffset=="+positionOffset);

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
//                ImageView imageView = new ImageView(MainActivity.this);
                ImageView imageView = (ImageView) viewList.get(position);
                imageView.setImageBitmap(BitmapFactory.decodeResource(MainActivity.this.getResources(), arrayList.get(position)));
                container.addView(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                System.out.println("position==" + position);
                try {
                    container.removeView(viewList.get(position));
                }catch (Exception e){

                }
            }
        });
    }
    public void add(View v){
        pageIndicator.setCurrentPage(pageIndicator.getCurrentPage()+1);
    }
}
