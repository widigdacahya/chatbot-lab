package com.cahyadesthian.apichatbot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.cahyadesthian.apichatbot.databinding.ActivityMainBinding
import com.cahyadesthian.apichatbot.model.BotResponse
import com.cahyadesthian.apichatbot.model.ChatModel
import com.cahyadesthian.apichatbot.networking.ApiService
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private var _mainBinding : ActivityMainBinding? = null
    private val mainBinding get() = _mainBinding!!

    private lateinit var chatList : ArrayList<ChatModel>

    companion object {
        const val BOT_MESSAGE_KEY= "bot"
        const val USER_MESSAGE_KEY= "user"
    }

    private lateinit var adapter: ChatRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        _mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        chatList = arrayListOf()

        adapter = ChatRVAdapter(this,chatList)
        mainBinding.rvMessageArea.adapter = adapter
        mainBinding.rvMessageArea.layoutManager = LinearLayoutManager(applicationContext)


        mainBinding.btnSend.setOnClickListener {
            if(mainBinding.etMessage.text.isNotEmpty()) {
                getResponse(mainBinding.etMessage.text.toString())
                mainBinding.etMessage.setText("")
            }
        }

    }

    private fun getResponse(message: String) {
        chatList.add(ChatModel(message,USER_MESSAGE_KEY))
        adapter.notifyDataSetChanged()
        val url = "http://api.brainshop.ai/get?bid=166207&key=We1Dz5ZfDzZWHvDU&uid=[uid]&msg=$message"
        val BASE_URL = "http://api.brainshop.ai/"
        val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        val retrofitApi = retrofit.create(ApiService::class.java)
//        val call : Call<BotResponse> = retrofitApi.getBotResponse(url)
//
//        call.enqueue(object Callback<BotResponse> {
//
//        })
        retrofitApi.getBotResponse(url).enqueue(object : Callback<BotResponse>{
            override fun onResponse(call: Call<BotResponse>, response: Response<BotResponse>) {
                if(response.isSuccessful) {
                    val modal : BotResponse? = response.body()
                    modal?.let { ChatModel(it.cnt, BOT_MESSAGE_KEY) }?.let { chatList.add(it) }
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BotResponse>, t: Throwable) {
                chatList.add(ChatModel("Revert Question please", BOT_MESSAGE_KEY))
                adapter.notifyDataSetChanged()
            }

        })

    }


    override fun onDestroy() {
        super.onDestroy()
        _mainBinding = null
    }

}