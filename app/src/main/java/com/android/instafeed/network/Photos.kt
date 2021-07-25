package com.android.instafeed.network

import com.android.instafeed.data.Photo
import java.util.*
/*
* https://farm<farm>.staticflickr.com/<server>/<id>_<secret>_m.j
pg*/
data class Photos(
    val page: Int,
    val pages: Long,
    val perpage: Int,
    val total: Long,
    val photo: List<Photo>? = null,
)