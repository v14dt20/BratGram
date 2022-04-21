package com.example.bratgram.utilits

import com.example.bratgram.database.*

enum class AppStates (val state: String) {
    ONLINE("В сети"),
    OFFLINE("Был недавно"),
    TYPING("печатает...");

    companion object {
        fun updateState(appStates: AppStates) {
            if (!CURREN_UID.isEmpty()) {
                REF_DATABASE_ROOT.child(NODE_USERS).child(CURREN_UID)
                    .child(CHILD_STATE).setValue(appStates.state)
                    .addOnSuccessListener { USER.state = appStates.state }
                    .addOnFailureListener { showToast(it.message.toString()) }
            }
        }
    }
}