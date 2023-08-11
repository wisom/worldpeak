package com.worldpeak.chnsmilead.oa.viewmodel.notice;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.worldpeak.chnsmilead.base.BaseViewModel;
import com.worldpeak.chnsmilead.home.model.CommonAccessory;
import com.worldpeak.chnsmilead.net.BaseResponse;
import com.worldpeak.chnsmilead.net.CommonRequest;
import com.worldpeak.chnsmilead.net.RetrofitListener;
import com.worldpeak.chnsmilead.oa.model.NoticeDeliveryDetailResponse;

import java.util.List;

public class NoticeDeliveryDetailViewModel extends BaseViewModel {

    private String TAG = "NoticeDeliveryDetailViewModel==";
    public MutableLiveData<NoticeDeliveryDetailResponse> result = new MutableLiveData<>();

    public void getDetail(String formId) {
        CommonRequest.getInstance().getNoticeDeliveryDetail(formId, new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data != null && data.isSuccessful()) {
                    NoticeDeliveryDetailResponse response = (NoticeDeliveryDetailResponse) data.getResult(NoticeDeliveryDetailResponse.class);
                    result.postValue(response);
                } else {
                    if (!TextUtils.isEmpty(data.getMsg())) {
                        showToast(data.getMsg());
                    }
                }
            }

            @Override
            public void onError(BaseResponse data, String description) {
                if (!TextUtils.isEmpty(description)) {
                    showToast(description);
                }
            }
        });
    }

    /**
     * 撤销
     *
     * @param formId
     */
    public void rebackNoticeDelivery(String formId) {
        CommonRequest.getInstance().rebackNoticeDelivery(formId, new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data != null && data.isSuccessful()) {
                    showToast("撤回成功");
                } else if (!TextUtils.isEmpty(data.getMsg())) {
                    showToast(data.getMsg());
                }
            }

            @Override
            public void onError(BaseResponse data, String description) {
                if (!TextUtils.isEmpty(description)) {
                    showToast(description);
                }
            }
        });
    }

    public void deleteNoticeDelivery(List<String> ids) {
        CommonRequest.getInstance().deleteNoticeDelivery(ids, new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (!TextUtils.isEmpty(data.getMsg())) {
                    showToast(data.getMsg());
                }
            }

            @Override
            public void onError(BaseResponse data, String description) {
                if (!TextUtils.isEmpty(description)) {
                    showToast(description);
                }
            }
        });
    }

    public void remindNotConfirm(String formId) {
        if (TextUtils.isEmpty(formId)) return;
        CommonRequest.getInstance().remindNoticeNotConfirm(formId, new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data != null && data.isSuccessful()) {
                    showToast("提醒成功");
                } else {
                    if (!TextUtils.isEmpty(data.getMsg())) {
                        showToast(data.getMsg());
                    }
                }
            }

            @Override
            public void onError(BaseResponse data, String description) {
                if (!TextUtils.isEmpty(description)) {
                    showToast(description);
                }
            }
        });
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
            List<CommonAccessory> infoAccessoryList
    ) {
        CommonRequest.getInstance().addNoticeDelivery(
                title,
                content,
                grade,
                process,
                remark,
                id,
                formId,
                status,
                infoDownNoticeList,
                infoAccessoryList,
                new RetrofitListener<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse data) {
                        if (data != null && data.isSuccessful()) {
                            showToast("发送成功");
                        } else {
                            if (!TextUtils.isEmpty(data.getMsg())) {
                                showToast(data.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(BaseResponse data, String description) {
                        if (!TextUtils.isEmpty(description)) {
                            showToast(description);
                        }
                    }
                });
    }
}
