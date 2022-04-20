package com.example.bratgram.ui.fragments

import androidx.fragment.app.Fragment
import com.example.bratgram.R
import com.example.bratgram.utilits.APP_ACTIVITY

class MainFragment : Fragment(R.layout.fragment_chats) {

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "BratGram"
        APP_ACTIVITY.mAppDrawer.enableDrawer()
    }
}