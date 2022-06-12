package com.best.friends.core.util

import android.content.Context
import android.widget.Toast

object ToastUtil {

    fun showTextToast(context: Context, text: String){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}
