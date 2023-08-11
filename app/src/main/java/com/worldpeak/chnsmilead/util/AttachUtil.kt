package com.worldpeak.chnsmilead.util

import com.worldpeak.chnsmilead.R

class AttachUtil {

    companion object {

        @JvmStatic
        fun getImageRes(fileOriginName: String?): Int {
            if (fileOriginName.isNullOrEmpty()) {
                return R.drawable.ic_unknow
            }
            return when {
                fileOriginName.endsWith(".xls") || fileOriginName.endsWith(".xlsx") -> {
                    R.drawable.ic_excel
                }
                fileOriginName.endsWith(".word") -> {
                    R.drawable.ic_word
                }
                fileOriginName.endsWith(".txt") -> {
                    R.drawable.ic_txt
                }
                fileOriginName.endsWith(".ppt") -> {
                    R.drawable.ic_ppt
                }
                fileOriginName.endsWith(".pdf") -> {
                    R.drawable.ic_pdf
                }
                fileOriginName.endsWith(".zip") || fileOriginName.endsWith(".rar") || fileOriginName.endsWith(".7z") -> {
                    R.drawable.ic_zip
                }
                fileOriginName.endsWith(".png") || fileOriginName.endsWith(".jpg")
                        || fileOriginName.endsWith(".jpeg") || fileOriginName.endsWith(".webp")
                        || fileOriginName.endsWith(".gif") || fileOriginName.endsWith(".bmp") -> {
                    R.drawable.ic_pic
                }
                else -> {
                    R.drawable.ic_unknow
                }
            }
        }
    }
}