package com.mindbees.medicinereminder.UI;

import android.graphics.Typeface;
import android.support.v4.app.DialogFragment ;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mindbees.medicinereminder.R;

/**
 * Created by User on 05-01-2017.
 */

public class PopupTips extends DialogFragment {
    TextView t1,t2,t3,t4,t5;
    TextView b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12;
    TextView p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14;
    View view;
    ImageView i;
     static PopupTips newInstance()
    {
        PopupTips p=new PopupTips();
        return p;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.support.v4.app.DialogFragment.STYLE_NORMAL, R.style.Theme_FullScren);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.tipslayout,container,false);
        getDialog().getWindow().requestFeature(1);

//        view.findViewById(R.id.button_close_tips).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent i=new Intent(getActivity(),Detalle.class);
////                startActivity(i);
//                dismiss();
//            }
//        });

        initUI(view);
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                validateform();
//            }
//        });

        return view;
    }

    private void initUI(View view) {
        i= (ImageView) view.findViewById(R.id.button_close_tips);
        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dismiss();
            }
        });
        t1= (TextView) view.findViewById(R.id.tips);
        t2= (TextView) view.findViewById(R.id.tips1);
        t3= (TextView) view.findViewById(R.id.tips2);
        t4= (TextView) view.findViewById(R.id.tips3);
        t5= (TextView) view.findViewById(R.id.tips4);
        b1= (TextView) view.findViewById(R.id.benefits);
        b2= (TextView) view.findViewById(R.id.benefits1);
        b3= (TextView) view.findViewById(R.id.benefits2);
        b4= (TextView) view.findViewById(R.id.benefits3);
        b5= (TextView) view.findViewById(R.id.benefits4);
        b6= (TextView) view.findViewById(R.id.benefits5);
        b7= (TextView) view.findViewById(R.id.benefits6);
        b8= (TextView) view.findViewById(R.id.benefits7);
        b9= (TextView) view.findViewById(R.id.benefits8);
        b10= (TextView) view.findViewById(R.id.benefits9);
        b11= (TextView) view.findViewById(R.id.benefits10);
        b12= (TextView) view.findViewById(R.id.benefits11);
        p1= (TextView) view.findViewById(R.id.proper);
        p2= (TextView) view.findViewById(R.id.proper1);
        p3= (TextView) view.findViewById(R.id.proper2);
        p4= (TextView) view.findViewById(R.id.proper3);
        p5= (TextView) view.findViewById(R.id.proper4);
        p6= (TextView) view.findViewById(R.id.proper5);
        p7= (TextView) view.findViewById(R.id.proper6);
        p8= (TextView) view.findViewById(R.id.proper7);
        p9= (TextView) view.findViewById(R.id.proper8);
        p10= (TextView) view.findViewById(R.id.proper9);
        p11= (TextView) view.findViewById(R.id.proper10);
        p12= (TextView) view.findViewById(R.id.proper11);
        p13= (TextView) view.findViewById(R.id.proper12);
        p14= (TextView) view.findViewById(R.id.proper13);
        Typeface typeface=Typeface.createFromAsset(getContext().getAssets(),"fonts/OpenSans_Bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(getContext().getAssets(),"fonts/tex_gyre_adventor_bold.ttf");
        Typeface typeface2=Typeface.createFromAsset(getContext().getAssets(),"fonts/OpenSans_Regular.ttf");
        t1.setTypeface(typeface);
        p1.setTypeface(typeface);
        b1.setTypeface(typeface);
        t2.setTypeface(typeface2);
        t3.setTypeface(typeface2);
        t4.setTypeface(typeface2);
        t5.setTypeface(typeface2);
        b2.setTypeface(typeface2);
        b3.setTypeface(typeface2);
        b4.setTypeface(typeface2);
        b5.setTypeface(typeface2);
        b6.setTypeface(typeface2);
        b7.setTypeface(typeface2);
        b8.setTypeface(typeface2);
        b9.setTypeface(typeface2);
        b10.setTypeface(typeface2);
        b11.setTypeface(typeface2);
        b12.setTypeface(typeface2);
        p2.setTypeface(typeface2);
        p3.setTypeface(typeface2);
        p4.setTypeface(typeface2);
        p5.setTypeface(typeface2);
        p6.setTypeface(typeface2);
        p7.setTypeface(typeface2);
        p8.setTypeface(typeface2);
        p9.setTypeface(typeface2);
        p10.setTypeface(typeface2);
        p11.setTypeface(typeface2);
        p12.setTypeface(typeface2);
        p13.setTypeface(typeface2);
        p14.setTypeface(typeface2);


//        t1.setText("1.Drink two glasses of water after waking up – helps activate internal organs.\n\n2.Drink one glass of water 30 Minutes before meal – helps digestion\n\n3.Drink one glass of water before taking a bath – helps lower blood pressure\n\n4.Drink one glass of water before sleep – may help to avoid stroke or heart attack.");
//        t2= (TextView) view.findViewById(R.id.tips2);
//        t2.setText("75% of brain is water\n\nregulates body temperature\n\n83% of body’s blood is water\n\nremoves body waste ,cushions bone, 75% of muscles are water\n\nhelps body absorb nutrients\n\nprotects and cushions vital organ\n\nhelp convert food into energy\n\nmoistens oxygen for breathing\n\nhelps carries nutrients and oxygen to cells\n\nhelps to maintain normal kidney function");
//        t3= (TextView) view.findViewById(R.id.tips3);
//        t3.setText("Boost your brain \n\nRelieves Your head ache\n\nSmooth your skin\n\nMore Energy-\n\nModulate blood pressure\n\nMetabolize FAT\n\nBetter DETOX\n\nImprove Your Mood\n\nBetter Physical Performance\n\nMore oxygen &amp; nutrients\n\nBetter Digestion\n\nReduce Constipation\n\nHappier Joints and bones");
    }

}
