package com.cahyadesthian.vanillachatbot.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.cahyadesthian.vanillachatbot.data.Message
import com.cahyadesthian.vanillachatbot.databinding.ActivityMainBinding
import com.cahyadesthian.vanillachatbot.utils.BotResponse
import com.cahyadesthian.vanillachatbot.utils.Constants.OPEN_GOOGLE
import com.cahyadesthian.vanillachatbot.utils.Constants.OPEN_SEARCH
import com.cahyadesthian.vanillachatbot.utils.Constants.RECEIVE_ID
import com.cahyadesthian.vanillachatbot.utils.Constants.SEND_ID
import com.cahyadesthian.vanillachatbot.utils.Time
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private var _mainBinding : ActivityMainBinding? = null
    private val mainBinding get() = _mainBinding!!

    private lateinit var adapter: MessagingAdapter
    private val botList = listOf("Peter","Francesca","Igor","Luigi")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        recyclerViewSetUp()

        clickEvent()

        val random = (0..3).random()
        customMessage("Hello, here is ${botList[random]}, May I help?")

    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun clickEvent() {
        mainBinding.btnSend.setOnClickListener {
            sendMessage()
        }

        mainBinding.etMessage.setOnClickListener{
            GlobalScope.launch {
                delay(1000)
                withContext(Dispatchers.Main) {
                    mainBinding.rvMessages.scrollToPosition(adapter.itemCount-1)
                }
            }
        }

    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun customMessage(message:String) {
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                val timeStamp = Time.timeStamp()
                adapter.insertMessage(Message(message,RECEIVE_ID,timeStamp))

                //get last message possitioning
                mainBinding.rvMessages.scrollToPosition(adapter.itemCount-1)
            }
        }
    }

    private fun recyclerViewSetUp() {
        adapter = MessagingAdapter()
        mainBinding.rvMessages.adapter = adapter
        mainBinding.rvMessages.layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun sendMessage() {
        val message = mainBinding.etMessage.text.toString()
        val timeStamp = Time.timeStamp()

        if(message.isNotEmpty()) {
            mainBinding.etMessage.setText("")

            adapter.insertMessage(Message(message,SEND_ID, timeStamp))
            mainBinding.rvMessages.scrollToPosition(adapter.itemCount-1)

            botResponse(message)
        }


    }


    @OptIn(DelicateCoroutinesApi::class)
    private fun botResponse(message: String) {

        val timeStamp = Time.timeStamp()

        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                val response = BotResponse.basicResponse(message)
                adapter.insertMessage(Message(response, RECEIVE_ID,timeStamp))

                mainBinding.rvMessages.scrollToPosition(adapter.itemCount-1)

                when(response) {
                    OPEN_GOOGLE -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    OPEN_SEARCH -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchedTerm: String? = message.substringAfter("search")
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchedTerm")
                        startActivity(site)
                    }
                }
            }
        }


    }


    @OptIn(DelicateCoroutinesApi::class)
    override fun onStart() {
        super.onStart()

        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                mainBinding.rvMessages.scrollToPosition(adapter.itemCount-1)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _mainBinding = null
    }
}