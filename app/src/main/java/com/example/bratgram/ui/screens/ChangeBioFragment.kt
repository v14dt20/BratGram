package com.example.bratgram.ui.screens

import com.example.bratgram.R
import com.example.bratgram.database.USER
import com.example.bratgram.database.setBioToDatabase
import kotlinx.android.synthetic.main.fragment_change_bio.*

class ChangeBioFragment : BaseChangeFragment(R.layout.fragment_change_bio) {

    override fun onResume() {
        super.onResume()
        if (!USER.bio.isEmpty()) settings_input_bio.setText(USER.bio)
    }

    override fun change() {
        super.change()
        val newBio = settings_input_bio.text.toString()

        setBioToDatabase(newBio)


    }
}