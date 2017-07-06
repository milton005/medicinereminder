package com.mindbees.medicinereminder.UI;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment ;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindbees.medicinereminder.R;
import com.mindbees.medicinereminder.UI.Base.BaseActivity;
import com.mindbees.medicinereminder.UI.Base.BaseFragment;
import com.mindbees.medicinereminder.UI.Model.ChangePassword.ModelchangePassword;
import com.mindbees.medicinereminder.UI.retrofit.APIService;
import com.mindbees.medicinereminder.UI.retrofit.ServiceGenerator;
import com.mindbees.medicinereminder.UTILS.Constants;
import com.mindbees.medicinereminder.UTILS.Util;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 09-01-2017.
 */

public class ChangePassword extends DialogFragment {
    View view;
    TextView popuphead;
    EditText newPassword,OldPassword;
    Button save;
    String Enew,Eold;
    ImageView close;
    static ChangePassword NewInstance()
    {
        ChangePassword p=new ChangePassword();
        return p;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.support.v4.app.DialogFragment.STYLE_NORMAL,  R.style.Theme_FullScren);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.popup_edit_password,container,false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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

    private void initUI(final View view) {
        newPassword= (EditText) view.findViewById(R.id.edittext_newpassword);
        newPassword.setFocusable(false);
        newPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                newPassword.setFocusableInTouchMode(true);
                return false;
            }
        });
        OldPassword= (EditText) view.findViewById(R.id.edittext_old_password);
        OldPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                OldPassword.setFocusableInTouchMode(true);
                return false;
            }
        });
        newPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    View view = getActivity().getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    CheckData();
                }
                return false;
            }
        });

        close= (ImageView) view.findViewById(R.id.button_close_popup_passwordedit);
        save= (Button) view.findViewById(R.id.button_save_editpassword);
        popuphead= (TextView) view.findViewById(R.id.change_password_popup_head);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.getUtils().isNetworkAvailable())
                {
                    View view = getActivity().getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    CheckData();
                }
//
            }
        });
        Typeface typeface=Typeface.createFromAsset(getContext().getAssets(),"fonts/OpenSans_Bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(getContext().getAssets(),"fonts/tex_gyre_adventor_bold.ttf");
        Typeface typeface2=Typeface.createFromAsset(getContext().getAssets(),"fonts/OpenSans_Regular.ttf");
        popuphead.setTypeface(typeface);

    }

    private void CheckData() {
        if(checkold()&&checknew())
                {
//                    String password=newPassword.getText().toString().trim();
//                    Snackbar snackbar=   Snackbar.make(view,"Password Changed",Snackbar.LENGTH_SHORT);
//                    View snackbarView = snackbar.getView();
//                    snackbarView.setBackgroundColor(Color.GREEN);
//                    snackbar.show();
//                    Util.getUtils().savePref(Constants.PASSWORD,password);
                    SubmitData();

                }
    }

    private void SubmitData() {
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();
        String Userid=Util.getUtils().getPref(Constants.USER_ID);
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id",Userid);
        params.put("old_password",Eold);
        params.put("new_password",Enew);

        APIService service = ServiceGenerator.createService(APIService.class, getActivity());
        Call<ModelchangePassword>call=service.chnagepassword(params);
        call.enqueue(new Callback<ModelchangePassword>() {
            @Override
            public void onResponse(Call<ModelchangePassword> call, Response<ModelchangePassword> response) {
                if (response.isSuccessful())
                {
                    if(pd.isShowing())
                    {
                        pd.dismiss();
                    }
                    ModelchangePassword modelchangePassword=response.body();
                    if(modelchangePassword!=null)
                    {
                        if (modelchangePassword.getResult()!=null){
                            Integer value=modelchangePassword.getResult().getValue();
                            if (value==1)
                            {
                                String msg=modelchangePassword.getResult().getMessage();
                               Snackbar snackbar=   Snackbar.make(view,msg,Snackbar.LENGTH_SHORT);
                                   View snackbarView = snackbar.getView();
                                Util.getUtils().savePref(Constants.PASSWORD,Enew);
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();
                            }
                            else
                            {
                                String msg=modelchangePassword.getResult().getMessage();
                                Snackbar snackbar=   Snackbar.make(view,msg,Snackbar.LENGTH_SHORT);
                                View snackbarView = snackbar.getView();
                                snackbarView.setBackgroundColor(getResources().getColor(R.color.expired_orange));
                                snackbar.show();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelchangePassword> call, Throwable t) {
                if(pd.isShowing())
                {
                    pd.dismiss();
                }
            }
        });

    }

    private boolean checknew() {
         Enew=newPassword.getText().toString().trim();
       if(Enew.isEmpty())
       {
           Snackbar snackbar=   Snackbar.make(view,"Please enter new password",Snackbar.LENGTH_SHORT);
           View snackbarView = snackbar.getView();
           snackbarView.setBackgroundColor(getResources().getColor(R.color.expired_orange));
           snackbar.show();
           newPassword.requestFocus();
           return false;


       }
        else {
           return true;
       }
    }

    private boolean checkold() {
        Eold=OldPassword.getText().toString().trim();
        if(Eold.isEmpty())
        {
            Snackbar snackbar=   Snackbar.make(view,"Please enter old password",Snackbar.LENGTH_SHORT);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(getResources().getColor(R.color.expired_orange));
            snackbar.show();
            OldPassword.requestFocus();
            return false;
        }

        else
        {
            String old=Util.getUtils().getPref(Constants.PASSWORD);
            if (Eold.equals(old))
            {
                return true;
            }
            else
            {
                Snackbar snackbar=   Snackbar.make(view,"Password Incorrect",Snackbar.LENGTH_SHORT);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(getResources().getColor(R.color.expired_orange));
                snackbar.show();
                OldPassword.requestFocus();
                return  false;
            }

        }
    }

}
