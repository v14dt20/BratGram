package com.example.bratgram.ui.screens.groups

import androidx.recyclerview.widget.RecyclerView
import com.example.bratgram.R
import com.example.bratgram.database.*
import com.example.bratgram.models.CommonModel
import com.example.bratgram.ui.screens.base.BaseFragment
import com.example.bratgram.utilits.*
import kotlinx.android.synthetic.main.fragment_add_contacts.*

class AddContactsFragment : BaseFragment(R.layout.fragment_add_contacts) {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: AddContactsAdapter
    private val mRefContactsList = REF_DATABASE_ROOT.child(NODE_PHONES_CONTACTS).child(CURREN_UID)
    private val mRefUsers = REF_DATABASE_ROOT.child(NODE_USERS)
    private val mRefMessages = REF_DATABASE_ROOT.child(NODE_MESSAGES).child(CURREN_UID)
    private var mListItems = listOf<CommonModel>()

    override fun onResume() {
        super.onResume()
        listContacts.clear()
        APP_ACTIVITY.title = "Добавить участников"
        hideKeyboard()
        initRecyclerView()
        add_contacts_btn_next.setOnClickListener {
            if (listContacts.isEmpty()) {
                showToast("Добавте участников")
            } else replaceFragment(CreateGroupFragment(listContacts))
        }
    }

    private fun initRecyclerView() {
        mRecyclerView = add_contacts_recycle_view
        mAdapter = AddContactsAdapter()

        mRefContactsList.addListenerForSingleValueEvent(AppValueEventListener { dataSnapshot ->
            mListItems = dataSnapshot.children.map { it.getCommonModel() }
            mListItems.forEach { model ->

                mRefUsers.child(model.id)
                    .addListenerForSingleValueEvent(AppValueEventListener { dataSnapshot1 ->

                        val newModel = dataSnapshot1.getCommonModel()
                        mRefMessages.child(model.id).limitToLast(1)
                            .addListenerForSingleValueEvent(AppValueEventListener { dataSnapshot2 ->

                                val tempList = dataSnapshot2.children.map { it.getCommonModel() }
                                if (tempList.isEmpty()) {
                                    newModel.lastMessage = "Чат очищен"
                                } else {
                                    newModel.lastMessage = tempList[0].text
                                }

                                if (newModel.fullname.isEmpty()) {
                                    newModel.fullname = newModel.phone
                                }
                                mAdapter.updateListItems(newModel)
                            })
                    })

            }
        })

        mRecyclerView.adapter = mAdapter
    }

    companion object {
        val listContacts = mutableListOf<CommonModel>()

    }
}