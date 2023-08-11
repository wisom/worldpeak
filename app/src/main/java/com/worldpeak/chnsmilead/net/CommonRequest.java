package com.worldpeak.chnsmilead.net;


import static com.worldpeak.chnsmilead.constant.Constants.DEFAULT_LOAD_PAGESIZE;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.worldpeak.chnsmilead.base.manager.AccountManager;
import com.worldpeak.chnsmilead.home.model.CommonAccessory;
import com.worldpeak.chnsmilead.home.model.ComplaintAdviceFileItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CommonRequest extends BaseRequest {

    private static CommonRequest instance = null;
    private static Gson mGson;

    private CommonRequest() {
    }

    public static CommonRequest getInstance() {
        if (instance == null) {
            synchronized (CommonRequest.class) {
                if (instance == null) {
                    instance = new CommonRequest();
                    mGson = new Gson();
                }
                return instance;
            }
        }
        return instance;
    }

    /**
     * 获取账号所属服务器列表
     */
    public void platformRegionUser(String account, String isStaff, final RetrofitListener<BaseResponse> retrofitListener) {
        getLoginApi().platformRegionUser(account, isStaff).enqueue(getCallback(retrofitListener));
    }

    /**
     * 账号登录
     */
    public void mobileLogin(String phone, String password, String userIdentity, final RetrofitListener<BaseResponse> retrofitListener) {
        RequestBody body = getRequestBody(new KV("phone", phone), new KV("password", password), new KV("userIdentity", userIdentity));
        getCommonApi().mobileLogin(body).enqueue(getCallback(retrofitListener));
    }

    /**
     * 获取登陆用户信息
     */
    public void getLoginUser(String token, final RetrofitListener<BaseResponse> retrofitListener) {
        getCommonApi().getLoginUser("Bearer " + token).enqueue(getCallback(retrofitListener));
    }

    /**
     * 查询投诉建议类型
     *
     * @param type
     * @param retrofitListener
     */
    public void getComplaintAdviceType(int type, final RetrofitListener<BaseResponse> retrofitListener) {
        getCommonApi().getComplaintAdviceType("Bearer " + AccountManager.getToken(), type).enqueue(getCallback(retrofitListener));
    }

    /**
     * 行政-查询投诉/建议 列表
     *
     * @param paramType        单位类型（1：pc学校版，2：pc行政版，3：app）
     * @param type             类型（1：建议，2：投诉）
     * @param retrofitListener
     */
    public void getComplaintAdviceList(String type, String pageNo, final RetrofitListener<BaseResponse> retrofitListener) {
        List<KV> kvList = new ArrayList<>();
        kvList.add(new KV("pageSize", DEFAULT_LOAD_PAGESIZE));
        if (!TextUtils.isEmpty(type)) {
            kvList.add(new KV("type", type));
        }
        kvList.add(new KV("paramType", "3"));
        kvList.add(new KV("pageNo", pageNo));

        RequestBody body = getRequestBody(kvList);
        getCommonApi().getComplaintAdviceList("Bearer " + AccountManager.getToken(), body).enqueue(getCallback(retrofitListener));
    }

    @NonNull
    private RequestBody getRequestBody(KV... kv) {
        // 将数据封装为map类型
        HashMap<String, String> params = new HashMap<String, String>();
        for (KV item : kv) {
            params.put(item.key, item.value);
        }
        //将Map转换为JSONObject
        JSONObject jsonObject = new JSONObject(params);
        // 最后将JsonObject转换为字符串,封装为Retrofit的RequestBody,
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        return body;
    }

    @NonNull
    private RequestBody getRequestBody(List<KV> kv) {
        // 将数据封装为map类型
        HashMap<String, String> params = new HashMap<String, String>();
        for (KV item : kv) {
            params.put(item.key, item.value);
        }
        //将Map转换为JSONObject
        JSONObject jsonObject = new JSONObject(params);
        // 最后将JsonObject转换为字符串,封装为Retrofit的RequestBody,
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        return body;
    }

    @NonNull
    private RequestBody getRequestObjBody(List<KVObj> kv) {
        // 将数据封装为map类型
        HashMap<String, Object> params = new HashMap<String, Object>();
        for (KVObj item : kv) {
            params.put(item.key, item.value);
        }
        //将Map转换为JSONObject
        JSONObject jsonObject = new JSONObject(params);
        // 最后将JsonObject转换为字符串,封装为Retrofit的RequestBody,
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        return body;
    }

    /**
     * 行政端-查询投诉建议详情
     *
     * @param retrofitListener
     */
    public void getComplaintAdviceDetail(String id, final RetrofitListener<BaseResponse> retrofitListener) {
        getCommonApi().getComplaintAdviceDetail("Bearer " + AccountManager.getToken(), id).enqueue(getCallback(retrofitListener));
    }

    public void testLogin(String username, String password, final RetrofitListener<BaseResponse> retrofitListener) {
        getCommonApi().testLogin(username, password).enqueue(getCallback(retrofitListener));
    }

    public void getAllProcessList(String listType,String queryStatus, int pageNo, final RetrofitListener<BaseResponse> retrofitListener) {
        List<KV> kvList = new ArrayList<>();
        kvList.add(new KV("listType", listType));//2：我的审批，3：我的通知
        kvList.add(new KV("pageSize", DEFAULT_LOAD_PAGESIZE));
        if (!TextUtils.isEmpty(queryStatus)) {
            kvList.add(new KV("queryStatus", queryStatus));//审批人状态（1待批/待读、2已批/已读(包含拒批)）,传空时为查所有
        }
        kvList.add(new KV("pageNo", pageNo));

        RequestBody body = getRequestBody(kvList);
        getCommonApi().getAllProcessList("Bearer " + AccountManager.getToken(), body).enqueue(getCallback(retrofitListener));
    }

    public void getDocumentApprovalDetail(String formId, final RetrofitListener<BaseResponse> retrofitListener) {
        getCommonApi().getDocumentApprovalDetail("Bearer " + AccountManager.getToken(), formId).enqueue(getCallback(retrofitListener));
    }

    /**
     * @param formId           OAD2023060003
     * @param approveRemark    "同意"
     * @param status           2已批/已读、3拒批
     * @param retrofitListener
     */
    public void documentApproval(String formId, String approveRemark, int status, final RetrofitListener<BaseResponse> retrofitListener) {
        List<KV> kvList = new ArrayList<>();
        kvList.add(new KV("formId", formId));
        kvList.add(new KV("approveRemark", approveRemark));
        kvList.add(new KV("status", status));

        RequestBody body = getRequestBody(kvList);
        getCommonApi().documentApproval("Bearer " + AccountManager.getToken(), body).enqueue(getCallback(retrofitListener));
    }

    /**
     * 通知下发列表
     *
     * @param paramType        1:我发起的，2：全部，3：全部已发起的
     * @param grade            重要程度（1普通、2重要、3紧急）
     * @param pageNo
     * @param retrofitListener
     */
    public void getNoticeDeliveryList(String paramType, String grade, int pageNo, final RetrofitListener<BaseResponse> retrofitListener) {
        List<KV> kvList = new ArrayList<>();
        kvList.add(new KV("pageSize", DEFAULT_LOAD_PAGESIZE));
        if (!TextUtils.isEmpty(paramType)) {
            kvList.add(new KV("paramType", paramType));
        }
        if (!TextUtils.isEmpty(paramType)) {
            kvList.add(new KV("grade", grade));
        }
        kvList.add(new KV("pageNo", pageNo));

        RequestBody body = getRequestBody(kvList);
        getCommonApi().getNoticeDeliveryList("Bearer " + AccountManager.getToken(), body).enqueue(getCallback(retrofitListener));
    }

    /**
     * app-查询信息下发详情
     *
     * @param formId
     * @param retrofitListener
     */
    public void getNoticeDeliveryDetail(String formId, final RetrofitListener<BaseResponse> retrofitListener) {
        getCommonApi().getNoticeDeliveryDetail("Bearer " + AccountManager.getToken(), formId).enqueue(getCallback(retrofitListener));
    }

    /**
     * 通知下发撤回
     *
     * @param formId
     * @param status           状态（1：已发送、2:已撤销）
     * @param retrofitListener
     */
    public void rebackNoticeDelivery(String formId, final RetrofitListener<BaseResponse> retrofitListener) {
        List<KV> kvList = new ArrayList<>();
        kvList.add(new KV("formId", formId));
        kvList.add(new KV("status", 0));
        RequestBody body = getRequestBody(kvList);
        getCommonApi().rebackNoticeDelivery("Bearer " + AccountManager.getToken(), body).enqueue(getCallback(retrofitListener));
    }

    /**
     * 通知下发删除（批量）
     *
     * @param ids              "ids":[
     *                         "16456654",
     *                         "44658"
     *                         ]
     * @param retrofitListener
     */
    public void deleteNoticeDelivery(List<String> ids, final RetrofitListener<BaseResponse> retrofitListener) {
        List<KVObj> kvList = new ArrayList<>();
        try {
            JSONArray array = new JSONArray();
            for (int i = 0; i < ids.size(); i++) {
                array.put(ids.get(i));
            }
            kvList.add(new KVObj("ids", array));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        RequestBody body = getRequestObjBody(kvList);
        getCommonApi().deleteNoticeDelivery("Bearer " + AccountManager.getToken(), body).enqueue(getCallback(retrofitListener));
    }

    /**
     * 首页统计学校教师信息
     *
     * @param retrofitListener
     */
    public void getTeacherInfo(final RetrofitListener<BaseResponse> retrofitListener) {
        getCommonApi().getTeacherInfo("Bearer " + AccountManager.getToken()).enqueue(getCallback(retrofitListener));
    }

    /**
     * 首页统计学校学生信息
     *
     * @param retrofitListener
     */
    public void getStudentInfo(final RetrofitListener<BaseResponse> retrofitListener) {
        getCommonApi().getStudentInfo("Bearer " + AccountManager.getToken()).enqueue(getCallback(retrofitListener));
    }

    /**
     * 缺勤信息
     *
     * @param type             1:日，2：月
     * @param day              查询日期
     * @param retrofitListener
     */
    public void getAbsenceInfo(int type, String day, final RetrofitListener<BaseResponse> retrofitListener) {
        RequestBody body = getRequestBody(new KV("type", type), new KV("day", day));
        getCommonApi().getAbsenceInfo("Bearer " + AccountManager.getToken(), body).enqueue(getCallback(retrofitListener));
    }

    /**
     * app-获取区域教育动态
     *
     * @param retrofitListener
     */
    public void getAreaDynamicInfo(final RetrofitListener<BaseResponse> retrofitListener) {
        getCommonApi().getAreaDynamicInfo("Bearer " + AccountManager.getToken()).enqueue(getCallback(retrofitListener));
    }


    /**
     * 查询区域宣传图片列表
     *
     * @param retrofitListener
     */
    public void getBannerList(final RetrofitListener<BaseResponse> retrofitListener) {
        getCommonApi().getAreaPicInfo("Bearer " + AccountManager.getToken()).enqueue(getCallback(retrofitListener));
    }

    /**
     * app-获取区域学校风采 分页查询
     *
     * @param pageNo
     * @param retrofitListener
     */
    public void getSchoolStyleList(int pageNo, final RetrofitListener<BaseResponse> retrofitListener) {
        getCommonApi().getSchoolStyleList("Bearer " + AccountManager.getToken(), pageNo, DEFAULT_LOAD_PAGESIZE).enqueue(getCallback(retrofitListener));
    }

    /**
     * app-获取区域教育动态
     *
     * @param retrofitListener
     */
    public void getEducationDynamicList(final RetrofitListener<BaseResponse> retrofitListener) {
        getCommonApi().getEducationDynamicList("Bearer " + AccountManager.getToken()).enqueue(getCallback(retrofitListener));
    }

    /**
     * 教育动态详情
     *
     * @param id
     * @param retrofitListener
     */
    public void getAreaDynamicDetail(String id, final RetrofitListener<BaseResponse> retrofitListener) {
        getCommonApi().getAreaDynamicDetail("Bearer " + AccountManager.getToken(), id).enqueue(getCallback(retrofitListener));
    }

    /**
     * 专家论坛--获取专家论坛列表(分页) 微校网站
     *
     * @param retrofitListener
     */
    public void getBBsList(int pageNo, final RetrofitListener<BaseResponse> retrofitListener) {
        getCommonApi().getBBsList("Bearer " + AccountManager.getToken(), pageNo).enqueue(getCallback(retrofitListener));
    }

    /**
     * 行政端-查询受理单位
     *
     * @param status           状态（0：正常），默认传0
     * @param retrofitListener
     */
    public void getServiceUnitList(final RetrofitListener<BaseResponse> retrofitListener) {
        RequestBody body = getRequestBody(new KV("status", 0));
        getCommonApi().getServiceUnitList("Bearer " + AccountManager.getToken(), body).enqueue(getCallback(retrofitListener));
    }

    /**
     * 行政版-新增投诉建议
     *
     * @param type                          类型（1：建议，2：投诉）
     * @param targetSchoolId                受理单位学校id
     * @param targetSchoolName              受理单位名称
     * @param targetSchoolType              受理单位类型（-1：区域行政）
     * @param complaintProposeType          投诉建议类型
     * @param complaintProposeName          投诉建议类型描述
     * @param title                         投诉建议标题
     * @param content                       投诉建议内容
     * @param complaintProposePeople        投诉建议人
     * @param contactInformation            投诉建议人联系方式
     * @param complaintProposeAccessoryList 附件集合
     * @param retrofitListener
     */
    public void addComplaintPropose(
            int type,
            String targetSchoolId,
            String targetSchoolName,
            int targetSchoolType,
            int complaintProposeType,
            String complaintProposeName,
            String title,
            String content,
            String complaintProposePeople,
            String contactInformation,
            List<ComplaintAdviceFileItem> complaintProposeAccessoryList,
            final RetrofitListener<BaseResponse> retrofitListener) {
        List<KVObj> kvList = new ArrayList<>();
        kvList.add(new KVObj("type", type));
        kvList.add(new KVObj("targetSchoolId", targetSchoolId));
        kvList.add(new KVObj("targetSchoolName", targetSchoolName));
        kvList.add(new KVObj("targetSchoolType", targetSchoolType));
        kvList.add(new KVObj("complaintProposeType", complaintProposeType));
        kvList.add(new KVObj("complaintProposeName", complaintProposeName));
        kvList.add(new KVObj("title", title));
        kvList.add(new KVObj("content", content));
        kvList.add(new KVObj("complaintProposePeople", complaintProposePeople));
        kvList.add(new KVObj("contactInformation", contactInformation));
//        kvList.add(new KVObj("complaintProposeAccessoryList", (new Gson()).toJson(complaintProposeAccessoryList)));
        kvList.add(new KVObj("complaintProposeAccessoryList", covertJsonObjectToJsonArray(complaintProposeAccessoryList)));
        RequestBody body = getRequestObjBody(kvList);
        getCommonApi().addComplaintPropose("Bearer " + AccountManager.getToken(), body).enqueue(getCallback(retrofitListener));
    }

    public JSONArray covertJsonObjectToJsonArray(Object InsideArray) {

        JSONArray jsonArray;

        if (InsideArray instanceof JSONArray) {
            jsonArray = (JSONArray) InsideArray;
        } else {
            jsonArray = new JSONArray();
            jsonArray.put((JSONObject) InsideArray);
        }
        return jsonArray;
    }

    /**
     * 行政版-查询投诉建议说明
     *
     * @param retrofitListener
     */
    public void getStatement(final RetrofitListener<BaseResponse> retrofitListener) {
        getCommonApi().getStatement("Bearer " + AccountManager.getToken()).enqueue(getCallback(retrofitListener));
    }

    /**
     * 通用上次文件
     *
     * @param filePath
     * @param retrofitListener
     */
    public void uploadFile(String filePath, final RetrofitListener<BaseResponse> retrofitListener) {
        File file = new File(filePath);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        getCommonApi().uploadFile("Bearer " + AccountManager.getToken(), body).enqueue(getCallback(retrofitListener));
    }

    /**
     * 行政端-查看通知结果
     *
     * @param formId
     * @param retrofitListener
     */
    public void getNoticeReplyList(String formId, int status, final RetrofitListener<BaseResponse> retrofitListener) {
        getCommonApi().getNoticeReplyList("Bearer " + AccountManager.getToken(), formId, status).enqueue(getCallback(retrofitListener));
    }

    /**
     * 行政端-发送通知提醒未确认的老师
     *
     * @param retrofitListener
     */
    public void remindNoticeNotConfirm(String formId, final RetrofitListener<BaseResponse> retrofitListener) {
        getCommonApi().remindNoticeNotConfirm("Bearer " + AccountManager.getToken(), formId).enqueue(getCallback(retrofitListener));
    }

    /**
     * 添加信息下发（发出）
     *
     * @param title
     * @param content
     * @param grade              重要程度（1普通、2重要、3紧急）
     * @param process            批阅要求（1仅阅读、2需回复）
     * @param remark
     * @param id                 信息下发主键id（新增传null，修改发出必传）
     * @param formId             表单编号（新增传空，修改发出必传）
     * @param status             状态（0：未发送、1：已发送，2：已撤回）
     * @param infoDownNoticeList 通知学校用户userId集合
     * @param infoAccessoryList  附件集合
     * @param retrofitListener
     */
    public void addNoticeDelivery(
            String title,
            String content,
            int grade,
            int process,
            String remark,
            long id,
            String formId,
            int status,
            List<String> infoDownNoticeList,
            List<CommonAccessory> infoAccessoryList,
            final RetrofitListener<BaseResponse> retrofitListener) {
        List<KVObj> kvList = new ArrayList<>();
        kvList.add(new KVObj("title", title));
        kvList.add(new KVObj("content", content));
        kvList.add(new KVObj("grade", grade));
        kvList.add(new KVObj("process", process));
        kvList.add(new KVObj("remark", remark));
        if (id != -1L) {
            kvList.add(new KVObj("id", id));
        }
        if (!formId.isEmpty()) {
            kvList.add(new KVObj("formId", formId));
        }
        kvList.add(new KVObj("status", status));
        kvList.add(new KVObj("infoDownNoticeList", infoDownNoticeList));
        kvList.add(new KVObj("infoAccessoryList", infoAccessoryList));
        RequestBody body = getRequestObjBody(kvList);
        getCommonApi().addNoticeDelivery("Bearer " + AccountManager.getToken(), body).enqueue(getCallback(retrofitListener));
    }

    /**
     * 查询隶属学校通讯录
     *
     * @param retrofitListener
     */
    public void getContactList(final RetrofitListener<BaseResponse> retrofitListener) {
        getCommonApi().getContactList("Bearer " + AccountManager.getToken()).enqueue(getCallback(retrofitListener));
    }

    /**
     * 文件下发列表
     *
     * @param paramType        1:我发起的，2：全部，3：全部已发起的
     * @param grade            重要程度（1普通、2重要、3紧急）
     * @param pageNo
     * @param retrofitListener
     */
    public void getFileDeliveryList(String paramType, String grade, int pageNo, final RetrofitListener<BaseResponse> retrofitListener) {
        List<KV> kvList = new ArrayList<>();
        kvList.add(new KV("pageSize", DEFAULT_LOAD_PAGESIZE));
        if (!TextUtils.isEmpty(paramType)) {
            kvList.add(new KV("paramType", paramType));
        }
        if (!TextUtils.isEmpty(paramType)) {
            kvList.add(new KV("grade", grade));
        }
        kvList.add(new KV("pageNo", pageNo));

        RequestBody body = getRequestBody(kvList);
        getCommonApi().getFileDeliveryList("Bearer " + AccountManager.getToken(), body).enqueue(getCallback(retrofitListener));
    }

    /**
     * app-查询文件下发详情
     *
     * @param formId
     * @param retrofitListener
     */
    public void getFileDeliveryDetail(String formId, final RetrofitListener<BaseResponse> retrofitListener) {
        getCommonApi().getFileDeliveryDetail("Bearer " + AccountManager.getToken(), formId).enqueue(getCallback(retrofitListener));
    }

    /**
     * 添加文件下发（发出）
     *
     * @param title
     * @param content
     * @param grade                重要程度（1普通、2重要、3紧急）
     * @param process              批阅要求（1仅阅读、2需回复）
     * @param remark
     * @param id                   信息下发主键id（新增传null，修改发出必传）
     * @param formId               表单编号（新增传空，修改发出必传）
     * @param status               状态（0：未发送、1：已发送，2：已撤回）
     * @param papersDownNoticeList 通知学校用户userId集合
     * @param papersAccessoryList  附件集合
     * @param retrofitListener
     */
    public void addFileDelivery(
            String title,
            String content,
            int grade,
            int process,
            String remark,
            long id,
            String formId,
            int status,
            List<String> papersDownNoticeList,
            List<CommonAccessory> papersAccessoryList,
            final RetrofitListener<BaseResponse> retrofitListener) {
        List<KVObj> kvList = new ArrayList<>();
        kvList.add(new KVObj("title", title));
        kvList.add(new KVObj("content", content));
        kvList.add(new KVObj("grade", grade));
        kvList.add(new KVObj("process", process));
        kvList.add(new KVObj("remark", remark));
        if (id != -1L) {
            kvList.add(new KVObj("id", id));
        }
        if (!formId.isEmpty()) {
            kvList.add(new KVObj("formId", formId));
        }
        kvList.add(new KVObj("status", status));
        kvList.add(new KVObj("papersDownNoticeList", papersDownNoticeList));
        kvList.add(new KVObj("papersAccessoryList", papersAccessoryList));
        RequestBody body = getRequestObjBody(kvList);
        getCommonApi().addFileDelivery("Bearer " + AccountManager.getToken(), body).enqueue(getCallback(retrofitListener));
    }

    /**
     * 行政端-查看文件结果
     *
     * @param formId
     * @param retrofitListener
     */
    public void getFileReplyList(String formId, int status, final RetrofitListener<BaseResponse> retrofitListener) {
        getCommonApi().getFileReplyList("Bearer " + AccountManager.getToken(), formId, status).enqueue(getCallback(retrofitListener));
    }

    /**
     * 文件下发撤回
     *
     * @param formId
     * @param status           状态（1：已发送、2:已撤销）
     * @param retrofitListener
     */
    public void rebackFileDelivery(String formId, final RetrofitListener<BaseResponse> retrofitListener) {
        List<KV> kvList = new ArrayList<>();
        kvList.add(new KV("formId", formId));
        kvList.add(new KV("status", 0));
        RequestBody body = getRequestBody(kvList);
        getCommonApi().rebackFileDelivery("Bearer " + AccountManager.getToken(), body).enqueue(getCallback(retrofitListener));
    }

    /**
     * 文件下发删除（批量）
     *
     * @param ids              "ids":[
     *                         "16456654",
     *                         "44658"
     *                         ]
     * @param retrofitListener
     */
    public void deleteFileDelivery(List<String> ids, final RetrofitListener<BaseResponse> retrofitListener) {
        List<KVObj> kvList = new ArrayList<>();
        try {
            JSONArray array = new JSONArray();
            for (int i = 0; i < ids.size(); i++) {
                array.put(ids.get(i));
            }
            kvList.add(new KVObj("ids", array));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        RequestBody body = getRequestObjBody(kvList);
        getCommonApi().deleteFileDelivery("Bearer " + AccountManager.getToken(), body).enqueue(getCallback(retrofitListener));
    }

    /**
     * 行政端-发送文件提醒未确认的老师
     *
     * @param retrofitListener
     */
    public void remindFileNotConfirm(String formId, final RetrofitListener<BaseResponse> retrofitListener) {
        getCommonApi().remindFileNotConfirm("Bearer " + AccountManager.getToken(), formId).enqueue(getCallback(retrofitListener));
    }

    /**
     * 行政-查询log图片
     *
     * @param retrofitListener
     */
    public void getLogo(final RetrofitListener<BaseResponse> retrofitListener) {
        getCommonApi().getLogo("Bearer " + AccountManager.getToken()).enqueue(getCallback(retrofitListener));
    }

    /**
     * 修改密码
     *
     * @param orginPwd
     * @param newPwd
     * @param retrofitListener
     */
    public void changePwd(String orginPwd, String newPwd, final RetrofitListener<BaseResponse> retrofitListener) {
        List<KVObj> kvList = new ArrayList<>();
        kvList.add(new KVObj("password", orginPwd));
        kvList.add(new KVObj("newPassword", newPwd));
        RequestBody body = getRequestObjBody(kvList);
        getCommonApi().changePwd("Bearer " + AccountManager.getToken(), body).enqueue(getCallback(retrofitListener));
    }

    /**
     * 获取access_token
     *
     * @param code 微信授权后返回的code，用于获取access_token
     * @param type type 无效，0-行政版 1-校园版
     */
    public void getAccessToken(String code, int type, final RetrofitListener<BaseResponse> retrofitListener) {
        getLoginApi().getAccessToken(code, type).enqueue(getCallback(retrofitListener));
    }

    /**
     * 获取微信用户信息
     *
     * @param accessToken
     * @param type        type 无效，0-行政版 1-校园版
     */
    public void getWxUserInfo(String accessToken, String openId, int type, final RetrofitListener<BaseResponse> retrofitListener) {
        getLoginApi().getWxUserInfo(accessToken, openId, type).enqueue(getCallback(retrofitListener));
    }

    /**
     * 微信登录
     *
     * @param accessToken
     * @param openId
     * @param account
     * @param password
     * @param userIdentity
     */
    public void wxLogin(String accessToken, String openId, String account, String password, int userIdentity, int type, final RetrofitListener<BaseResponse> retrofitListener) {
        List<KVObj> kvList = new ArrayList<>();
        kvList.add(new KVObj("accessToken", accessToken));
        kvList.add(new KVObj("openId", openId));
        kvList.add(new KVObj("account", account));
        kvList.add(new KVObj("password", password));
        kvList.add(new KVObj("userIdentity", userIdentity));
        kvList.add(new KVObj("type", type));
        RequestBody body = getRequestObjBody(kvList);
        getLoginApi().wxLogin(body).enqueue(getCallback(retrofitListener));
    }

    public void refreshToken(String refreshToken, int type, final RetrofitListener<BaseResponse> retrofitListener) {
        getLoginApi().refreshToken(refreshToken, type).enqueue(getCallback(retrofitListener));
    }

    public void verifyAccessToken(String accessToken, String openId, int type, final RetrofitListener<BaseResponse> retrofitListener) {
        getLoginApi().verifyAccessToken(accessToken, openId, type).enqueue(getCallback(retrofitListener));
    }

    public void getWxUnbindList(String account, final RetrofitListener<BaseResponse> retrofitListener) {
        getLoginApi().getWxUnbindList("Bearer " + AccountManager.getToken(), account).enqueue(getCallback(retrofitListener));
    }

    public void wxunbinding(String account, String openId, int type, final RetrofitListener<BaseResponse> retrofitListener) {
        getLoginApi().wxunbinding("Bearer " + AccountManager.getToken(), account, openId, type).enqueue(getCallback(retrofitListener));
    }

    public void sendVerify(String phone, final RetrofitListener<BaseResponse> retrofitListener) {
        List<KVObj> kvList = new ArrayList<>();
        kvList.add(new KVObj("phoneNumbers", phone));
        RequestBody body = getRequestObjBody(kvList);
        getLoginApi().sendVerify(body).enqueue(getCallback(retrofitListener));
    }

    public void findPwd(String phone, String validateCode, String newPassword, final RetrofitListener<BaseResponse> retrofitListener) {
        List<KVObj> kvList = new ArrayList<>();
        kvList.add(new KVObj("phoneNumbers", phone));
        kvList.add(new KVObj("validateCode", validateCode));
        kvList.add(new KVObj("newPassword", newPassword));
        RequestBody body = getRequestObjBody(kvList);
        getLoginApi().findPwd(body).enqueue(getCallback(retrofitListener));
    }
}
