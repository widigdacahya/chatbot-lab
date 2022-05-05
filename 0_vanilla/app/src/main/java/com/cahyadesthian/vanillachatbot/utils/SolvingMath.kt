package com.cahyadesthian.vanillachatbot.utils

import android.util.Log

object SolvingMath {

    fun solveMath(equation: String): Int {

        val theEquation = equation.replace(" ","")
        //Log.d("Math", theEquation)

        return when {
            theEquation.contains("+") -> {
                val split = theEquation.split("+")
                val result = split[0].toInt() + split[1].toInt()
                result
            }
            theEquation.contains("-") -> {
                val split = theEquation.split("-")
                val result = split[0].toInt()-split[1].toInt()
                result
            }
            theEquation.contains("*") -> {
                val split = theEquation.split("*")
                val result = split[0].toInt() * split[1].toInt()
                result
            }
            theEquation.contains("/") -> {
                val split = theEquation.split("/")
                val result = split[0].toInt() / split[1].toInt()
                result
            }
            else -> 0
        }


    }


}