package com.mindbees.medicinereminder.UI.Model.ChangePassword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by User on 10-02-2017.
 */

public class ModelchangePassword implements Serializable {
    @SerializedName("result")
    @Expose
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
