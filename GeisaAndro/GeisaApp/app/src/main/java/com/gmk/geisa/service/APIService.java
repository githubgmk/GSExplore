package com.gmk.geisa.service;

import com.androidadvance.androidsurvey.Answers;
import com.androidadvance.androidsurvey.models.Survey;
import com.gmk.geisa.model.Post.rCallPlan;
import com.gmk.geisa.model.Post.rCallPlanComplain;
import com.gmk.geisa.model.Post.rCallPlanDemo;
import com.gmk.geisa.model.Post.rCallPlanNote;
import com.gmk.geisa.model.Post.rCallPlanPost;
import com.gmk.geisa.model.Post.rCallPlanSample;
import com.gmk.geisa.model.Post.rCustomerAdd;
import com.gmk.geisa.model.Post.rPoPost;
import com.gmk.geisa.model.Post.rUserLogin;
import com.gmk.geisa.model.PostResult;
import com.gmk.geisa.model.mArea;
import com.gmk.geisa.model.mBiCsType;
import com.gmk.geisa.model.mCallPlan;
import com.gmk.geisa.model.mCallPlanNote;
import com.gmk.geisa.model.mChannel;
import com.gmk.geisa.model.mComplain;
import com.gmk.geisa.model.mCustStatus;
import com.gmk.geisa.model.mCustomer;
import com.gmk.geisa.model.mCustomerClasification;
import com.gmk.geisa.model.mDemo;
import com.gmk.geisa.model.mDistributor;
import com.gmk.geisa.model.mDistributorBranch;
import com.gmk.geisa.model.mLevel;
import com.gmk.geisa.model.mPO;
import com.gmk.geisa.model.mPesan;
import com.gmk.geisa.model.mProduct;
import com.gmk.geisa.model.mProductPriceDiskon;
import com.gmk.geisa.model.mPromo;
import com.gmk.geisa.model.mRofo;
import com.gmk.geisa.model.mRofoAktualisasi;
import com.gmk.geisa.model.mRofoTarget;
import com.gmk.geisa.model.mSales;
import com.gmk.geisa.model.mSample;
import com.gmk.geisa.model.mStock;
import com.gmk.geisa.model.mStockBranch;
import com.gmk.geisa.model.mTodoList;
import com.gmk.geisa.model.mTracking;
import com.gmk.geisa.model.mTrackingPicture;
import com.gmk.geisa.model.mUnit;
import com.gmk.geisa.model.mZone;
import com.gmk.geisa.model.Post.rCustomerPost;
import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface APIService {
    @FormUrlEncoded
    @POST
    Call<PostResult> registerUser(@FieldMap Map<String, String> params, @Url String url);

    @GET("api/Customer/getCustomer")
    Call<ArrayList<mCustomer>> getAllCustomer(@QueryMap Map<String, String> params);

    @GET("api/Customer/getCustomerById")
    Call<mCustomer> getCustomerById(@QueryMap Map<String, String> parameters);

    @GET("api/Customer/GetCustomerClasification")
    Call<mCustomerClasification> getCustomerClasificationById(@QueryMap   Map<String, String> parameters);

    @GET("api/Customer/checkNewUpdateCustomer")
    Call<ArrayList<mCustomer>> getUpdateAllCustomer(@QueryMap Map<String, String> params);

    @POST("api/Customer/updateCustomer")
    Call<mCustomer> updateCustomer(@Body mCustomer customer);

    @POST("api/Customer/updateCustomerDownload")
    Call<ArrayList<PostResult>> setUpdateAllCustomer(@Body rCustomerPost data);

    @POST("api/Customer/addNewCustomer")
    Call<mCustomer> addNewCustomer(@Body rCustomerAdd data);

    @POST("api/PO/addNewPO")
    Call<mPO> updatePO(@Body mPO po);

    @POST("api/PO/salesConfirmPO")
    Call<mPO> confirmPO(@Body mPO po);

    @POST("api/PO/updatePoStatus")
    Call<ArrayList<PostResult>> setUpdateAllPoStatus(@Body rPoPost parameters);

    @GET("api/PO/GetPPActive")
    Call<ArrayList<mPO>> GetPPActiveByCust(@QueryMap Map<String, String> params);

    @GET("api/PO/checkNewUpdatePo")
    Call<ArrayList<mPO>> cekPoUpdate(@QueryMap Map<String, String> params);

    @POST("api/CallPlan/addNewCallPlan")
    Call<ArrayList<mCallPlan>> updateCallPlan(@Body rCallPlan data);

    @POST("api/CallPlan/updateCallPlan")
    Call<ArrayList<mCallPlan>> updateCallPlanRealisasi(@Body rCallPlan data);

    @GET("api/CallPlan/getCallPlanByDate")
    Call<ArrayList<mCallPlan>> getCallPlan(@QueryMap Map<String, String> params);

    @GET("api/CallPlan/checkNewUpdateCallPlan")
    Call<ArrayList<mCallPlan>> getUpdateAllCallPlan(@QueryMap Map<String, String> params);

    @GET("api/CallPlan/checkNewUpdateCallPlanBetween")
    Call<ArrayList<mCallPlan>> getUpdateAllCallPlanBetween(@QueryMap Map<String, String> params);

    @GET("api/CallPlan/checkNewUpdateCallPlanNoteBetween")
    Call<ArrayList<mCallPlanNote>> getUpdateAllCallPlanNoteBetween(@QueryMap Map<String, String> params);

    @POST("api/CallPlan/updateCallPlanStatus")
    Call<ArrayList<PostResult>> setUpdateAllCallPlanStatus(@Body rCallPlanPost data);

    @POST("api/CallPlan/updateTransCallPlanNote")
    Call<ArrayList<mCallPlanNote>> updateTransCallPlanNote(@Body rCallPlanNote data);

    @POST("api/CallPlan/updateTransCallPlanDemo")
    Call<ArrayList<mDemo>> updateTransCallPlanDemo(@Body rCallPlanDemo data);

    @POST("api/CallPlan/updateTransCallPlanComplain")
    Call<ArrayList<mComplain>> updateTransCallPlanComplain(@Body rCallPlanComplain data);

    @GET("api/CallPlan/getComplainBetween")
    Call<ArrayList<mComplain>> getUpdateAllComplainBetween(@QueryMap  Map<String, String> params);

    @GET("api/CallPlan/getDemoBetween")
    Call<ArrayList<mDemo>> getUpdateAllDemoBetween(@QueryMap  Map<String, String> params);

    @POST("api/CallPlan/updateTransCallPlanSample")
    Call<ArrayList<mSample>> updateTransCallPlanSample(@Body rCallPlanSample data);

    @POST("api/CallPlan/getTransCallPlanSample")
    Call<ArrayList<mSample>> getTransCallPlanSampleCustomer(@QueryMap Map<String, String> params);

    @GET("api/CallPlan/getSampleBetween")
    Call<ArrayList<mSample>> getUpdateAllSampleBetween(@QueryMap Map<String, String> params);

    @GET("api/Master/getProduct")
    Call<ArrayList<mProduct>> getDataProduct(@QueryMap Map<String, String> params);

    @GET("api/Master/getProductPriceDiskon")
    Call<ArrayList<mProductPriceDiskon>> getDataProductPriceDiskon(@QueryMap Map<String, String> params);

    @GET("api/Master/getPromo")
    Call<ArrayList<mPromo>> getDataPromo(@QueryMap Map<String, String> params);

    @GET("api/CallPlan/GetTodoList")
    Call<ArrayList<mTodoList>> getDataTodoList(@QueryMap  Map<String, String> params);

    @POST("api/CallPlan/updateTodoList")
    Call<mTodoList> updateTodoList(@Body mTodoList todo);

    @POST("api/CallPlan/updateTodoListStatusNew")
    Call<ArrayList<PostResult>> setUpdateAllTodoListStatusNew(@Body rCallPlanPost data);

    @POST("api/Register/putSales")
    Call<ArrayList<mSales>> getUser(@Body rUserLogin data);


    @POST("api/Register/updateSalesDetail")
    Call<mSales> updateUserServer(@Body mSales params);//not yet implement not a primary

    @FormUrlEncoded
    @POST("api/Register/ForgotPassword")
    Call<PostResult> UserForgetPassword(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("api/Register/updateSalesLogin")
    Call<PostResult> updateUserLoginServer(@FieldMap Map<String, String> params);

    @POST("api/TransTracking/addTracking")
    Call<PostResult> insertTrackingServer(@Body mTracking params);

    @GET("api/Master/getArea")
    Call<ArrayList<mArea>> getDataArea(@QueryMap Map<String, String> params);

    @GET("api/Master/getLevel")
    Call<ArrayList<mLevel>> getDataLevel(@QueryMap Map<String, String> params);

    @GET("api/Master/getUnit")
    Call<ArrayList<mUnit>> getDataUnit(@QueryMap Map<String, String> params);

    @GET("api/Master/getChannel")
    Call<ArrayList<mChannel>> getDataChannel(@QueryMap Map<String, String> params);

    @GET("api/Master/getZone")
    Call<ArrayList<mZone>> getDataZone(@QueryMap Map<String, String> params);

    @GET("api/Master/getCustStatus")
    Call<ArrayList<mCustStatus>> getDataCustStatus(@QueryMap Map<String, String> params);

    @GET("api/Master/getDist")
    Call<ArrayList<mDistributor>> getDataDist(@QueryMap Map<String, String> params);

    @GET("api/Master/getDistBranch")
    Call<ArrayList<mDistributorBranch>> getDataDistBranch(@QueryMap Map<String, String> params);

    @GET("api/Master/GetProductStockDist")
    Call<ArrayList<mStockBranch>> getDataStock(@QueryMap Map<String, String> params);

    @GET("api/Master/GetProductStockProduct")
    Call<ArrayList<mStock>> getDataStockProduct(@QueryMap Map<String, String> params);

    @GET("api/Master/getBiType")
    Call<ArrayList<mBiCsType>> getDataBiType(@QueryMap  Map<String, String> params);

    @GET("api/Rofo/getRofo")
    Call<ArrayList<mRofo>> getDataRofo(@QueryMap Map<String, String> params);

    @GET("api/Rofo/getTarget")
    Call<ArrayList<mRofoTarget>> getDataRofoTarget(@QueryMap Map<String, String> params);

    @GET("api/Rofo/geAktualisasi")
    Call<ArrayList<mRofoAktualisasi>> getDataRofoAktualisasi(@QueryMap  Map<String, String> params);

    @POST("api/Rofo/addNewRofo")
    Call<ArrayList<mRofo>> updateRofo(@Body ArrayList<mRofo> po);

    @GET("api/Other/getSurvey")
    Call<ArrayList<Survey>> GetSurvey(@QueryMap  Map<String, String> params);

    @POST("api/Other/sendSurvey")
    Call<Answers.AllValue> sendSurvey(@Body Answers.AllValue answer);

    @GET("api/Other/getPesan")
    Call<ArrayList<mPesan>> GetPesan(@QueryMap  Map<String, String> params);

    @POST("api/Other/sendPesan")
    Call<mPesan> sendPesan(@Body mPesan pesan);

    //save image
    @Multipart
    @POST("api/TransTracking/PostUserImage")
    Call<ResponseBody> uploadFile(@Part MultipartBody.Part file, @Part("name") RequestBody name,
                                  @Part("TrackingPictureId") RequestBody  TrackingPictureId, @Part("Picture")RequestBody Picture,
                                  @Part("PictureRef")RequestBody  PictureRef, @Part("StatusBattery") RequestBody StatusBattery,
                                  @Part("Note") RequestBody Note, @Part("CreatedDate") RequestBody CreatedDate,
                                  @Part("CreatedBy") RequestBody CreatedBy);

    @Multipart
    @POST("api/TransTracking/PostUserImages")
    Call<PostResult> uploadFiles(@Part MultipartBody.Part file, @Part("name") RequestBody name,
                                  @Part("TrackingPictureId") RequestBody  TrackingPictureId,@Part("Picture")RequestBody Picture,
                                  @Part("PictureRef")RequestBody  PictureRef, @Part("StatusBattery") RequestBody StatusBattery,
                                  @Part("Note") RequestBody Note,@Part("CreatedDate") RequestBody CreatedDate,
                                  @Part("CreatedBy") RequestBody CreatedBy);

    @Multipart
    @POST("api/TransTracking/PostUserImages2")
    Call<PostResult> uploadFiles(@Part MultipartBody.Part file, @Part("name") mTrackingPicture data);



}
