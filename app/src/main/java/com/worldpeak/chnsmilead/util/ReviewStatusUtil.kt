package com.worldpeak.chnsmilead.util

import com.worldpeak.chnsmilead.R

class ReviewStatusUtil {

    companion object {

        /**
         * @param reviewStatus 状态（1待批/待读、2已批/已读、3拒批）
         */
        @JvmStatic
        fun getStatusStr(reviewStatus: Int?): String {
            return when (reviewStatus) {
                1 -> {
                    return "待批"
                }
                2 -> {
                    return "已批"
                }
                3 -> {
                    return "拒绝"
                }
                else -> {
                    return "未知"
                }
            }
        }

        /**
         * @param reviewStatus 状态（1待批/待读、2已批/已读、3拒批）
         */
        @JvmStatic
        fun getStatusBg(reviewStatus: Int?): Int {
            return when (reviewStatus) {
                1 -> {
                    return R.drawable.shape_bg_gray_radius_11dp
                }
                2 -> {
                    return R.drawable.shape_bg_blue_radius_11dp
                }
                3 -> {
                    return R.drawable.shape_bg_orange_radius_11dp
                }
                else -> {
                    return R.drawable.shape_bg_gray_radius_11dp
                }
            }
        }
    }
}