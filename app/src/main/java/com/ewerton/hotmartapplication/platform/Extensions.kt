package com.ewerton.hotmartapplication.platform

import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import com.ewerton.hotmartapplication.R

private val navOptions = NavOptions.Builder()
    .setEnterAnim(R.anim.slide_in_right)
    .setExitAnim(R.anim.slide_out_left)
    .setPopEnterAnim(R.anim.slide_in_left)
    .setPopExitAnim(R.anim.slide_out_right)
    .build()

fun NavController.navigateWithDefaultAnims(resId: Int, bundle: Bundle) {
    this.navigate(resId, bundle, navOptions)
}

fun Toolbar.setupAppearence(visibility: Int, iconId: Int? = null){
    this.visibility = visibility
    this.navigationIcon = iconId?.let {
        context.getDrawable(iconId)
    }
}



