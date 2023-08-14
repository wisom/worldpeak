package com.worldpeak.chnsmilead.share

import android.R.attr.thumb
import android.graphics.Bitmap
import com.umeng.socialize.ShareAction
import com.umeng.socialize.media.UMImage
import com.umeng.socialize.media.UMWeb


/**
 *  Create by ggyy on 13
 */
public class ShareUtil {
    fun shareWeb(url:String,title:String,thumb:UMImage,description:String) {
        val web: UMWeb =  UMWeb(url)
        web.title = title //标题

        web.setThumb(thumb) //缩略图

        web.description = description //描述

    }

    fun test() {

    }
}