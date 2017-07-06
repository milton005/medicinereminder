package com.mindbees.medicinereminder.Adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.mindbees.medicinereminder.R;

/**
 * Created by User on 06-02-2017.
 */

public class AdapterTutorial extends PagerAdapter {
    private Context context;
    private LayoutInflater mLayoutInflater;
    private TypedArray typedArray;

    public AdapterTutorial(Context context, TypedArray typedArray) {
        this.context = context;
        this.typedArray = typedArray;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return typedArray.length();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.inflate_tutorial, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);

//        imageView.setImageDrawable(typedArray.getDrawable(position));
        Glide.with(context)
                .load(typedArray.getResourceId(position, 0))
                .into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}