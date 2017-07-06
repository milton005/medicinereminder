package com.mindbees.medicinereminder.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.mindbees.medicinereminder.UI.Model.Login.ModelLogin;
import com.mindbees.medicinereminder.UI.Model.register.ModelRegister;
import com.mindbees.medicinereminder.R;
import com.mindbees.medicinereminder.UI.Base.BaseActivity;
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

public class RegisterActivity extends BaseActivity{
EditText name,email,password;
    RelativeLayout regster;
    String Ename,Eemail,Epassword;
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initUi();
        setupUi();

    }

    private void setupUi() {
        name.setFocusable(false);
        name.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                name.setFocusableInTouchMode(true);
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
        email.setFocusable(false);
        email.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                email.setFocusableInTouchMode(true);
                return false;
            }
        });
        regster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable())
                {
                    ValidateData();
                }
                else
                {
                    showToast("Network Unavailable");
                }
            }


        });

    }

    private void ValidateData() {
        if(checkname()&&checkemail()&&checkpassword())
        {
            SubmitData();
        }
    }

    private void SubmitData() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();

        HashMap<String, String> params = new HashMap<>();
        params.put("full_name",Ename);
        params.put("user_email",Eemail);
        params.put("user_password",Epassword);

        APIService service = ServiceGenerator.createService(APIService.class, RegisterActivity.this);
        Call<ModelRegister>call=service.register(params);
        call.enqueue(new Callback<ModelRegister>() {
            @Override
            public void onResponse(Call<ModelRegister> call, Response<ModelRegister> response) {
                if (response.isSuccessful()) {
                    if (pd.isShowing()) {
                        pd.dismiss();
                    }
                    ModelRegister register=response.body();
                    if (register!=null)
                    {
                        if (register.getResult()!=null)
                        {
                            String msg=register.getResult().getMessage();
                            if (register.getResult().getValue()==1)
                            {
                                showSnackBar(msg,true);
                                mHandler.postDelayed(mUpdateTimeTask,300);
                            }
                            else
                            {
                                showSnackBar(msg,false);
                            }

                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<ModelRegister> call, Throwable t) {
                if (pd.isShowing()) {
                    pd.dismiss();
                }
                showSnackBar("Registration Failed",false);
            }
        });
    }
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
//                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            submitLogin(Eemail,Epassword);
        }
    };

    private void submitLogin(String eemail, String epassword) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();

        HashMap<String, String> params = new HashMap<>();
        params.put("user_email",eemail);
        params.put("password",epassword);
        APIService service = ServiceGenerator.createService(APIService.class, RegisterActivity.this);
        Call<ModelLogin>call=service.login(params);
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
                        if (modelLogin.getResult()!=null) {
                            String status = modelLogin.getResult().get(0).getUserStatus();
                            if (status.equals("1")) {
                                String userid = modelLogin.getResult().get(0).getUserId();
                                savePref(Constants.USER_ID, userid);
                                savePref(Constants.EMAIL, Eemail);
                                savePref(Constants.PASSWORD, Epassword);
                                savePref(Constants.TAG_LOGGED_IN_FB, false);
                                savePref(Constants.TAG_ISLOGGED_IN, true);
                                Intent intent = new Intent(RegisterActivity.this, Home.class);
                                startActivity(intent);
                                showSnackBar("Login Success", true);

                            }
                        }
                        else {
                            showSnackBar("LOGIN FAILED",false);
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
        if(Epassword.isEmpty())
        {
            showSnackBar("please Enter a  password",true);
            password.requestFocus();

            return false;
        }
        else
        {
            return true;
        }
    }

    private boolean checkemail() {
        Eemail=email.getText().toString().trim();
        if(Eemail.isEmpty())
        {
            showSnackBar("please Enter Email",false);
            email.requestFocus();
            return  false;
        }
        else {
            if (isValidEmail(Eemail))
            {
                return true;
            }
            else
            {
                showSnackBar("please Enter a valid Email",false);
                password.requestFocus();
                return false;
            }
        }



    }

    private boolean checkname() {
        Ename=name.getText().toString().trim();
        if (Ename.isEmpty())
        {
            showSnackBar("Please Enter a Name",false);
            name.requestFocus();
            return false;
        }
        else
        {
            return true;
        }

    }

    private void initUi() {
        name= (EditText) findViewById(R.id.edittext_reg_name);
        email= (EditText) findViewById(R.id.edittext_reg_email);
        password= (EditText) findViewById(R.id.edittext_reg_password);
        regster= (RelativeLayout) findViewById(R.id.layout_Register);

    }
}
