package com.example.icare.domain

object Calculations {
    /** Simple estimate: ~0.2 * weightKg per round */
    fun suryaCalories(rounds: Int, weightKg: Int): Int =
        (rounds * weightKg * 0.2).toInt()
}
