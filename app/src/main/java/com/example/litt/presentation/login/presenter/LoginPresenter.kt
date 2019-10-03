package com.example.litt.presentation.login.presenter

import com.example.litt.domain.interactor.login.SignInteractor
import com.example.litt.presentation.login.LoginContract
import com.example.litt.presentation.login.exception.FirebaseLoginException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LoginPresenter(sigInInteractor: SignInteractor) : LoginContract.LoginPresenter, CoroutineScope {
    var view : LoginContract.LoginView? = null
    var sigInInteractor : SignInteractor? = null

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    init {
        this.sigInInteractor = sigInInteractor
    }

    override fun attachView(view: LoginContract.LoginView) {
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

    override fun sigInUserWithEmailAndPassword(email: String, password: String) {
        launch {
            view?.showProgressBar()
            try {
                sigInInteractor?.signInWithEmailAndPassword(email, password)
                if (isViewAttached()) {
                    view?.navigateToMain()
                    view?.hideProgressBar()
                }
            } catch (e : FirebaseLoginException) {
                if (isViewAttached()) {
                    view?.hideProgressBar()
                    view?.showError(e.message)
                }
            }
        }
    }

    override fun checkEmptyFields(email: String, password: String) : Boolean {
        return email.isEmpty() || password.isEmpty()
    }
}