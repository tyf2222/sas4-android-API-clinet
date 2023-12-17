package com.mena.login

import ApiService


val apiService = ApiService()
data class LoginData(

    val status: String = "default_status_value",
    val message: String = "default_message_value",
    var token : String = "${apiService.getToken()}"
)

