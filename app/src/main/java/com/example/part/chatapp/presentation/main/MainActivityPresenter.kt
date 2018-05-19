package com.example.part.chatapp.presentation.main

import android.util.Log
import com.example.part.chatapp.models.ChatModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivityPresenter(private val firebaseAuth: FirebaseAuth, private val view: MainView) {

    private lateinit var firebaseUser: FirebaseUser
    private lateinit var dbReference: DatabaseReference

    private val email: String = "greenhorn2010@ukr.net"

    init {
        if (firebaseAuth.currentUser == null) {

            firebaseAuth.signInWithEmailAndPassword(email, "123456")
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            init()
                        } else {
                            Log.d(TAG, "firebase authentication failed")
                        }
                    }
        } else {
            init()
        }
    }

    fun init() {
        firebaseUser = firebaseAuth.currentUser!!
        dbReference = FirebaseDatabase.getInstance().reference
        view.initAdapter(firebaseUser.email!!)
        initDBListener()
        view.initListeners()
    }

    private fun initDBListener() {
        dbReference.child("HI")!!.addChildEventListener(
                object : ChildEventListener {
                    override fun onChildAdded(p0: DataSnapshot?, p1: String?) {
                        val chatMessage: ChatModel? = p0?.getValue(ChatModel::class.java)
                        if (chatMessage != null) {
                            view.showMessage(chatMessage)
                        }
                    }

                    override fun onCancelled(p0: DatabaseError?) {}
                    override fun onChildMoved(p0: DataSnapshot?, p1: String?) {}
                    override fun onChildChanged(p0: DataSnapshot?, p1: String?) {}
                    override fun onChildRemoved(p0: DataSnapshot?) {}
                }
        )
    }

    fun sendMessage(message: String) {
        dbReference.child("HI")?.push()?.setValue(
                ChatModel(message,
                        SimpleDateFormat("EEE, HH:mm").format(Calendar.getInstance().time),
                        firebaseUser.email!!))
    }

    val TAG = "main_activity"
}