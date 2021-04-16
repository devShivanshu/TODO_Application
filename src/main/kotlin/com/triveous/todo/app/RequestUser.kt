package com.triveous.todo.app

import com.google.gson.annotations.SerializedName


data class RequestUser (

        @SerializedName("username") var username : String,
        @SerializedName("password") var password : String

)