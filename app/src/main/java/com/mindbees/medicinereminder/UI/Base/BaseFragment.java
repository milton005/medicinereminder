package com.mindbees.medicinereminder.UI.Base;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mindbees.medicinereminder.UTILS.Util;


/**
 * Created by user on 7/26/2016.
 */
public class BaseFragment extends Fragment {
    ProgressDialog pDialog;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private ProgressDialog getProgressDialog() {
        if (this.pDialog == null) {
//            this.pDialog = CustomProgressDialog.nowRunningDialog(getActivity());
        }
        return this.pDialog;
    }

    public void showProgress() {
        getProgressDialog();

        if (pDialog != null)
            this.pDialog.show();
    }

    public void hideProgress() {
        if (pDialog != null && this.pDialog.isShowing()) {
            this.pDialog.dismiss();
            pDialog = null;
        }
    }

    public  boolean isValidEmail(CharSequence target) {
        return Util.getUtils().isValidEmail(target);
    }

    public void showSnackBar(String msg, boolean isSuccess){

        Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT);
        View view  = snackbar.getView();
        TextView textView = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        if (isSuccess){
            view.setBackgroundColor(Color.GREEN);
        }else {
            view.setBackgroundColor(Color.RED);
        }

        snackbar.show();

//        Snackbar.make(context.findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT).show();
    }

    public void showLog(String msg){


    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void delete(String key) {
        Util.getUtils().delete(key);
    }


    public void savePref(String key, Object value) {
        Util.getUtils().savePref(key, value);
    }



    @SuppressWarnings("unchecked")
    public <T> T getPref(String key) {
        return (T) Util.getUtils().getPref(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T getPref(String key, T defValue) {
        return Util.getUtils().getPref(key, defValue);
    }

    public boolean isPrefExists(String key) {
        return Util.getUtils().isPrefExists(key);
    }

//    public void setupDownloadingProcedure(String fileUrl, String type, int format) {
//        // TODO Auto-generated method stub
//        Util.getUtils().setupDownloadingProcedure(fileUrl, type, getActivity(), format);
//
//    }

//    public void openWebPage(String url) {
//        Util.getUtils().openWebPage(url, getActivity());
//    }
//    public void composeEmail(String[] addresses) {
//        Util.getUtils().composeEmail(addresses, getActivity());
//    }
//    public void dialPhoneNumber(String ph) {
//        Util.getUtils().dialPhoneNumber(ph, getActivity());
//    }


}
