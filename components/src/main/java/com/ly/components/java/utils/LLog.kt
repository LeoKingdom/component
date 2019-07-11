package com.ly.components.java.utils

import android.util.Log


class LLog  {

    companion object {
        /**
         * 线程安全式单例模式
         */
//        private var instance: LLog? = null
//            get() {
//                if (field == null) {
//                    field = LLog()
//                }
//                return field
//            }
//
//        @Synchronized
//        fun get(): LLog {
//            return instance!!
//        }

        //设置是否开启log,在application初始化
        var isDebug = false

        fun e(tag: String, str: String?) {
            if (isDebug) {
                Log.e(tag, str + "")
            }
        }

        fun i(tag: String, str: String?) {
            if (isDebug) {
                Log.i(tag, str + "")
            }
        }

        fun d(tag: String, str: String?) {
            if (isDebug) {
                Log.d(tag, str + "")
            }
        }

        fun v(tag: String, str: String?) {
            if (isDebug) {
                Log.v(tag, str + "")
            }
        }

        fun w(tag: String, str: String?) {
            if (isDebug) {
                Log.w(tag, str + "")
            }
        }
    }
}