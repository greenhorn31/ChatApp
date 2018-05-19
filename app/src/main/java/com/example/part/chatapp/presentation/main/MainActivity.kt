package com.example.part.chatapp.presentation.main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.part.chatapp.R
import com.example.part.chatapp.models.ChatModel
import com.example.part.chatapp.presentation.BaseActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : BaseActivity(), MainView {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var list: RecyclerView
    private lateinit var presenter: MainActivityPresenter

    private var adapter: MessageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    fun init() {
        firebaseAuth = FirebaseAuth.getInstance()
        list = findViewById(R.id.recyclerView)
        list.layoutManager = LinearLayoutManager(applicationContext)
        presenter = MainActivityPresenter(firebaseAuth, this)
    }

    override fun initAdapter(email: String) {
        adapter = MessageAdapter(email, ArrayList<ChatModel>())
        list.adapter = adapter
    }

    override fun initListeners() {
        send.setOnClickListener {
            sendMessage()
        }
    }

    override fun showMessage(chatMessage: ChatModel) {
        adapter?.add(chatMessage)
        list.scrollToPosition(adapter?.itemCount!! - 1)
    }

    private fun sendMessage() {
        presenter.sendMessage(sendText.text.toString())
        sendText.text = null
    }

    override fun onDestroy() {
        super.onDestroy()
        firebaseAuth.signOut()
    }
}
