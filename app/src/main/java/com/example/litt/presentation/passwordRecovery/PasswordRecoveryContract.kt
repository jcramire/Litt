package com.example.litt.presentation.passwordRecovery

interface PasswordRecoveryContract {

    interface PasswordRecoveryView {
        fun showError(msgError : String?)
        fun showProgressBar()
        fun hideProgressBar()
        fun recoverPassword()
        fun navigateToLogin()
    }

    interface PasswordRecoveryPresenter {
        fun attachView(view : PasswordRecoveryView)
        fun dettachView()
        fun dettachJob()
        fun isViewAttached() : Boolean
        fun checkValidEmail(email : String) : Boolean
        fun sendPasswordRecover(email : String)
    }
}