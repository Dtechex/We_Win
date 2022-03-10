package com.co.wewin.utilities

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

object CustomAlertDialogBox {
    fun alertDialogShow(context: Context?, message: String?) {
        val alertDialog = AlertDialog.Builder(
            context!!
        ).create()
        alertDialog.setMessage(message)
        alertDialog.setButton("OK",
            DialogInterface.OnClickListener { dialog, which -> alertDialog.dismiss() })
        alertDialog.show()
    }
}