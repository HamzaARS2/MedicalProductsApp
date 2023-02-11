package com.ars.data.repository.auth

import com.ars.domain.utils.Resource
import com.ars.domain.repository.auth.IAuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

const val TAG = "FirebaseAuthImpl"

class FirebaseAuthImpl @Inject constructor(
    private val mAuth: FirebaseAuth
) : IAuthRepository {
    override val currentUser: FirebaseUser?
        get() = mAuth.currentUser


    override suspend fun register(
        email: String,
        password: String
    ): Resource<FirebaseUser?> {
        return try {
            val result = mAuth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user!!
            Resource.Success(user)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun login(email: String, password: String): Resource<FirebaseUser?> {
        return try {
            val result = mAuth.signInWithEmailAndPassword(email, password).await()
            val user = result.user!!
            Resource.Success(user)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun linkPhoneWithExistingAccount(
        verificationId: String,
        smsCode: String
    ): Resource<FirebaseUser?> {
        val user = mAuth.currentUser
        val credentials = PhoneAuthProvider.getCredential(verificationId, smsCode)
        return try {
            val response = user?.linkWithCredential(credentials)?.await()
            Resource.Success(response?.user)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }


    override suspend fun delete(): Boolean {
        return if (mAuth.currentUser != null) {
            return try {
                mAuth.currentUser!!.delete().await()
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }

        } else false
    }

    override fun logout() {
        if (currentUser != null)
            mAuth.signOut()
    }
}