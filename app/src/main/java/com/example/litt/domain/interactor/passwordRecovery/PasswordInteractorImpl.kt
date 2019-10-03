package com.example.litt.domain.interactor.passwordRecovery

import com.example.litt.domain.interactor.register.RegisterInteractor
import com.example.litt.presentation.passwordRecovery.exception.FirebaseRecoveryPasswordException
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class PasswordInteractorImpl : PasswordInteractor {

    override suspend fun sendPasswordResetEmail(email : String) : Unit = suspendCancellableCoroutine { continuation ->
        FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful) {
                continuation.resume(Unit)
            } else {
                continuation.resumeWithException(FirebaseRecoveryPasswordException(it.exception?.message))
            }
        }
    }
}