package com.example.bratgram.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bratgram.R
import com.example.bratgram.databinding.ActivityRegisterBinding
import com.example.bratgram.ui.fragments.EnterPhoneNumberFragment
import com.example.bratgram.utilits.initFirebase
import com.example.bratgram.utilits.replaceFragment

class RegisterActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityRegisterBinding
    private lateinit var mToolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initFirebase()
    }

    override fun onStart() {
        super.onStart()
        mToolbar = mBinding.registerToolbar
        setSupportActionBar(mToolbar)
        title = getString(R.string.register_title_your_phone)
        replaceFragment(EnterPhoneNumberFragment())
    }
}