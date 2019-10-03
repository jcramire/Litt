package com.example.litt.presentation.login.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.litt.R
import com.example.litt.base.BaseActivity
import com.example.litt.domain.interactor.login.SignInInteractorImpl
import com.example.litt.presentation.login.LoginContract
import com.example.litt.presentation.login.presenter.LoginPresenter
import com.example.litt.presentation.main.view.MainActivity
import com.example.litt.presentation.passwordRecovery.view.PasswordRecoveryActivity
import com.example.litt.presentation.register.view.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.etxtEmail
import kotlinx.android.synthetic.main.activity_login.progressBar

class LoginActivity : BaseActivity(), LoginContract.LoginView {
    val TAG = this::class.java.simpleName
    lateinit var presenter : LoginPresenter

    // etxtName
    // txtName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = LoginPresenter(SignInInteractorImpl())
        presenter.attachView(this)

        btnPasswordRecovery.setOnClickListener {
            navigateToPassworRecovery()
        }

        btnSigIn.setOnClickListener {
            signIn()
        }

        btnRegister.setOnClickListener {
            navigateToRegister()
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_login
    }

    override fun showError(msgError : String?) {
        toast(msgError, Toast.LENGTH_LONG)
    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun signIn() {
        if (presenter.checkEmptyFields(etxtEmail.text.toString().trim(), etxtPassword.text.toString().trim())) toast("Debes completar todos los parametros")
        else presenter.sigInUserWithEmailAndPassword(etxtEmail.text.toString().trim(), etxtPassword.text.toString().trim())
    }

    override fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun navigateToRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    override fun navigateToPassworRecovery() {
        startActivity(Intent(this, PasswordRecoveryActivity::class.java))
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

//    private suspend fun fakeApiRequest() {
//        val result = getResultApi()
//        withContext(Main){
//            data.text = result
//        }
//    }
//
//    private suspend fun getResultApi() : String {
//        logThread()
//        delay(1000)
//        return "resultado 1"
//    }
//
//    private fun logThread() {
//        Log.d(TAG, "getResultApi ${Thread.currentThread().name}")
//    }
//}
