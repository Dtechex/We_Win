package com.co.wewin.utilities

import android.widget.Toast
import com.co.wewin.WeWinApplication.WeWinApplication

class ToastUtils {
    companion object{
        fun showShortToast(message:String){
            message.let {
                Toast.makeText(WeWinApplication.getInstance(),it,Toast.LENGTH_SHORT).show()
            }
        }

        fun showLongToast(message:String){
            message.let {
                Toast.makeText(WeWinApplication.getInstance(),it,Toast.LENGTH_LONG).show()
            }
        }
    }


}