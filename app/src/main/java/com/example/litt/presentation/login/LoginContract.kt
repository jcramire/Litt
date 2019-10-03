package com.example.litt.presentation.login

interface LoginContract {

    interface LoginView {
        fun showError(msgError : String?)
        fun showProgressBar()
        fun hideProgressBar()
        fun signIn()
        fun navigateToMain()
        fun navigateToRegister()
        fun navigateToPassworRecovery()
    }

    interface LoginPresenter {
        fun attachView(view : LoginView)
        fun dettachView()
        fun dettachJob()
        fun isViewAttached() : Boolean
        fun sigInUserWithEmailAndPassword(email : String, password : String)
        fun checkEmptyFields(email : String, password : String) : Boolean
    }
}