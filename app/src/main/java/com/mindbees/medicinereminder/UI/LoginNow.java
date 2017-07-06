package com.mindbees.medicinereminder.UI;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mindbees.medicinereminder.App.MedicineReminder;
import com.mindbees.medicinereminder.R;
import com.mindbees.medicinereminder.UI.Base.BaseActivity;
import com.mindbees.medicinereminder.UI.Model.Login.ModelLogin;
import com.mindbees.medicinereminder.UI.retrofit.APIService;
import com.mindbees.medicinereminder.UI.retrofit.ServiceGenerator;
import com.mindbees.medicinereminder.UTILS.Constants;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 03-12-2016.
 */

public class LoginNow extends BaseActivity {
    RelativeLayout login;
    EditText email,password;
    String Eemail,Epassword;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initui();
        setupUi();
    }

    private void setupUi() {
        try
        {
           String em= getPref(Constants.EMAIL);
            if(!em.isEmpty())
            {
                email.setText(em);
            }
            String pw=getPref(Constants.PASSWORD);
            if(!pw.isEmpty())
            {
                password.setText(pw);
            }

        }catch (Exception e)
        {

        }
        email.setFocusable(false);
        email.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                email.setFocusableInTouchMode(true);
                return false;
            }
        });
        password.setFocusable(false);
        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                password.setFocusableInTouchMode(true);
                return false;
            }
        });
        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    View view = getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    Checkdata();
                }
                return false;
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkAvailable())
                {View view = getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    Checkdata();
                }
                else
                {
                    showSnackBar("Network Unavailable",false);
                }



            }
        });
    }

    private void Checkdata() {
      if (Checkemail()&&checkpassword())
      {
          submitData();
      }

    }

    private void submitData() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();
        HashMap<String, String> params = new HashMap<>();
        params.put("user_email",Eemail);
        params.put("password",Epassword);

        APIService service = ServiceGenerator.createService(APIService.class, LoginNow.this);
        Call<ModelLogin> call=service.login(params);
        call.enqueue(new Callback<ModelLogin>() {
            @Override
            public void onResponse(Call<ModelLogin> call, Response<ModelLogin> response) {
                if (pd.isShowing()) {
                    pd.dismiss();
                }
                if (response.isSuccessful())
                {
                    ModelLogin modelLogin=response.body();
                    if (modelLogin!=null)
                    {
                        if (modelLogin.getResult()!=null)
                        {
                            String status=modelLogin.getResult().get(0).getUserStatus();
                            if(status.equals("1")) {
                                String userid = modelLogin.getResult().get(0).getUserId();
                                savePref(Constants.USER_ID, userid);
                                savePref(Constants.EMAIL, Eemail);
                                savePref(Constants.PASSWORD, Epassword);
                                savePref(Constants.TAG_LOGGED_IN_FB, false);
                                savePref(Constants.TAG_ISLOGGED_IN, true);
                                if (getPref(Constants.TAG_ISLOGGED_FIRST, false)) {

                                    Intent i = new Intent(LoginNow.this, Home.class);
                                    startActivity(i);
//                    MainActivity.start(SplashActivity.this);
                                } else {
                                    savePref(Constants.TAG_ISLOGGED_FIRST, true);
                                    startActivity(new Intent(LoginNow.this, Tutorial_Activity.class));
//                    InitialActivity.start(SplashActivity.this);
                                }


                                showSnackBar("Login Success", true);
                            }
                            else
                            {
                                showSnackBar("LOGIN FAILED",false);
                            }

                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<ModelLogin> call, Throwable t) {
                if (pd.isShowing()) {
                    pd.dismiss();
                    showSnackBar("LOGIN FAILED",false);
                }
            }
        });

    }

    private boolean checkpassword() {

       Epassword=password.getText().toString().trim();
        if (Epassword.isEmpty())
        {
            showSnackBar("Password is Required",false);
            password.requestFocus();
            return  false;
        }
        else {
            return true;
        }
    }

    private boolean Checkemail() {
        Eemail=email.getText().toString();
        if (Eemail.isEmpty())
        {
            showSnackBar("Email is Required",false);
            email.requestFocus();
            return false;
        }
        else
        {
            if (isValidEmail(Eemail))
            {
                return true;
            }
            else
            {
                showSnackBar("please Enter a valid email",false);
                email.requestFocus();
                return false;
            }
        }

    }

    private void initui() {
        login= (RelativeLayout) findViewById(R.id.layout_login);
        email= (EditText) findViewById(R.id.edittext_email_login);
        password= (EditText) findViewById(R.id.edittext_password_login);

    }


}
