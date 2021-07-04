package com.iddevops.android_base.utils.feedback

import android.widget.Toast
import com.iddevops.android_base.Application

fun Application.shortToast(message: String) = Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()