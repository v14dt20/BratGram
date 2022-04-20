package com.example.bratgram.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.bratgram.MainActivity
import com.example.bratgram.R
import com.example.bratgram.activities.RegisterActivity
import com.example.bratgram.utilits.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_enter_code.*

class EnterCodeFragment(val phoneNumber: String, val id: String) :
    Fragment(R.layout.fragment_enter_code) {


    override fun onStart() {
        super.onStart()
        (activity as RegisterActivity).title = phoneNumber
        register_input_code.addTextChangedListener(AppTextWatcher {
            val string = register_input_code.text.toString()
            if (string.length == 6) {
                enterCode()
            }
        })
    }

    private fun enterCode() {
        val code = register_input_code.text.toString()
        val credential = PhoneAuthProvider.getCredential(id, code)

        AUTH.signInWithCredential(credential)
            .addOnCompleteListener(activity as RegisterActivity) { task ->
                if (task.isSuccessful) {
                    val uid = AUTH.currentUser?.uid.toString()
                    val dateMap = mutableMapOf<String, Any>()
                    dateMap[CHILD_ID] = CURREN_UID
                    dateMap[CHILD_PHONE] = phoneNumber
                    if (REF_DATABASE_ROOT.child(NODE_USERS).child(uid).child(CHILD_USERNAME).toString().isEmpty()) {
                        dateMap[CHILD_USERNAME] = uid
                    }

                    REF_DATABASE_ROOT.child(NODE_PHONES).child(phoneNumber).setValue(uid)
                        .addOnFailureListener { showToast(it.message.toString()) }
                        .addOnSuccessListener {
                            REF_DATABASE_ROOT.child(NODE_USERS).child(uid).updateChildren(dateMap)
                                .addOnSuccessListener {
                                    AppStates.updateState(AppStates.ONLINE)
                                    showToast("Добро пожаловать!")
                                    (activity as RegisterActivity).replaceActivity(MainActivity())
                                }
                                .addOnFailureListener { showToast(it.message.toString()) }
                        }


                } else showToast(task.exception?.message.toString())

            }
    }

}