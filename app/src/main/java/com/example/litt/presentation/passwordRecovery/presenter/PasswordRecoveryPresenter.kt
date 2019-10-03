package com.example.litt.presentation.passwordRecovery.presenter

import androidx.core.util.PatternsCompat
import com.example.litt.domain.interactor.passwordRecovery.PasswordInteractor
import com.example.litt.presentation.passwordRecovery.PasswordRecoveryContract
import com.example.litt.presentation.passwordRecovery.exception.FirebaseRecoveryPasswordException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PasswordRecoveryPresenter(passwordInteractor: PasswordInteractor) : PasswordRecoveryContract.PasswordRecoveryPresenter, CoroutineScope {
    var view : PasswordRecoveryContract.PasswordRecoveryView? = null
    var passwordInteractor : PasswordInteractor? = null

    override val coroutineContext: CoroutineContext = Dispatchers.Main


    init {
        this.passwordInteractor = passwordInteractor
    }


    override fun attachView(view: PasswordRecoveryContract.PasswordRecoveryView) {
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

    override fun checkValidEmail(email: String): Boolean {
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun sendPasswordRecover(email: String) {
        launch {
            view?.showProgressBar()
            try {
                passwordInteractor?.sendPasswordResetEmail(email)
                view?.hideProgressBar()
                view?.navigateToLogin()
            } catch (e : FirebaseRecoveryPasswordException) {
                if (isViewAttached()) {
                    view?.hideProgressBar()
                    view?.showError(e.message)
                }
            }
        }
    }

}