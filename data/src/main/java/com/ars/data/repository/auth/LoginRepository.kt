package com.ars.data.repository.auth

import com.ars.domain.utils.Resource
import com.ars.domain.utils.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val mAuth: FirebaseAuth
) {

    val isLoggedIn: Pair<Boolean,String?>
    get() = Pair(mAuth.currentUser != null, mAuth.currentUser?.uid)

    suspend fun signInCustomer(email: String, password: String): Flow<Response<FirebaseUser>> {
        val result = try {
            val result = mAuth.signInWithEmailAndPassword(email, password).await()
            val user = result.user
             if (user != null)
                Response.Success(user)
            else Response.Error(NullPointerException("Can't sign in this user"))
        } catch (e: Exception) {
            e.printStackTrace()
            Response.Error(e)
        }
        return flowOf(result)
    }

    fun logout() {
        if (mAuth.currentUser != null)
            mAuth.signOut()
    }

}