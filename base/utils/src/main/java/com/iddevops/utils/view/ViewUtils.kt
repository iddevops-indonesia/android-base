package com.iddevops.utils.view

import android.view.View

fun View.gone(){
    this.visibility = View.GONE
}
fun View.visible(){
    this.visibility = View.VISIBLE
}
fun View.invincible(){
    this.visibility = View.INVISIBLE
}