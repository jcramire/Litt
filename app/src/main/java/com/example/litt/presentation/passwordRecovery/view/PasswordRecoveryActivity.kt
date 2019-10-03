package com.example.litt.presentation.passwordRecovery.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.litt.R
import com.example.litt.base.BaseActivity
import com.example.litt.domain.interactor.passwordRecovery.PasswordInteractorImpl
import com.example.litt.presentation.login.view.LoginActivity
import com.example.litt.presentation.passwordRecovery.PasswordRecoveryContract
import com.example.litt.presentation.passwordRecovery.presenter.PasswordRecoveryPresenter
import kotlinx.android.synthetic.main.activity_password_recovery.*

class PasswordRecoveryActivity : BaseActivity(), PasswordRecoveryContract.PasswordRecoveryView {

    lateinit var presenter : PasswordRecoveryContract.PasswordRecoveryPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = PasswordRecoveryPresenter(PasswordInteractorImpl())
        presenter.attachView(this)

        btnRecoveryPassword.setOnClickListener {
            recoverPassword()
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_password_recovery
    }

    override fun showError(msgError: String?) {
        toast(msgError)
    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun recoverPassword() {
        val email = etxtEmail.text.toString().trim()

        if (!presenter.checkValidEmail(email)) {
            etxtEmail.error = "El email es invalido."
            return
        }

        presenter.sendPasswordRecover(email)
    }

    override fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        presenter.dettachView()
        presenter.dettachJob()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dettachView()
        presenter.dettachJob()
    }
}
