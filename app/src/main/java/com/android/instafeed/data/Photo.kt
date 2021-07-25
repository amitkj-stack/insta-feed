package com.android.instafeed.data

/*
* https://live.staticflickr.com/<server>/<id>_<secret>_m.j
pg*/
data class Photo(
    val id: String? = null,
    val title: String? = null,
    private val server: String? = null,
    private val secret: String? = null,
) {
    val link: String
        get() {
            return "https://live.staticflickr.com/$server/${id}_${secret}_m.jpg"
        }
}