package com.example.litt.domain.interactor.register

import com.example.litt.presentation.register.exception.FirebaseRegisterException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class RegisterInteractorImpl : RegisterInteractor {

    override suspend fun signUp(fullName: String, email: String, password: String) : Unit = suspendCancellableCoroutine { continuation ->
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener { itSignUp ->
            if (itSignUp.isSuccessful) {
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(fullName)
                    .build()

                FirebaseAuth.getInstance().currentUser?.updateProfile(profileUpdates)?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        continuation.resume(Unit)
                    } else {
                        continuation.resumeWithException(FirebaseRegisterException(it.exception?.message))
                    }
                }
            } else {
                continuation.resumeWithException(FirebaseRegisterException(itSignUp.exception?.message))
            }
        }
    }

}