package com.example.litt.presentation.register.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.litt.R
import com.example.litt.base.BaseActivity
import com.example.litt.domain.interactor.register.RegisterInteractorImpl
import com.example.litt.presentation.main.view.MainActivity
import com.example.litt.presentation.register.RegisterContract
import com.example.litt.presentation.register.presenter.RegisterPresenter
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity(), RegisterContract.RegisterView {

    lateinit var presenter: RegisterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = RegisterPresenter(RegisterInteractorImpl())
        presenter.attachView(this)

        btnSigUp.setOnClickListener {
            signUp()
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_register
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

    override fun signUp() {
        val fullName = etxtFullName.text.toString().trim()
        val email = etxtEmail.text.toString().trim()
        val password1 = etxtPassword1.text.toString().trim()
        val password2 = etxtPassword2.text.toString().trim()

        if (presenter.checkEmptyFullName(fullName)) {
            etxtFullName.error = "El nombre esta vacio."
            return
        }

        if (!presenter.checkValidEmail(email)) {
            etxtEmail.error = "El email es invalido."
            return
        }

        if (presenter.checkEmptyPasswords(password1, password2)) {
            etxtPassword1.error = "La password 1 esta vacio."
            etxtPassword2.error = "La password 2 esta vacio."
            return
        }

        if (!presenter.checkPasswordMatch(password1, password2)) {
            etxtPassword1.error = "La password 1 no es igual."
            etxtPassword2.error = "La password 2 no es igual."
            return
        }

        presenter.signUp(fullName, email, password1)
    }

    override fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
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
