package com.example.litt.domain.interactor.login

interface SignInteractor {

    suspend fun signInWithEmailAndPassword(email : String, password : String)
}