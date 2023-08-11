package com.worldpeak.chnsmilead.home.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.worldpeak.chnsmilead.base.BaseViewModel;
import com.worldpeak.chnsmilead.home.model.AreaDynamicReponse;
import com.worldpeak.chnsmilead.home.model.CommonAccessory;
import com.worldpeak.chnsmilead.home.model.ComplaintAdviceFileItem;
import com.worldpeak.chnsmilead.home.model.ComplaintAdviceStatementResponse;
import com.worldpeak.chnsmilead.home.model.ComplaintAdviceType;
import com.worldpeak.chnsmilead.home.model.ComplaintAdviceUnitItem;
import com.worldpeak.chnsmilead.interfaces.IUploadListener;
import com.worldpeak.chnsmilead.net.BaseResponse;
import com.worldpeak.chnsmilead.net.CommonRequest;
import com.worldpeak.chnsmilead.net.RetrofitListener;

import java.util.List;

public class ComplaintAdviceViewModel extends BaseViewModel {

    private String TAG = "ComplaintAdviceViewModel==";
    public MutableLiveData<List<ComplaintAdviceType>> typeList = new MutableLiveData<>();
    public MutableLiveData<List<ComplaintAdviceUnitItem>> unitList = new MutableLiveData<>();
    public MutableLiveData<ComplaintAdviceStatementResponse> statement = new MutableLiveData<>();


    public void getComplaintAdviceType(int type) {
        CommonRequest.getInstance().getComplaintAdviceType(type, new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data != null && data.isSuccessful()) {
                    List type = data.getResultList(ComplaintAdviceType.class);
                    typeList.postValue(type);
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

    public void getServiceUnitList() {
        CommonRequest.getInstance().getServiceUnitList(new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data != null && data.isSuccessful()) {
                    List type = data.getResultList(ComplaintAdviceUnitItem.class);
                    unitList.postValue(type);
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
     * 获取后台说明文案
     */
    public void getStatement() {
        CommonRequest.getInstance().getStatement(new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data != null && data.isSuccessful()) {
                    ComplaintAdviceStatementResponse type = (ComplaintAdviceStatementResponse) data.getResult(ComplaintAdviceStatementResponse.class);
                    statement.postValue(type);
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

    public void uploadFile(String filePath, IUploadListener listener) {
        CommonRequest.getInstance().uploadFile(filePath, new RetrofitListener<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data != null && data.isSuccessful()) {
                    CommonAccessory file = (CommonAccessory) data.getResult(CommonAccessory.class);
                    listener.onUploadSuccess(file);
                } else {
                    listener.onUploadFail();
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
     */
    public void submit(int type,
                       String targetSchoolId,
                       String targetSchoolName,
                       int targetSchoolType,
                       int complaintProposeType,
                       String complaintProposeName,
                       String title,
                       String content,
                       String complaintProposePeople,
                       String contactInformation,
                       List<ComplaintAdviceFileItem> complaintProposeAccessoryList) {
        CommonRequest.getInstance().addComplaintPropose(
                type,
                targetSchoolId,
                targetSchoolName,
                targetSchoolType,
                complaintProposeType,
                complaintProposeName,
                title,
                content,
                complaintProposePeople,
                contactInformation,
                complaintProposeAccessoryList,
                new RetrofitListener<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse data) {
                        if (data != null && data.isSuccessful()) {
                            showToast("提交成功");
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
