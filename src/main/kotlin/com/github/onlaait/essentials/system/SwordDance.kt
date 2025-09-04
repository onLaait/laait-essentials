package com.github.onlaait.essentials.system

import kotlin.random.Random

object SwordDance {

    private var i = 0

    fun onNotSwording() {
        i = 0
    }

    fun onSwording(): Boolean {
        if (i == 0) {
            i = Random.nextInt(3, 5)
        }
        if (i > 0) i--
        return i == 0
    }

    fun wasSwording(): Boolean = i == 0
}