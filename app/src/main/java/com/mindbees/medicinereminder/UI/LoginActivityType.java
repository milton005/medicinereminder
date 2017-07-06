package com.mindbees.medicinereminder.UI;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.mindbees.medicinereminder.R;
import com.mindbees.medicinereminder.UI.Base.BaseActivity;
import com.mindbees.medicinereminder.UI.Model.Fbregistration.ModelFbRegister;
import com.mindbees.medicinereminder.UI.retrofit.APIService;
import com.mindbees.medicinereminder.UI.retrofit.ServiceGenerator;
import com.mindbees.medicinereminder.UTILS.Constants;
import com.mindbees.medicinereminder.UTILS.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityType extends Activity {
RelativeLayout login,fb,register;
    CallbackManager callbackManager;

    String id = "";
    public static Context context;
    BroadcastReceiver breceiver;
    private static final String NAME = "name";
    private static final String ID = "id";
    private static final String EMAIL = "email";
    private static final String GENDER = "gender";
    private static final String FIELDS = "fields";
    private static final String REQUEST_FIELDS = TextUtils.join(",", new String[]{ID, NAME, EMAIL,GENDER});
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_type);
        init();
        facebookSDKInitialize();
        context = this;
        onclicks();
//        try
//        {
//            PackageInfo info=getPackageManager().getPackageInfo("com.mindbees.medicinereminder", PackageManager.GET_SIGNATURES);
//            for(Signature signature:info.signatures)
//            {
//                MessageDigest md=MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash: ", Base64.encodeToString(md.digest(),Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.KEY_FILTER+".ACTION_RQST_FNSH");
        breceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO Auto-generated method stub

                finish();

            }
        };
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

//                showToast("Login success");

//                tools.showLog(loginResult.getAccessToken() + "", 1);
//
////                isFbLogin = true;
                fetchUserInfo();

            }

            @Override
            public void onCancel() {
//                showToast("Login cancel");

            }

            @Override
            public void onError(FacebookException e) {
//                Intent intent=new Intent(getApplicationContext(),Home.class);
//                startActivity(intent);
//             showToast ("Login error");

            }
        });

       registerReceiver(breceiver, intentFilter);

    }
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        unregisterReceiver(breceiver);
    }
    private void fetchUserInfo() {
        final AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            GraphRequest request = GraphRequest.newMeRequest(
                    accessToken, new GraphRequest.GraphJSONObjectCallback() {

                        @Override
                        public void onCompleted(JSONObject object,
                                                GraphResponse response) {
                            // TODO Auto-generated method stub

//                            tools.showLog(object+"", 2);
//                            tools.showLog(response+"",3);

                            parseUserInfo(object);


//	                            user = me;
//	                            updateUI();

                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString(FIELDS, REQUEST_FIELDS);
            request.setParameters(parameters);
            GraphRequest.executeBatchAsync(request);

//            LoginManager.getInstance().logInWithPublishPermissions(
//                    this,
//                    Arrays.asList(PERMISSION));
        } else {

//            tools.showLog("access token is null", 2);
//            user = null;
        }
    }

    private void parseUserInfo(JSONObject me) {

        String name = "";

        String birthday = "";
        String gender ="";
        String pictureUrl=null;
        try
        {  id = me.getString("id");

            name = me.getString("name");
            name = me.getString("name");

        }catch (JSONException e)
        {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {

                    }
                });

                URL url = null;

//                try {
//                    url = new URL(imgUrl);
//                    mIcon1 = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//                } catch (MalformedURLException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }catch (IOException e) {
//                    // TODO: handle exception
//                }

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        getwebservice(id);


                    }
                });


            }
        }).start();

    }

    private void getwebservice( final String id) {

       final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();
        HashMap<String, String> params = new HashMap<>();
        params.put("fb_id", id);
        APIService service = ServiceGenerator.createService(APIService.class,LoginActivityType.this);
        Call<ModelFbRegister> call=service.fbregister(params);
        call.enqueue(new Callback<ModelFbRegister>() {
            @Override
            public void onResponse(Call<ModelFbRegister> call, Response<ModelFbRegister> response) {
                if (response.isSuccessful())
                {
                    if (pd.isShowing())
                    {
                        pd.dismiss();
                    }
                    ModelFbRegister fbregister=response.body();
                    if (fbregister!=null)
                    {
                        if (fbregister.getResult()!=null)
                        {
                            if (fbregister.getResult().getValue()==1)
                            {
                                String message=fbregister.getResult().getMessage();
                                String Userid=fbregister.getResult().getUserId();
                               Util.getUtils(). savePref(Constants.USER_ID,Userid);
                               Util.getUtils(). savePref(Constants.TAG_ISLOGGED_IN,true);
                               Util.getUtils(). savePref(Constants.TAG_LOGGED_IN_FB,true);
                                Util.getUtils().showToastMessage("LOGIN SUCCESS");
                                Intent bIntent = new Intent();
                                bIntent.setAction(Constants.KEY_FILTER+".ACTION_RQST_FNSH");
                                sendBroadcast(bIntent);
                                Intent intent=new Intent(getApplicationContext(),Home.class);
                                startActivity(intent);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelFbRegister> call, Throwable t) {
                if (pd.isShowing())
                {
                    pd.dismiss();
                }
              Util.getUtils().showToastMessage("LOGIN FAILED");

            }
        });
    }


    private void facebookSDKInitialize() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
    }

    private void onclicks() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivityType.this,LoginNow.class);
                startActivity(i);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivityType.this,RegisterActivity.class);
                startActivity(i);
            }
        });
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.getUtils().isNetworkAvailable()) {

                    LoginManager.getInstance().logInWithReadPermissions(LoginActivityType.this, Arrays.asList("public_profile", "user_friends", "email"));
                }
            }
        });
    }

    private void init() {
       login= (RelativeLayout) findViewById(R.id.layout_loginNow);
        fb= (RelativeLayout) findViewById(R.id.layout_fblogin);
        register= (RelativeLayout) findViewById(R.id.layout_RegisterNow);


    }


}
