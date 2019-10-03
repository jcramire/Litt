package com.example.litt.presentation.register.presenter

import android.util.Patterns
import androidx.core.util.PatternsCompat
import com.example.litt.domain.interactor.register.RegisterInteractor
import com.example.litt.presentation.register.RegisterContract
import com.example.litt.presentation.register.exception.FirebaseRegisterException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RegisterPresenter(registerInteractor : RegisterInteractor) : RegisterContract.RegisterPresenter, CoroutineScope {
    var view : RegisterContract.RegisterView? = null
    var registerInteractor : RegisterInteractor? = null
    override val coroutineContext: CoroutineContext= Dispatchers.Main

    init {
        this.registerInteractor = registerInteractor
    }

    override fun attachView(view: RegisterContract.RegisterView) {
        this.view = view
    }

    override fun dettachView() {
        view = null
    }

    override fun dettachJob() {
        coroutineContext.cancel()
    }

    override fun isViewAttached(): Boolean {
        return view != null
    }

    override fun checkEmptyFullName(fullname: String): Boolean {
        return fullname.isEmpty()
    }

    override fun checkValidEmail(email: String): Boolean {
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun checkEmptyPasswords(password1: String, password2: String): Boolean {
        return password1.isEmpty() or password2.isEmpty()
    }

    override fun checkPasswordMatch(password1: String, password2: String): Boolean {
        return password1 == password2
    }

    override fun signUp(fullname: String, email: String, password: String) {
        launch {
            view?.showProgressBar()
            try {
                registerInteractor?.signUp(fullname, email, password)
                if (isViewAttached()) {
                    view?.navigateToMain()
                    view?.hideProgressBar()
                }
            } catch (e : FirebaseRegisterException) {
                if (isViewAttached()) {
                    view?.hideProgressBar()
                    view?.showError(e.message)
                }
            }
        }
    }

}