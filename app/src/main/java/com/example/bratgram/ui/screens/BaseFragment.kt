package com.example.bratgram.ui.screens

import androidx.fragment.app.Fragment
import com.example.bratgram.utilits.APP_ACTIVITY


open class BaseFragment(layout: Int) : Fragment(layout) {
    override fun onStart() {
        super.onStart()
        APP_ACTIVITY.mAppDrawer.disableDrawer()
    }
}