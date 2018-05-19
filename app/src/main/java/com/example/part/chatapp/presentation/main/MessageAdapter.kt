package com.example.part.chatapp.presentation.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.part.chatapp.R
import com.example.part.chatapp.models.ChatModel


class MessageAdapter(private val email: String, private var chatList: ArrayList<ChatModel>) :
        RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    private val RECEIVED = 1
    private val SENT = 2

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.text.text = chatList[position].text
        holder.email.text = chatList[position].user
        holder.time.text = chatList[position].time
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        var layout = 0
        when (viewType) {
            RECEIVED -> layout = R.layout.chat_item
            SENT -> layout = R.layout.chat_item_right
        }
        val v = LayoutInflater.from(parent.context)
                .inflate(layout, parent, false)
        val holder = MessageViewHolder(v)
//        v.listItem.setOnClickListener {
//            val position = holder.adapterPosition
//            val elementPos = data.keys.elementAt(position)
//            listener.interactionClick(elementPos)
//        }
        return holder
    }

    override fun getItemViewType(position: Int): Int {
        return if (chatList[position].user.equals(email)) SENT else RECEIVED
    }

    fun add(chatMessage: ChatModel) {
        chatList.add(chatMessage)
        notifyItemInserted(chatList.size - 1)
    }


    fun remove(position: Int) {
        chatList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text = itemView.findViewById<TextView>(R.id.text)
        val email = itemView.findViewById<TextView>(R.id.userName)
        val time = itemView.findViewById<TextView>(R.id.time)
    }
}