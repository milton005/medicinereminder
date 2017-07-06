package com.mindbees.medicinereminder.UI;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.mindbees.medicinereminder.Adapters.AdapterTutorial;
import com.mindbees.medicinereminder.R;
import com.mindbees.medicinereminder.UI.Base.BaseActivity;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * Created by User on 06-02-2017.
 */

public class Tutorial_Activity extends BaseActivity {
    private AdapterTutorial adapterTutorial;
    private ViewPager viewPager;
    private CirclePageIndicator circlePageIndicator;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        initUi();
    }

    private void initUi() {
        viewPager = (ViewPager) findViewById(R.id.pager);
        circlePageIndicator = (CirclePageIndicator) findViewById(R.id.circlePageIndicator);
        adapterTutorial = new AdapterTutorial(this, getDrawerImages());
        viewPager.setAdapter(adapterTutorial);
        circlePageIndicator.setViewPager(viewPager);

    }

    private TypedArray getDrawerImages() {
        return getResources().obtainTypedArray(R.array.tutorial_screens);
    }
}
