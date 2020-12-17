package com.example.mvvmstructurekotlin.repository

import android.content.Context
import com.example.mvvmstructurekotlin.repository.dbhandler.login.LoginDatabase
import com.example.mvvmstructurekotlin.repository.dbhandler.login.LoginRepository
import org.koin.core.KoinComponent

class RepoModel(context: Context) : KoinComponent {

    var loginDatabase = LoginRepository(LoginDatabase.getDatabaseClient(context).loginDao())


}