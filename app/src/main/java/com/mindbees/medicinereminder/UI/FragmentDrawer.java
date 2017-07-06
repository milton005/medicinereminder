package com.mindbees.medicinereminder.UI;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mindbees.medicinereminder.R;
import com.mindbees.medicinereminder.UI.Base.BaseFragment;

/**
 * Created by User on 01-02-2017.
 */

public class FragmentDrawer extends BaseFragment implements View.OnClickListener {
    LinearLayout layouthome,layoutpill,layoutapp,layoutwater,layoutsettings;
    ImageView imageViewhome,imageViewpill,imageViewapp,imageViewwater,imageViewsettings;
    TextView textViewhome,textViewpill,textViewapp,textViewwater,textViewsettings;
    private NavigationDrawerCallbacks mCallbacks;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallbacks = (NavigationDrawerCallbacks) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.view_drawer, null);
        initUi(v);
        return v;
    }

    private void initUi(View v) {
        layouthome= (LinearLayout) v.findViewById(R.id.menu_layout_home);
        layoutapp= (LinearLayout) v.findViewById(R.id.menu_layout_appointment);
        layoutpill= (LinearLayout) v.findViewById(R.id.menu_layout_pill);
        layoutwater= (LinearLayout) v.findViewById(R.id.menu_layout_water);
        layoutsettings= (LinearLayout) v.findViewById(R.id.menu_layout_settings);
        imageViewhome= (ImageView) v.findViewById(R.id.menu_image_home);
        imageViewapp= (ImageView) v.findViewById(R.id.menu_image_appointment);
        imageViewpill= (ImageView) v.findViewById(R.id.menu_image_pill);
        imageViewwater= (ImageView) v.findViewById(R.id.menu_image_water);
        imageViewsettings= (ImageView) v.findViewById(R.id.menu_image_settings);
        textViewhome= (TextView) v.findViewById(R.id.menu_text_home);
        textViewapp= (TextView) v.findViewById(R.id.menu_text_appointment);
        textViewpill= (TextView) v.findViewById(R.id.menu_text_pill);
        textViewwater= (TextView) v.findViewById(R.id.menu_text_water);
        textViewsettings= (TextView) v.findViewById(R.id.menu_text_settings);
        Typeface typeface=Typeface.createFromAsset(getContext().getAssets(),"fonts/OpenSans_Bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(getContext().getAssets(),"fonts/tex_gyre_adventor_bold.ttf");
        Typeface typeface2=Typeface.createFromAsset(getContext().getAssets(),"fonts/OpenSans_Regular.ttf");
        textViewhome.setTypeface(typeface);
        textViewpill.setTypeface(typeface);
        textViewwater.setTypeface(typeface);
        textViewapp.setTypeface(typeface);
        textViewsettings.setTypeface(typeface);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }
    @Override
    public void onClick(View v) {

    }
}
