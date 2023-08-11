package com.worldpeak.chnsmilead.net;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ICommonApi {

    /**
     * 这里需要稍作说明，@GET注解就表示get请求，@Query表示请求参数，将会以key=value的方式拼接在url后面    @QueryMap 传map 把key value丢进去
     * @POST注解就表示post请求，@Field表示请求参数，将会以表单的方式提交  @FieldMap 意思一样
     * 除此之外还有 @Body @Path 添加header等方法
     * @return
     */

    /**
     * 获取产品分类
     *
     * @return
     */
    @GET("platformRegionUser/default/list")
    Call<BaseResponse> platformRegionUser(@Query("account") String account, @Query("isStaff") String isStaff);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("app-api/mobileLogin")
    Call<BaseResponse> mobileLogin(@Body RequestBody body);

    @GET("app-api/getLoginUser")
    Call<BaseResponse> getLoginUser(@Header("Authorization") String token);

    /**
     * 查询投诉建议类型
     *
     * @param token
     * @param type  类型（1：建议，2：投诉）
     */
    @GET("school-admin-api/adminComplaintPropose/getKinds")
    Call<BaseResponse> getComplaintAdviceType(@Header("Authorization") String token, @Query("type") int type);


    /**
     * 行政-查询投诉/建议 列表
     *
     * @param token
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("school-admin-api/adminComplaintPropose/pageCp")
    Call<BaseResponse> getComplaintAdviceList(@Header("Authorization") String token, @Body RequestBody body);


    /**
     * 行政端-查询投诉建议详情
     *
     * @param token
     * @param id    投诉建议主键id
     * @return
     */
    @GET("school-admin-api/adminComplaintPropose/detailAdmin")
    Call<BaseResponse> getComplaintAdviceDetail(@Header("Authorization") String token, @Query("id") String id);

    @POST("user/login")
    @FormUrlEncoded
    Call<BaseResponse> testLogin(@Field("username") String username, @Field("password") String password);

    /**
     * 公文审批列表-全部流程
     *
     * @param token
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("school-admin-api/admin/reportDocument/reportDocumentList")
    Call<BaseResponse> getAllProcessList(@Header("Authorization") String token, @Body RequestBody body);


    /**
     * 行政-上报公文详细信息
     */
    @GET("school-admin-api/admin/reportDocument/adminDetailPc")
    Call<BaseResponse> getDocumentApprovalDetail(
            @Header("Authorization") String token,
            @Query("formId") String formId
    );

    /**
     * 行政-上报公文-审批
     *
     * @param token
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("school-admin-api/admin/reportDocument/reply")
    Call<BaseResponse> documentApproval(@Header("Authorization") String token, @Body RequestBody body);

    /**
     * app端-分页查询信息下发
     *
     * @param token
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("school-admin-api/admin/infoDown/pageInfoDownApp")
    Call<BaseResponse> getNoticeDeliveryList(@Header("Authorization") String token, @Body RequestBody body);


    /**
     * app-查询信息下发详情
     */
    @GET("school-admin-api/admin/infoDown/getAppInfoDowmDetail")
    Call<BaseResponse> getNoticeDeliveryDetail(
            @Header("Authorization") String token,
            @Query("formId") String formId
    );

    /**
     * 通知下发撤回
     *
     * @param token
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("school-admin-api/admin/infoDown/changeStatus")
    Call<BaseResponse> rebackNoticeDelivery(@Header("Authorization") String token, @Body RequestBody body);


    /**
     * 通知下发删除（批量）
     *
     * @param token
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("school-admin-api/admin/infoDown/deleteList")
    Call<BaseResponse> deleteNoticeDelivery(@Header("Authorization") String token, @Body RequestBody body);


    /**
     * 首页统计学校教师信息
     *
     * @param token
     * @return
     */
    @GET("school-admin-api/schoolManage/countSchoolTeacher")
    Call<BaseResponse> getTeacherInfo(
            @Header("Authorization") String token
    );

    /**
     * 首页统计学校学生信息
     *
     * @param token
     * @return
     */
    @GET("school-admin-api/schoolManage/countSchoolStudent")
    Call<BaseResponse> getStudentInfo(
            @Header("Authorization") String token
    );

    /**
     * 首页统计学校缺勤信息
     *
     * @param token
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("school-admin-api/schoolManage/countLeave")
    Call<BaseResponse> getAbsenceInfo(@Header("Authorization") String token, @Body RequestBody body);


    /**
     * app-获取区域教育动态
     *
     * @param token
     * @return
     */
    @GET("school-admin-api/app/admin/getAdminInformationList")
    Call<BaseResponse> getAreaDynamicInfo(@Header("Authorization") String token);


    /**
     * 查询区域宣传图片列表
     *
     * @param token
     * @return
     */
    @GET("school-admin-api/app/admin/getAdminInformationList")
    Call<BaseResponse> getAreaPicInfo(@Header("Authorization") String token);


    /**
     * app-获取区域教育动态
     *
     * @param token
     * @return
     */
    @GET("school-admin-api/app/admin/getAdminInformationList")
    Call<BaseResponse> getEducationDynamicList(@Header("Authorization") String token);


    /**
     * app-获取区域学校风采 分页查询
     *
     * @param token
     * @return
     */
    @GET("school-admin-api/app/admin/demeanour/list/CAMPUS_SHOW")
    Call<BaseResponse> getSchoolStyleList(@Header("Authorization") String token, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);


    /**
     * 查询教育动态详情
     *
     * @param token
     * @param id
     * @return
     */
    @GET("school-admin-api/adminArticleCampusInfo/detail")
    Call<BaseResponse> getAreaDynamicDetail(@Header("Authorization") String token, @Query("id") String id);

    /**
     * 专家论坛--获取专家论坛列表(分页) 微校网站
     *
     * @param token
     * @return
     */
    @GET("app-api/app/school/news/list/EXPORT_FORUM")
    Call<BaseResponse> getBBsList(@Header("Authorization") String token, @Query("pageNo") int pageNo);


    /**
     * 行政端-查询受理单位
     *
     * @param token
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("school-admin-api/adminComplaintPropose/getAffiliationSchool")
    Call<BaseResponse> getServiceUnitList(@Header("Authorization") String token, @Body RequestBody body);


    /**
     * 行政版-新增投诉建议
     *
     * @param token
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("school-admin-api/adminComplaintPropose/addComplaintPropose")
    Call<BaseResponse> addComplaintPropose(@Header("Authorization") String token, @Body RequestBody body);


    /**
     * 行政版-查询投诉建议说明
     *
     * @param token
     * @param pageNo
     * @return
     */
    @GET("school-admin-api/adminComplaintPropose/getDesc")
    Call<BaseResponse> getStatement(@Header("Authorization") String token);

    /**
     * 通用上次文件
     *
     * @param token
     * @param file
     * @return
     */
    @POST("app-api/sysFileInfo/uploadNew")
    @Multipart
    Call<BaseResponse> uploadFile(@Header("Authorization") String token, @Part MultipartBody.Part file);

    /**
     * @param token
     * @param formId
     * @param status 0:未回复，3：已回复
     * @return
     */
    //行政端-查看通知结果
    @GET("school-admin-api/admin/infoDown/getNoticeList")
    Call<BaseResponse> getNoticeReplyList(@Header("Authorization") String token, @Query("formId") String formId, @Query("status") int status);

    /**
     * 行政端-发送通知提醒未确认的老师
     *
     * @param token
     * @return
     */
    @GET("school-admin-api/admin/infoDown/remindNotConfirm")
    Call<BaseResponse> remindNoticeNotConfirm(@Header("Authorization") String token, @Query("formId") String formId);

    /**
     * 添加信息下发（发出）
     *
     * @param token
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("school-admin-api/admin/infoDown/addAdminInfo")
    Call<BaseResponse> addNoticeDelivery(@Header("Authorization") String token, @Body RequestBody body);

    /**
     * 查询隶属学校通讯录
     *
     * @param token
     * @return
     */
    @GET("school-admin-api/schoolManage/getAffiliationSchoolUserData")
    Call<BaseResponse> getContactList(
            @Header("Authorization") String token
    );

    /**
     * app端-分页查询文件下发
     *
     * @param token
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("school-admin-api/admin/papersDown/pagePapersDownApp")
    Call<BaseResponse> getFileDeliveryList(@Header("Authorization") String token, @Body RequestBody body);

    /**
     * app-查询文件下发详情
     */
    @GET("school-admin-api/admin/papersDown/getAppPapersDowmDetail")
    Call<BaseResponse> getFileDeliveryDetail(
            @Header("Authorization") String token,
            @Query("formId") String formId
    );

    /**
     * 添加文件下发（发出）
     *
     * @param token
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("school-admin-api/admin/papersDown/addAdminPapers")
    Call<BaseResponse> addFileDelivery(@Header("Authorization") String token, @Body RequestBody body);


    /**
     * 行政端-查看文件结果
     *
     * @param token
     * @param formId
     * @param status 0:未回复，3：已回复
     * @return
     */
    @GET("school-admin-api/admin/papersDown/getNoticeList")
    Call<BaseResponse> getFileReplyList(@Header("Authorization") String token, @Query("formId") String formId, @Query("status") int status);

    /**
     * 文件下发撤回
     *
     * @param token
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("school-admin-api/admin/papersDown/changeStatus")
    Call<BaseResponse> rebackFileDelivery(@Header("Authorization") String token, @Body RequestBody body);

    /**
     * 文件下发删除（批量）
     *
     * @param token
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("school-admin-api/admin/papersDown/deleteList")
    Call<BaseResponse> deleteFileDelivery(@Header("Authorization") String token, @Body RequestBody body);

    /**
     * 行政端-发送文件提醒未确认的老师
     *
     * @param token
     * @return
     */
    @GET("school-admin-api/admin/papersDown/remindNotConfirm")
    Call<BaseResponse> remindFileNotConfirm(@Header("Authorization") String token, @Query("formId") String formId);

    /**
     * 行政-查询log图片
     *
     * @param token
     * @return
     */
    @GET("school-admin-api/schoolConfigSysSetting/list")
    Call<BaseResponse> getLogo(@Header("Authorization") String token);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("app-api/sysUser/updatePwd")
    Call<BaseResponse> changePwd(@Header("Authorization") String token, @Body RequestBody body);

    @GET("api/wx-auth/getAccessToken")
    Call<BaseResponse> getAccessToken(@Query("code") String code, @Query("type") int type);

    @GET("api/wx-auth/getWxUserInfo")
    Call<BaseResponse> getWxUserInfo(@Query("accessToken") String accessToken, @Query("openId") String openId, @Query("type") int type);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("api/wx-auth/wxLogin")
    Call<BaseResponse> wxLogin(@Body RequestBody body);

    @GET("api/wx-auth/refreshToken")
    Call<BaseResponse> refreshToken(@Query("refreshToken") String refreshToken, @Query("type") int type);

    @GET("api/wx-auth/verifyAccessToken")
    Call<BaseResponse> verifyAccessToken(@Query("accessToken") String accessToken, @Query("openId") String openId, @Query("type") int type);

    @GET("api/wx-auth/getWxInfo")
    Call<BaseResponse> getWxUnbindList(@Header("Authorization") String token, @Query("account") String account);

    @GET("api/wx-auth/unbindingWx")
    Call<BaseResponse> wxunbinding(
            @Header("Authorization") String token,
            @Query("account") String account,
            @Query("openId") String openId,
            @Query("type") int type
    );

    /**
     * 发送验证码
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("app-api/sms/sendMsg")
    Call<BaseResponse> sendVerify(@Body RequestBody body);

    /**
     * 校验验证码并更新密码
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("app-api/sms/appValidateMsg")
    Call<BaseResponse> findPwd(@Body RequestBody body);

}

