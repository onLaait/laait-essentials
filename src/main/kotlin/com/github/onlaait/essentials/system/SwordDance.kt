package com.github.onlaait.essentials.system

import kotlin.random.Random

object SwordDance {

    private var i = 0

    fun onNotSwording() {
        i = 0
    }

    fun onSwording(): Boolean {
        if (i > 0) i--
        val zero = i == 0
        if (zero) i = Random.nextInt(2, 5)
        return zero
    }

    fun wasSwording(): Boolean = i > 1
}