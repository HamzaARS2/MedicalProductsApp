package com.ars.data.repository.auth

import com.ars.domain.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RegistrationRepository @Inject constructor(
    private val mAuth: FirebaseAuth
) {

    suspend fun createNewCustomer(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = mAuth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user
            return if (user != null)
                Resource.Success(user)
            else Resource.Failure(NullPointerException("Can't create a new user"))

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    suspend fun linkPhoneWithExistingAccount(
        verificationId: String,
        smsCode: String
    ): Resource<FirebaseUser?> {
        val user = mAuth.currentUser
        val credentials = PhoneAuthProvider.getCredential(verificationId, smsCode)
        return try {
            return if (user != null) {
                val response = user.linkWithCredential(credentials).await()
                Resource.Success(response.user)
            } else Resource.Failure(NullPointerException("Can't perform this operation. Please re-authenticate and try again"))

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}