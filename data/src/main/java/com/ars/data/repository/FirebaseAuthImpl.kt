package com.ars.data.repository

import android.util.Log
import com.ars.domain.utils.Resource
import com.ars.domain.repository.IAuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

const val TAG = "FirebaseAuthImpl"
class FirebaseAuthImpl @Inject constructor(
    private val mAuth: FirebaseAuth
): IAuthRepository {
    override val currentUser: FirebaseUser?
        get() = mAuth.currentUser


    override suspend fun register(
        email: String,
        password: String
    ): Resource<FirebaseUser> {
        return try {
            val result = mAuth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user!!
            Resource.Success(user)
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Failure(e)
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

    override fun loginState(onLoginStateChanged: (loggedIn: Boolean) -> Unit) {
        mAuth.addAuthStateListener {
            onLoginStateChanged(it.currentUser != null)
        }
    }

    override suspend fun delete(): Boolean {
        currentUser?.delete()
        return true
    }

    override fun logout() {
        if (currentUser != null)
        mAuth.signOut()
    }
}