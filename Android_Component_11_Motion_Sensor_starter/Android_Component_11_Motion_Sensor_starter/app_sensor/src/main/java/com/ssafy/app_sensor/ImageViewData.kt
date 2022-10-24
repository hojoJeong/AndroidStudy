package com.ssafy.app_sensor

import android.widget.ImageView
import kotlin.random.Random

data class ImageViewData(
    val imageView: ImageView,
    val speed: Float =  Random.nextInt(50).toFloat()
) {

}