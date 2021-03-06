package com.example.bratgram.ui.screens.base

import android.view.*
import com.example.bratgram.R
import com.example.bratgram.ui.screens.base.BaseFragment
import com.example.bratgram.utilits.APP_ACTIVITY
import com.example.bratgram.utilits.hideKeyboard


open class BaseChangeFragment (layout: Int): BaseFragment(layout) {

    override fun onStart() {
        super.onStart()
        setHasOptionsMenu(true)
        APP_ACTIVITY.mAppDrawer.disableDrawer()
    }

    override fun onStop() {
        super.onStop()
        hideKeyboard()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        APP_ACTIVITY.menuInflater.inflate(R.menu.settings_menu_confirm, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_confirm_change -> change()
        }
        return true
    }

    open fun change() {

    }

}