package com.example.bratgram.ui.fragments

import com.example.bratgram.R
import com.example.bratgram.utilits.*
import kotlinx.android.synthetic.main.fragment_change_bio.*

class ChangeBioFragment : BaseChangeFragment(R.layout.fragment_change_bio) {

    override fun onResume() {
        super.onResume()
        if (!USER.bio.isEmpty()) settings_input_bio.setText(USER.bio)
    }

    override fun change() {
        super.change()
        val newBio = settings_input_bio.text.toString()
        REF_DATABASE_ROOT.child(NODE_USERS).child(CURREN_UID).child(CHILD_BIO).setValue(newBio).addOnCompleteListener {
            if (it.isSuccessful) {
                showToast(getString(R.string.toast_data_update))
                USER.bio = newBio
                fragmentManager?.popBackStack()
            }
        }
    }

}