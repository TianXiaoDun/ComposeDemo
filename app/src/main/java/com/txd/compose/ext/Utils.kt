package com.txd.compose.ui.ext

import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.txd.compose.BaseApplication

/**
 *
 *
 * @ClassName:      Utils
 * @CreateDate:     2022/9/1
 */

fun getColorEx(@ColorRes id: Int): Int = ContextCompat.getColor(
    BaseApplication.context,
    id
)

fun getDrawableEx(@DrawableRes id: Int): Drawable = ContextCompat.getDrawable(
    BaseApplication.context,
    id
)!!