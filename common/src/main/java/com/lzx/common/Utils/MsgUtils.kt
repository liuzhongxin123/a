package com.lzx.common.Utils

import android.content.Context
import android.widget.Toast

object MsgUtils {
    fun show(cxt: Context, msg:String){
        Toast.makeText(cxt,msg,Toast.LENGTH_SHORT).show();
    }
}