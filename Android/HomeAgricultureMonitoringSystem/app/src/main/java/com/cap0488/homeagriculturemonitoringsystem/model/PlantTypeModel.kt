package com.cap0488.homeagriculturemonitoringsystem.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlantTypeModel(
        var name: String = "",
        var img:Int = 0,
): Parcelable