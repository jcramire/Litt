package com.example.litt.presentation.register

interface RegisterContract {

    interface RegisterView {
        fun showError(msgError : String?)
        fun showProgressBar()
        fun hideProgressBar()
        fun navigateToMain()
        fun signUp()
    }

    interface RegisterPresenter {
        fun attachView(view : RegisterView)
        fun dettachView()
        fun dettachJob()
        fun isViewAttached() : Boolean
        fun checkEmptyFullName(fullName : String) : Boolean
        fun checkValidEmail(email : String) : Boolean
        fun checkEmptyPasswords(password1 : String, password2 : String) : Boolean
        fun checkPasswordMatch(password1 : String, password2 : String) : Boolean
        fun signUp(fullname : String, email : String, password : String)
    }
}