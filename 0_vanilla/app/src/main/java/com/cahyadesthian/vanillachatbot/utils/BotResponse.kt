package com.cahyadesthian.vanillachatbot.utils

import com.cahyadesthian.vanillachatbot.utils.Constants.OPEN_GOOGLE
import com.cahyadesthian.vanillachatbot.utils.Constants.OPEN_SEARCH

object BotResponse {

    fun basicResponse(_message: String): String {

        val random = (0..2).random()
        val message = _message.lowercase()

        return when {

            //user send hello
            message.contains("hello") -> {
                when(random) {
                    0 -> "Hi There!"
                    1 -> "Holla"
                    2 -> "Hwellloooo"
                    else -> "error"
                }
            }

            //user send How are you
            message.contains("how are you") -> {
                when(random) {
                    0 -> "Fine here, thanks for asking :)"
                    1 -> "Hungry, food plz"
                    2 -> "Preety good. Beautiful day right"
                    else -> "error"
                }
            }

            //for opening goofle
            message.contains("open") && message.contains("google") -> {
                OPEN_GOOGLE
            }

            //for search
            message.contains("search") -> {
                OPEN_SEARCH
            }

            //get time time
            message.contains("time") && message.contains("?") -> {
                Time.timeStamp()
            }

            //solving math
            message.contains("solve") -> {
                val equation: String? = message.substringAfter("solve")
                return try {
                    val answer = SolvingMath.solveMath(equation?:"0")
                    answer.toString()
                }catch (e:Exception) {
                    "Sorry, cant solve that"
                }
            }

            //flip coin
            message.contains("flip") && message.contains("coin") -> {
                var r = (0..1).random()
                val result = if(r==0) "head" else "tails"

                "Coin flipped, the result is $result"
            }

            else -> {
                when(random) {
                    0 -> "Sorry, not learnign that yet"
                    1 -> "Sorry, IDK :')"
                    2 -> "Would you mind to ask other things?"
                    else -> "error"
                }
            }
        }

    }

}