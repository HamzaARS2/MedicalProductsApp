package com.ars.data.repository.auth

import com.ars.domain.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val mAuth: FirebaseAuth
) {

    val isLoggedIn: Pair<Boolean,String?>
    get() = Pair(mAuth.currentUser != null, mAuth.currentUser?.uid)

    suspend fun signInCustomer(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = mAuth.signInWithEmailAndPassword(email, password).await()
            val user = result.user
            return if (user != null)
                Resource.Success(user)
            else Resource.Failure(NullPointerException("Can't sign in this user"))
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    fun logout() {
        if (mAuth.currentUser != null)
            mAuth.signOut()
    }

}