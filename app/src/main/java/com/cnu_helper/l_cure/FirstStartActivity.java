package com.cnu_helper.l_cure;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class FirstStartActivity extends AppCompatActivity {

    private ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_start);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new PagerAdapterClass(getApplicationContext()));
    }

    private View.OnClickListener mCloseButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int infoFirst = 1;
            SharedPreferences a = getSharedPreferences("a", MODE_PRIVATE);
            SharedPreferences.Editor editor = a.edit();
            editor.putInt("First", infoFirst);
            editor.commit();
            finish();
        }
    };

    private class PagerAdapterClass extends PagerAdapter{
        private LayoutInflater mlnflater;

        public PagerAdapterClass(Context c) {
            super();
            mlnflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Object instantiateItem(View pager, int position) {
            View v = null;

            switch (position) {
                case 0: {
                    v = mlnflater.inflate(R.layout.firststartview1, null);
                    v.findViewById(R.id.fsv_one);
                    break;
                }
                case 1: {
                    v = mlnflater.inflate(R.layout.firststartview2, null);
                    v.findViewById(R.id.fsv_two);
                    break;
                }
                case 2: {
                    v = mlnflater.inflate(R.layout.firststartview3, null);
                    v.findViewById(R.id.fsv_three);
                    break;
                }
                case 3: {
                    v = mlnflater.inflate(R.layout.firststartview4, null);
                    v.findViewById(R.id.fsv_four);
                    break;
                }
                case 4: {
                    v = mlnflater.inflate(R.layout.firststartview5, null);
                    v.findViewById(R.id.fsv_five).setOnClickListener(mCloseButtonClick);
                    break;
                }
                default:
                    break;
            }

            ((ViewPager)pager).addView(v, 0);
            return v;
        }

        @Override
        public void destroyItem(View pager, int position, @NonNull Object view) {
            ((ViewPager)pager).removeView((View)view);
        }

        @Override
        public boolean isViewFromObject(@NonNull View pager, @NonNull Object obj) {
            return pager == obj;
        }

        @Override
        public void restoreState(@Nullable Parcelable state, @Nullable ClassLoader loader) {
        }

        @Nullable
        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(@NonNull ViewGroup container) {
        }

        @Override
        public void finishUpdate(@NonNull ViewGroup container) {
        }
    }

}
