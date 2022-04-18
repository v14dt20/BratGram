package com.example.bratgram.ui.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.canhub.cropper.*
import com.canhub.cropper.databinding.CropImageActivityBinding
import com.example.bratgram.MainActivity
import com.example.bratgram.R
import com.example.bratgram.activities.RegisterActivity
import com.example.bratgram.utilits.*
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {
    private val cropImage = registerForActivityResult(CropImageContract()) {
        if (it.isSuccessful) {
            val uriContent = it.uriContent
            val path = REF_STORAGE_ROOT.child(FOLDER_PROFILE_IMAGE).child(CURREN_UID)

            if (uriContent != null) {
                putImagetoStorage(uriContent, path) {
                    getUrlFromStorage(path) {
                        putUrlToDatabase(it) {
                            settings_user_photo.downloadAndSetImage(it)
                            showToast(getString(R.string.toast_data_update))
                            USER.photoUrl = it
                            APP_ACTIVITY.mAppDrawer.updateHeader()
                        }
                    }
                }
            }
        } else {
            showToast("Error")
        }
    }


    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        initFields()
    }

    private fun initFields() {
        settings_bio.text = USER.bio
        settings_full_name.text = USER.fullname
        settings_phone_number.text = USER.phone
        settings_status.text = USER.state
        settings_username.text = USER.username
        settings_btn_change_username.setOnClickListener { replaceFragment(ChangeUsernameFragment()) }
        settings_btn_change_bio.setOnClickListener { replaceFragment(ChangeBioFragment()) }
        settings_change_photo.setOnClickListener { changePhotoUser() }
        settings_user_photo.downloadAndSetImage(USER.photoUrl)
    }

    private fun changePhotoUser() {
        cropImage.launch(
            options {
                setAspectRatio(1, 1)
                setRequestedSize(600, 600)
                setCropShape(CropImageView.CropShape.OVAL)
            }
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val uri = CropImage.getPickImageResultUriContent(APP_ACTIVITY.baseContext, data)
            val path = REF_STORAGE_ROOT.child(FOLDER_PROFILE_IMAGE).child(CURREN_UID)
            path.putFile(uri)
        }
        showToast("OK")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.settings_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.settings_menu_exit -> {
                AUTH.signOut()
                APP_ACTIVITY.replaceActivity(RegisterActivity())
            }
            R.id.settings_menu_change_name -> replaceFragment(ChangeNameFragment())
        }
        return true
    }
}