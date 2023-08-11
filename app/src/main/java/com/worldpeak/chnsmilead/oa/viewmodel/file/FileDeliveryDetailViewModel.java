package com.worldpeak.chnsmilead.oa.viewmodel.file;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.worldpeak.chnsmilead.base.BaseViewModel;
import com.worldpeak.chnsmilead.home.model.CommonAccessory;
import com.worldpeak.chnsmilead.net.BaseResponse;
import com.worldpeak.chnsmilead.net.CommonRequest;
import com.worldpeak.chnsmilead.net.RetrofitListener;
import com.worldpeak.chnsmilead.oa.model.file.FileDeliveryDetailResponse;

import java.util.List;

public class FileDeliveryDetailViewModel extends BaseViewModel {

    private String TAG = "FileDeliveryDetailViewModel==";
    public MutableLiveData<FileDeliveryDetailResponse> result = new MutableLiveData<>();

    public void getDetail(String formId) {
        CommonRequest.getInstance().getFileDeliveryDetail(formId, new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data != null && data.isSuccessful()) {
                    FileDeliveryDetailResponse response = (FileDeliveryDetailResponse) data.getResult(FileDeliveryDetailResponse.class);
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
    public void rebackFileDelivery(String formId) {
        CommonRequest.getInstance().rebackFileDelivery(formId, new RetrofitListener<BaseResponse>() {
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

    public void deleteFileDelivery(List<String> ids) {
        CommonRequest.getInstance().deleteFileDelivery(ids, new RetrofitListener<BaseResponse>() {
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

    public void remindFileNotConfirm(String formId) {
        if (TextUtils.isEmpty(formId)) return;
        CommonRequest.getInstance().remindFileNotConfirm(formId, new RetrofitListener<BaseResponse>() {
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
     * @param papersDownNoticeList 通知学校用户schoolId集合
     * @param papersDowmAccessoryList  附件集合
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
            List<CommonAccessory> papersDowmAccessoryList
    ) {
        CommonRequest.getInstance().addFileDelivery(
                title,
                content,
                grade,
                process,
                remark,
                id,
                formId,
                status,
                papersDownNoticeList,
                papersDowmAccessoryList,
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
