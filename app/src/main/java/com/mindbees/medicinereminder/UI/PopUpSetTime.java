package com.mindbees.medicinereminder.UI;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment ;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.mindbees.medicinereminder.R;
import com.mindbees.medicinereminder.UTILS.Constants;
import com.mindbees.medicinereminder.UTILS.Util;

/**
 * Created by User on 11-01-2017.
 */

public class PopUpSetTime extends DialogFragment {
    ImageView b;
    RelativeLayout Done;
    View view;
    TextView H1,h2,h3;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    Spinner s1,s2;
    String []interval1={"9 pm","10 pm","11 pm","12 am"};
    String []interval2={"6 am","5 am","7 am","8 am"};
    static PopUpSetTime newInstance()
    {
        PopUpSetTime p=new PopUpSetTime();
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
        view=inflater.inflate(R.layout.set_sleep_time,container,false);
        getDialog().getWindow().requestFeature(1);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

//        view.findViewById(R.id.button_close_tips).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent i=new Intent(getActivity(),Detalle.class);
////                startActivity(i);
//                dismiss();
//            }
//        });

        initUI(view);
        ArrayAdapter<String> adapter1 =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, interval1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(adapter1);
        ArrayAdapter<String> adapter2 =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, interval2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s2.setAdapter(adapter2);
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                validateform();
//            }
//        });
        try{
            int i=Util.getUtils().getPref(Constants.SET_SLEEP_FROM);
            s1.setSelection(i);
            int k=Util.getUtils().getPref(Constants.SET_SLEEP_TO);
            s2.setSelection(k);

        }catch (Exception e)
        {

        }
       setupui();
        return view;
    }
    public void setFragmentView(Fragment fragment) {

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, fragment)
                .commit();
    }
    private void setupui() {
//   s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//       @Override
//       public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//           switch (position)
//           {
//               case 0:
//                   Util.getUtils().savePref(Constants.SET_SLEEP_FROM,0);
//                   break;
//               case 1:Util.getUtils().savePref(Constants.SET_SLEEP_FROM,1);
//                   break;
//               case 2:Util.getUtils().savePref(Constants.SET_SLEEP_FROM,2);
//                   break;
//               case 3:Util.getUtils().savePref(Constants.SET_SLEEP_FROM,3);
//                   break;
//               default:
//                   break;
//           }
//
//       }
//
//       @Override
//       public void onNothingSelected(AdapterView<?> parent) {
//
//       }
//   });
//        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                switch (position){
//                    case 0:
//                        Util.getUtils().savePref(Constants.SET_SLEEP_TO,0);
//                        break;
//                    case 1:Util.getUtils().savePref(Constants.SET_SLEEP_TO,1);
//                        break;
//                    case 2:Util.getUtils().savePref(Constants.SET_SLEEP_TO,2);
//                        break;
//                    case 3:Util.getUtils().savePref(Constants.SET_SLEEP_TO,3);
//                        break;
//                    default:
//                        break;
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
    }

    private void initUI(View view) {
        b= (ImageView) view.findViewById(R.id.button_close_popup_set_sleep_time);
        s1= (Spinner) view.findViewById(R.id.Spinnner_set_sleep_time_from);
        s2= (Spinner) view.findViewById(R.id.Spinnner_set_sleep_time_to);
        Done= (RelativeLayout) view.findViewById(R.id.layout_Done_sleep);
        H1= (TextView) view.findViewById(R.id.SetTimePopuPHEad);
        h2= (TextView) view.findViewById(R.id.textSpinnner_set_sleep_time_to);
        h3= (TextView) view.findViewById(R.id.textSpinnner_set_sleep_time_from);
        Typeface typeface=Typeface.createFromAsset(getContext().getAssets(),"fonts/OpenSans_Bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(getContext().getAssets(),"fonts/tex_gyre_adventor_bold.ttf");
        Typeface typeface2=Typeface.createFromAsset(getContext().getAssets(),"fonts/OpenSans_Regular.ttf");
        H1.setTypeface(typeface);
        h2.setTypeface(typeface2);
        h3.setTypeface(typeface2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),MedicineReminderActivity.class);
                intent.putExtra(Constants.FRAG_TYPE, 4);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                getActivity().overridePendingTransition(0, 0);
                getActivity().finish();

                getActivity().overridePendingTransition(0, 0);
                startActivity(intent);
            }
        });
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.getUtils().savePref(Constants.SET_SLEEP_FROM,s1.getSelectedItemPosition());
                Util.getUtils().savePref(Constants.SET_SLEEP_TO,s2.getSelectedItemPosition());
                Intent intent=new Intent(getActivity(),MedicineReminderActivity.class);
                intent.putExtra(Constants.FRAG_TYPE, 4);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                getActivity().overridePendingTransition(0, 0);
                getActivity().finish();

                getActivity().overridePendingTransition(0, 0);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onResume() {

        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){

                    Intent intent=new Intent(getActivity(),MedicineReminderActivity.class);
                    intent.putExtra(Constants.FRAG_TYPE, 3);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    getActivity().overridePendingTransition(0, 0);
                    getActivity().finish();

                    getActivity().overridePendingTransition(0, 0);
                    startActivity(intent);
                    return true;

                }

                return false;
            }
        });
    }

}
