package com.example.litt.domain.interactor.passwordRecovery

interface PasswordInteractor {

    suspend fun sendPasswordResetEmail(email : String)

}