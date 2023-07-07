package com.example.timer.core.domain

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TimerUseCase @Inject constructor() {

    private var timerIsActive = false
    private var currentTime = 0

    val timerFlow: Flow<Int> = flow {
        while (timerIsActive) {
            emit(currentTime++)
            delay(1000)
        }
    }

    fun startTimer() {
        timerIsActive = true
    }

    fun stopTimer() {
        timerIsActive = false
    }

    fun timerIsActive(): Boolean {
        return timerIsActive
    }
}