@file:JvmName("UIUtils")
@file:JvmMultifileClass
package com.sweny.afterpay.carsapplication.utils

import android.content.Context
import android.content.DialogInterface
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sweny.afterpay.carsapplication.R


/**
 * utils function for display alert dialog
 *
 * @param context
 * @param title : title of alert dialog
 * @param message : messsage to display
 * @param btnText : positive button text
 */
fun showAlertDialog(context: Context, title: String, message: String, btnText: String) {
    var dialog =
        MaterialAlertDialogBuilder(context, R.style.MaterialAlertDialog_Background)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(btnText) { dialog, which ->
                dialog.dismiss()
            }
            .show()

    dialog.getButton(DialogInterface.BUTTON_POSITIVE).isAllCaps = false
}