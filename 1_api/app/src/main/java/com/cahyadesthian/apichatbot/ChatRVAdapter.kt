package com.cahyadesthian.apichatbot

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cahyadesthian.apichatbot.databinding.BotMsgRvItemBinding
import com.cahyadesthian.apichatbot.databinding.UserMsgRvItemBinding
import com.cahyadesthian.apichatbot.model.ChatModel
import java.nio.file.Files.find


class ChatRVAdapter(context: Context, listMessage: ArrayList<ChatModel>) :  RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    companion object {
        const val BOT_MESSAGE_VIEW = 0
        const val USER_MESSAGE_VIEW = 1
    }

    //private val ourContext: Context = context
    var theMessageList: ArrayList<ChatModel> = listMessage

    private inner class BotMessageItemViewHolder(val itemBotMsgBinding: BotMsgRvItemBinding): RecyclerView.ViewHolder(itemBotMsgBinding.root) {

        fun bindBotMsg(position: Int) {
            val recyclerViewModel = theMessageList[position]
            itemBotMsgBinding.tvBot.text = recyclerViewModel.message
        }

    }

    private inner class UserMessageItemViewHolder(val itemUserMsgBinding: UserMsgRvItemBinding): RecyclerView.ViewHolder(itemUserMsgBinding.root) {

        fun bindUserMsg(position: Int) {
            val recyclerViewModel = theMessageList[position]
            itemUserMsgBinding.tvUser.text = recyclerViewModel.message
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        when(viewType) {
//            0 -> return BotMessageItemViewHolder(BotMsgRvItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
//            1 -> return UserMessageItemViewHolder(UserMsgRvItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
//        }
        return if(viewType == 0) {
            BotMessageItemViewHolder(BotMsgRvItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        } else {
            UserMessageItemViewHolder(UserMsgRvItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //val chatsModal = theMessageList[position]
//        when(chatsModal.sender) {
//            "bot" -> BotMessageItemViewHolder.bindBo
//        }
        if(theMessageList[position].sender == "bot") {
            (holder as BotMessageItemViewHolder).bindBotMsg(position)
        } else {
            (holder as UserMessageItemViewHolder).bindUserMsg(position)
        }
    }

    override fun getItemCount(): Int = theMessageList.size

    override fun getItemViewType(position: Int): Int {
//        return if(theMessageList.get(position).sender == "bot") {
//            return 0
//        } else {
//            return 1
//        }
//        when(theMessageList[position].sender) {
//            "bot" -> return 0
//            "user" -> return 1
//
//        }
        return if(theMessageList[position].sender== "bot") {
            0
        } else {
            1
        }
    }


}