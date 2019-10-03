package com.example.litt.domain.interactor.register

interface RegisterInteractor {

    suspend fun signUp(fullName : String, email : String, password : String)
}