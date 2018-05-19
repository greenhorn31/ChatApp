package com.example.part.chatapp.models

class ChatModel() {
    var text: String? = null
    var user: String? = null
    var time: String? = null

    constructor(text: String, time: String, user: String) : this() {
        this.text = text
        this.user = user
        this.time = time
    }
}