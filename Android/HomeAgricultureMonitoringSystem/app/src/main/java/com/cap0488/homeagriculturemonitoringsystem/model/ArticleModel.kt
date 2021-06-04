package com.cap0488.homeagriculturemonitoringsystem.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticleModel(
        var title: String = "",
        var img:Int = 0,
        var content :String = ""
):Parcelable