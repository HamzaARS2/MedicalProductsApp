package com.ars.groceriesapp

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import java.util.concurrent.TimeUnit

class PhoneVerifier(
     var phoneNumber: String = "",
    activity: Activity,
    mCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks,
) {
    private val options = PhoneAuthOptions.newBuilder()
        .setPhoneNumber(phoneNumber)
        .setActivity(activity)
        .setTimeout(60L, TimeUnit.SECONDS)
        .setCallbacks(mCallback)

    fun verify() {
        PhoneAuthProvider
            .verifyPhoneNumber(options.build())
    }

    fun resendSMSCode(resendingToken: ForceResendingToken?) {
        resendingToken ?: return
        options.setForceResendingToken(resendingToken)
        PhoneAuthProvider.verifyPhoneNumber(options.build())
    }
}