package com.github.omadahealth.slidepager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.github.omadahealth.demo.R;
import com.github.omadahealth.slidepager.lib.SlidePager;
import com.nineoldandroids.animation.Animator;

import java.util.List;

public class MainActivity extends ActionBarActivity {

    private SlidePager mSlidePager;

    /**
     * The animation time in milliseconds that we take to display the steps taken
     */
    private static final int BAR_ANIMATION_TIME = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mSlidePager = (SlidePager) findViewById(R.id.slidepager);
        View[] demoViews = initDummyViews();
        mSlidePager.setAdapter(new DemoPagerAdapter(this, demoViews));

        mSlidePager.setPageTransformer(false, new SlideTransformerImpl());

        mSlidePager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            @SuppressWarnings("unchecked")
            public void onPageSelected(int position) {
                View selectedView = ((DemoPagerAdapter) mSlidePager.getAdapter()).getCurrentView(mSlidePager.getCurrentItem());

                List<View> children = (List<View>) selectedView.getTag();
                if (children != null) {
                    int i = 0;
                    for (final View child : children) {
                        if (child instanceof DayProgressView) {
                            final DayProgressView dayProgressView = (DayProgressView) child;
                            dayProgressView.getCircularBar().addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    dayProgressView.getRightStreak().setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {
                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {
                                }
                            });
                            dayProgressView.getCircularBar().animateProgress(0, (14 * i++), BAR_ANIMATION_TIME);
                        }
                    }
                }
            }

            @Override
            @SuppressWarnings("unchecked")
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    View selectedView = ((DemoPagerAdapter) mSlidePager.getAdapter()).getCurrentView(mSlidePager.getCurrentItem());

                    List<View> children = (List<View>) selectedView.getTag();
                    if (children != null) {
                        for (final View child : children) {
                            if (child instanceof DayProgressView) {
                                final DayProgressView dayProgressView = (DayProgressView) child;
                                dayProgressView.getRightStreak().setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                }

            }
        });
    }

    private View[] initDummyViews() {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View[] views = new View[4];
        views[0] = inflater.inflate(R.layout.page, null);
        views[1] = inflater.inflate(R.layout.page, null);
        views[2] = inflater.inflate(R.layout.page, null);
        views[3] = inflater.inflate(R.layout.page, null);
        return views;
    }
}
