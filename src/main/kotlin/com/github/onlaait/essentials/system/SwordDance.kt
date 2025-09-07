package com.github.onlaait.essentials.system

import kotlin.random.Random

object SwordDance {

    private var i = 0
    private var i2 = 0

    fun onNotSwording() {
        i = 0
        i2 = 0
    }

    fun onSwording(): Boolean {
        if (i > 0) i--
        if (i2 > 0) i2--
        val zero = i == 0
        if (zero) {
            i = Random.nextInt(3, 6)
            i2 = 2
        }
        return zero
    }

    fun wasSwording(): Boolean = i2 != 0
}