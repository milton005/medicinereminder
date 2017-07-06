package com.mindbees.medicinereminder.UI.retrofit;



import com.mindbees.medicinereminder.UI.Model.ChangePassword.ModelchangePassword;
import com.mindbees.medicinereminder.UI.Model.Fbregistration.ModelFbRegister;
import com.mindbees.medicinereminder.UI.Model.Login.ModelLogin;
import com.mindbees.medicinereminder.UI.Model.register.ModelRegister;
import com.mindbees.medicinereminder.UTILS.Urls;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * Created by tony on 14/06/2016.
 */
public interface APIService {
@FormUrlEncoded
    @POST(Urls.REGISTER_URL)
    Call<ModelRegister>register(@FieldMap HashMap<String,String>params);
    @FormUrlEncoded
    @POST(Urls.LOGIN_URL)
    Call<ModelLogin>login(@FieldMap HashMap<String,String>params);
    @FormUrlEncoded
    @POST(Urls.FBLOGIN)
    Call<ModelFbRegister>fbregister(@FieldMap HashMap<String,String>params);
    @FormUrlEncoded
    @POST(Urls.CHANGE_PASSWORD)
    Call<ModelchangePassword>chnagepassword(@FieldMap HashMap<String,String>params);
//    @Headers("Oakey:try")
//   @FormUrlEncoded
//   @POST(Urls.register)
//   Call<Modelregister> register(@FieldMap HashMap<String, String> params);
//    @Headers("Oakey:try")
//    @FormUrlEncoded
//    @POST(Urls.register)
//    Call<Modellogin> login_email(@FieldMap HashMap<String, String> params);
//    @Headers("Oakey:try")
//    @FormUrlEncoded
//    @POST(Urls.register)
//    Call<Modelallreminder> all_reminder(@FieldMap HashMap<String, String> params);
//    @Headers("Oakey:try")
//    @FormUrlEncoded
//    @POST(Urls.register)
//    Call<Modelsetreminder> add_reminder(@FieldMap HashMap<String, String> params);
//    @Headers("Oakey:try")
//    @FormUrlEncoded
//    @POST(Urls.register)
//    Call<Modelloadcategory> load_category(@FieldMap HashMap<String, String> params);
// @Headers("Oakey:try")
// @FormUrlEncoded
// @POST(Urls.register)
// Call<ModelLoadIncome> load_income(@FieldMap HashMap<String, String> params);
//    @Headers("Oakey:try")
//    @FormUrlEncoded
//    @POST(Urls.register)
//    Call<Modelbanks> load_banks(@FieldMap HashMap<String, String> params);
// @Headers("Oakey:try")
// @FormUrlEncoded
// @POST(Urls.register)
// Call<Modeladdaccount> add_account(@FieldMap HashMap<String, String> params);
// @Headers("Oakey:try")
// @FormUrlEncoded
// @POST(Urls.register)
// Call<Modeloadaccount> load_accounts(@FieldMap HashMap<String, String> params);
// @Headers("Oakey:try")
// @FormUrlEncoded
// @POST(Urls.register)
// Call<Modeladdrecord> add_record(@FieldMap HashMap<String, String> params);
// @Headers("Oakey:try")
// @FormUrlEncoded
// @POST(Urls.register)
// Call<Modeldelaccount> del_account(@FieldMap HashMap<String, String> params);
// @Headers("Oakey:try")
// @FormUrlEncoded
// @POST(Urls.register)
// Call<Modeladdcategory> add_category(@FieldMap HashMap<String, String> params);
// @Headers("Oakey:try")
// @FormUrlEncoded
// @POST(Urls.register)
// Call<Modelgetprofile> get_profile(@FieldMap HashMap<String, String> params);
// @Headers("Oakey:try")
// @FormUrlEncoded
// @POST(Urls.register)
// Call<Modelupdate_profile> update_profile(@FieldMap HashMap<String, String> params);
// @Headers("Oakey:try")
// @FormUrlEncoded
// @POST(Urls.register)
// Call<Modelupdate_profile> update_password(@FieldMap HashMap<String, String> params);
// @Headers("Oakey:try")
// @FormUrlEncoded
// @POST(Urls.register)
// Call<Modelcontact> contactus(@FieldMap HashMap<String, String> params);
// @Headers("Oakey:try")
// @FormUrlEncoded
// @POST(Urls.register)
// Call<ModelYearlyCategoryReport> yearlycategoryreport(@FieldMap HashMap<String, String> params);
// @Headers("Oakey:try")
// @FormUrlEncoded
// @POST(Urls.register)
// Call<ModelYearlyReport> yearlyreport(@FieldMap HashMap<String, String> params);
// @Headers("Oakey:try")
// @FormUrlEncoded
// @POST(Urls.register)
// Call<Modeloadaccount> load_status(@FieldMap HashMap<String, String> params);
// @Headers("Oakey:try")
// @FormUrlEncoded
// @POST(Urls.register)
// Call<ModelFblogin> fb_login(@FieldMap HashMap<String, String> params);
//
//
//
//    @FormUrlEncoded
//    @POST(Urls.register)
//    Call<ModelRegister> register(@FieldMap HashMap<String, String> params);
//
//    @FormUrlEncoded
//    @POST(Urls.facebookRegister)
//    Call<ModelFbRegister> facebookRegister(@FieldMap Map<String, String> map);
//
//    @FormUrlEncoded
//    @POST(Urls.facebookLogin)
//    Call<ModelLogin> facebookLogin(@FieldMap Map<String, String> map);
//
//    @POST(Urls.getJobList)
//    Call<ModelJobs> getJobs(@QueryMap Map<String, String> map);
//
//    @FormUrlEncoded
//    @POST(Urls.getnotification)
//    Call<ModelJobs>getnotification(@FieldMap Map<String, String> map);
//    @FormUrlEncoded
//    @POST(Urls.getaboutus)
//    Call<ModelAboutUs>getaboutus(@FieldMap Map<String, String> map);
//    @FormUrlEncoded
//    @POST(Urls.getnotificationcount)
//    Call<ModelNotificationCount>getnotificationcount(@FieldMap Map<String, String> map);
//    @FormUrlEncoded
//    @POST(Urls.getlogout)
//    Call<Modellogout>getlogout(@FieldMap Map<String, String> map);
//    @FormUrlEncoded
//    @POST(Urls.getPutYourSkillsToWork)
//    Call<ModelPutYourSkillsToWork> getJobseekers(@FieldMap Map<String, String> map);
//    @FormUrlEncoded
//    @POST(Urls.getPlants)
//    Call<ModelPlants> getPlants(@FieldMap Map<String, String> map);
//    @POST(Urls.getplant1)
//    Call<ModelPlants> getPlants1(@QueryMap Map<String, String> map);
//
//    @POST(Urls.getRequirements)
//    Call<ModelRequirements> getRequirements(@QueryMap Map<String, String> map);
//
//    @FormUrlEncoded
//    @POST(Urls.getJobDetail)
//    Call<ModelJobDetail> getJobDetail(@FieldMap Map<String, String> map);
//
//
//    @FormUrlEncoded
//    @POST(Urls.getPutPlantDetail)
//    Call<ModelPutPlantDetail> getPutPlantDetail(@FieldMap Map<String, String> map);
//
//    @FormUrlEncoded
//    @POST(Urls.updateProfile)
//    Call<ModelUpadateJobprofile> getUpdateProfile(@FieldMap Map<String, String> map);
//
//    @FormUrlEncoded
//    @POST(Urls.jobSeekerDetail)
//    Call<ModelJobSeekerDetail> getJobSeekerDetail(@FieldMap Map<String, String> map);
//
//    @FormUrlEncoded
//    @POST(Urls.applyNow)
//    Call<ModelApplyNow> getApplyNow(@FieldMap Map<String, String> map);

}
