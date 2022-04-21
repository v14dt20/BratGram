package com.example.bratgram.ui.screens.groups

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.example.bratgram.R
import com.example.bratgram.database.*
import com.example.bratgram.models.CommonModel
import com.example.bratgram.ui.screens.base.BaseFragment
import com.example.bratgram.ui.screens.main_list.MainListFragment
import com.example.bratgram.utilits.*
import kotlinx.android.synthetic.main.fragment_add_contacts.*
import kotlinx.android.synthetic.main.fragment_create_group.*
import kotlinx.android.synthetic.main.fragment_settings.*

class CreateGroupFragment(private val listContacts: List<CommonModel>): BaseFragment(R.layout.fragment_create_group) {
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: AddContactsAdapter
    private var mUri = Uri.EMPTY
    private val cropImage = registerForActivityResult(CropImageContract()) {
        if (it.isSuccessful) {
            val uriContent = it.uriContent
            mUri = uriContent
            create_group_photo.setImageURI(mUri)
        } else {
            showToast("Error")
        }
    }

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Создать группу"
        hideKeyboard()
        initRecyclerView()
        create_group_photo.setOnClickListener { addPhoto() }
        create_group_btn_complete.setOnClickListener {
            val nameGroup = create_group_input_name.text.toString()
            if (nameGroup.isEmpty()) {
                showToast("Введите имя")
            } else {

                createGroupToDatabase(nameGroup, mUri, listContacts) {
                    replaceFragment((MainListFragment()))
                }
            }
        }
        create_group_input_name.requestFocus()
        create_group_counts.text = getPlurals(listContacts.size)
    }



    private fun addPhoto() {
        cropImage.launch(
            options {
                setAspectRatio(1, 1)
                setRequestedSize(250, 250)
                setCropShape(CropImageView.CropShape.OVAL)
            }
        )
    }

    private fun initRecyclerView() {
        mRecyclerView = create_group_recycle_view
        mAdapter = AddContactsAdapter()
        mRecyclerView.adapter = mAdapter
        listContacts.forEach {
            mAdapter.updateListItems(it)
        }
    }
}