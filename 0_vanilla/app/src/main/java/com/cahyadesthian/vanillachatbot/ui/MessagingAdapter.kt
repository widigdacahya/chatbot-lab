package com.cahyadesthian.vanillachatbot.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cahyadesthian.vanillachatbot.R
import com.cahyadesthian.vanillachatbot.data.Message
import com.cahyadesthian.vanillachatbot.databinding.MessageItemBinding
import com.cahyadesthian.vanillachatbot.utils.Constants.RECEIVE_ID
import com.cahyadesthian.vanillachatbot.utils.Constants.SEND_ID

class MessagingAdapter: RecyclerView.Adapter<MessagingAdapter.MessageViewHolder>() {

    var messageList = mutableListOf<Message>()

    inner class MessageViewHolder(val itemViewBinding: MessageItemBinding): RecyclerView.ViewHolder(itemViewBinding.root) {

        init {
            itemViewBinding.root.setOnClickListener {
                messageList.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = MessageItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {

        //which side to display
        val currentMessage = messageList[position]

        when(currentMessage.id) {
            SEND_ID -> {
                holder.itemViewBinding.tvSenderMessage.apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.itemViewBinding.tvBotMessage.visibility = View.GONE
            }
            RECEIVE_ID -> {
                holder.itemViewBinding.tvBotMessage.apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.itemViewBinding.tvSenderMessage.visibility = View.GONE
            }
        }

    }

    override fun getItemCount(): Int = messageList.size


    fun insertMessage(message: Message) {
        this.messageList.add(message)
        notifyItemInserted(messageList.size)
        notifyDataSetChanged()
    }


}