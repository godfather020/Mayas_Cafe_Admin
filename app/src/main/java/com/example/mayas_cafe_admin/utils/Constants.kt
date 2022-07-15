package com.example.mayas_cafe_admin.utils

import kotlin.jvm.JvmField

object Constants {
    @JvmField
    var SET_ORDER_TAB = 0

    const val API_DEVELOPMENT_URL = "http://18.118.2.222:3000/api/"
    const val API_TESTING_URL = "testingURL"
    const val API_LIVE_URL = "liveURL"

    const val userNameError = "Enter a valid name"
    const val emptyFieldError = "Field can't be empty"
    const val phoneNumError = "Enter a valid phone number"
    @JvmField
    var cc = "+1"
    const val SPLASH_DELAY = 2000
    const val duration = 60
    @JvmField
    var orderId = ""
    @JvmField
    var orderStatus = ""
    @JvmField
    var orderPickUp = ""
    @JvmField
    var userPic = ""
    @JvmField
    var categoryName = ""
    @JvmField
    var categoryId = ""
    @JvmField
    var DEVICE_TOKEN = ""
    @JvmField
    var isLogin = false

    const val AdminProfile_Path = "http://18.118.2.222/mayas/mayasgarden/assets/images/"
    const val AdminCoupon_Path = "http://18.118.2.222/mayas/mayasgarden/assets/images/coupons/"
    const val AdminProduct_Path = "http://18.118.2.222/mayas/mayasgarden/assets/images/products/"

    object ApiConstant {

        //API END POINTS
        internal const val DASHBOARD = "public/Listhomedetails"
        internal const val CATEGORY = "public/Listcategory"
        internal const val VERIFY = "public/staffverify"
        internal const val GET_OTP = "public/stafflogin"
        internal const val REGISTER = "public/register"
        internal const val DEVICE_INFO = "public/deviceInfo"
        internal const val USER_PROFILE = "staff/getstaffprofile"
        internal const val UPDATE_PROFILE = "staff/Updatestaffprofile"
        internal const val MY_COUPONS = "public/Listcoupon"
        internal const val POPULAR_FOOD = "public/Listpopularproduct"
        internal const val RESTAURANT_CHOICES = "public/Listrestaurentproduct"
        internal const val LIST_PRODUCTS = "public/Listproduct"
        internal const val ADD_REMOVE_FAV = "customer/Createproductfavorite"
        internal const val NOTIFICATION = "customer/notifications"
        internal const val FAVORITE_LIST = "customer/Listfavoriteproduct"
        internal const val CREATE_ORDER = "customer/Createorder"
        internal const val GET_ORDERS = "staff/Listbranchorder"
        internal const val UPDATE_ORDER = "staff/Statusorder"
        internal const val UPDATE_PRODUCT = "staff/Updateproduct"
        internal const val SET_PROFILE_IMAGE = "staff/uploadstaffProfilePic"
        internal const val GET_PRODUCT_DETAILS = "public/Detailsproduct"
        internal const val REMOVE_NOTIFICATION = "customer/readNotification"
        internal const val REMOVE_ALL_NOTIFICATION = "customer/readAllNotification"
        internal const val CANCEL_ORDER = "staff/Cancelorder"
        internal var DEVICE_TOKEN = ""

    }

    object sharedPrefrencesConstant{

        internal const val DEVICE_TOKEN = "D-token"
        internal const val X_TOKEN="x-token"
        internal const val WALLET_BALANCE="wallet-balance"
        internal const val REFFERAL_AMOUNT="referral-balance"
        internal const val LOGIN="login"
        internal const val FIRST_TIME="first_time"
        internal const val USER_N = "userName"
        internal const val USER_P = "userPhone"
        internal const val USER_I = "userProfile"
        internal const val USER_E = "userEmail"
        internal const val OTP = "OTP"
        internal const val CATEGORYID = "categoryID"

    }
}