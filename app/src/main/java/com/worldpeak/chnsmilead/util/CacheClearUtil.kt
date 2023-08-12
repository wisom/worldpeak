package com.worldpeak.chnsmilead.util

import android.content.Context
import android.os.Environment
import java.io.File
import java.math.BigDecimal
import java.util.*

/**
 *  Create by ggyy on 12
 */
class CacheClearUtil {
    companion object {
        /**
         * 删除APP缓存
         */
        fun clearCache(context: Context) {
            delDir(context.cacheDir)
            delDir(context.filesDir)
            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                context.externalCacheDir?.let { delDir(it) }
                context.getExternalFilesDir(null)?.let { delDir(it) }
            }
        }

        /**
         * 获取应用缓存大小
         */
        fun getCacheSize(context: Context): String {
            var cacheSize: Long = 0
            try {
                cacheSize = getFolderSize(context.cacheDir)
                cacheSize += getFolderSize(context.filesDir)
                if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                    cacheSize += getFolderSize(context.externalCacheDir)
                    cacheSize += getFolderSize(context.getExternalFilesDir(null))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                cacheSize = 0
            }
            return getFormatSize(cacheSize.toDouble())
        }

        /**
         * 格式化文件单位
         */
        private fun getFormatSize(size: Double): String {
            val kiloByte = size / 1024f
            if (kiloByte < 1) {
                return "0.00MB"
            }

            var megaByte = kiloByte / 1024f
            if (megaByte < 1) {
                return String.format(Locale.CHINA, "%.02f", kiloByte) + "KB"
            }

            val gigaByte = megaByte / 1024f
            if (gigaByte < 1) {
                if (megaByte > 10) {
                    megaByte = 10 + (megaByte - 10) * 0.1
                }
                return String.format(Locale.CHINA, "%.02f", megaByte) + "MB"
            }
            val teraBytes = gigaByte / 1024f
            if (teraBytes < 1) {
                val result3 = BigDecimal(java.lang.Double.toString(gigaByte))
                return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB"
            }
            val result4 = BigDecimal(teraBytes)
            return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB"
        }

        /**
         * 获取文件夹大小
         */
        private fun getFolderSize(file: File?): Long {
            var size: Long = 0
            try {
                val fileList = file!!.listFiles()
                for (i in fileList!!.indices) {
                    // 如果下面还有文件
                    if (fileList[i].isDirectory) {
                        size += getFolderSize(fileList[i])
                    } else {
                        size += fileList[i].length()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return size
        }

        /**
         * 删除文件夹
         */
        private fun delDir(dir: File) {
            if (dir.isDirectory) {
                val children = dir.list()
                for (i in children!!.indices) {
                    delDir(File(dir, children[i]))
                }
            }
            dir.delete()
        }
    }
}