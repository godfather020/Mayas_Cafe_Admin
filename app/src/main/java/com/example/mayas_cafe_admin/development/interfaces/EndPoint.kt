package com.example.mayas_cafe_admin.development.interfaces

interface EndPoint {

    /** The base API URL. */
    fun getUrl(): String

    /** A name for differentiating multiple API URLs */
    fun getName(): String
}