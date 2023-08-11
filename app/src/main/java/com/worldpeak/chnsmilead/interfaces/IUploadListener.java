package com.worldpeak.chnsmilead.interfaces;

import com.worldpeak.chnsmilead.home.model.CommonAccessory;

public interface IUploadListener {
    void onUploadSuccess(CommonAccessory file);
    void onUploadFail();
}
