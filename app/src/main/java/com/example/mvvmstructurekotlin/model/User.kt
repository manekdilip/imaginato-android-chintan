package com.example.mvvmstructurekotlin.model

data class User(
    val errorCode: String,
    val errorMessage: String,
    val user: UserDetails
)