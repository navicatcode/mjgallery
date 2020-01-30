package com.mjgallery.mjgallery.widget;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;

public class DecorationLayout extends FrameLayout implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private ImageWatcherHelper mHolder;
    private TextView vDisplayOrigin;
    private View vDownload;
    private int currentPosition;
    private int mPagerPositionOffsetPixels;

    public DecorationLayout(Context context) {
        this(context, null);
    }

    public DecorationLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        final FrameLayout vContainer = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.layout_watcher_decoration, this);
        vDisplayOrigin = vContainer.findViewById(R.id.vDisplayOrigin);
        vDisplayOrigin.setOnClickListener(this);
        vDownload = vContainer.findViewById(R.id.vDownload);
        vDownload.setOnClickListener(this);
    }

    public void attachImageWatcher(ImageWatcherHelper iwHelper) {
        mHolder = iwHelper;
    }


    @Override
    public void onClick(View v) {
        if (mPagerPositionOffsetPixels != 0) return;

        if (v.getId() == R.id.vDownload) {
            final int clickPosition = currentPosition;
            ArmsUtils.makeText(v.getContext().getApplicationContext(), "download [" + clickPosition + "] ");
        } else if (v.getId() == R.id.vDisplayOrigin) {
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
        mPagerPositionOffsetPixels = i1;
        notifyAlphaChanged(v);
        //     setAlpha(1 - v);
    }

    @Override
    public void onPageSelected(int i) {
        currentPosition = i;
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    private void notifyAdapterItemChanged(int position, Uri newUri) {
        if (mHolder != null) {
            final ImageWatcher iw = mHolder.getImageWatcher();
            if (iw != null) {
                iw.notifyItemChanged(position, newUri);
            }
        }
    }

    private void notifyAlphaChanged(float p) {
        if (0 < p && p <= 0.2f) {
            float r = (0.2f - p) * 5;
            setAlpha(r);
        } else if (0.8f <= p && p < 1) {
            float r = (p - 0.8f) * 5;
            setAlpha(r);
        } else if (p == 0) {
            setAlpha(1f);
        } else {
            setAlpha(0f);
        }
    }
}
