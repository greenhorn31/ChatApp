package com.example.part.chatapp.presentation.main

import com.example.part.chatapp.models.ChatModel

interface MainView {
    fun showMessage(chatMessage: ChatModel)
    fun initAdapter(email: String)
    fun initListeners()
}