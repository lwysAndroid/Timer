package com.example.timer.features.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timer.R
import com.example.timer.core.domain.TimerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val timerUseCase: TimerUseCase
) : ViewModel() {

    private val _timerScreenState = MutableStateFlow<TimerViewState>(
        TimerViewState.UpdateTimeAndButtonText(
            time = "0",
            buttonTextResource = R.string.start
        )
    )

    val timerScreenState: StateFlow<TimerViewState> = _timerScreenState


    fun onClickTimerButton() {
        if (timerUseCase.timerIsActive()) {
            timerUseCase.stopTimer()
            _timerScreenState.value = TimerViewState.UpdateTimeAndButtonText(
                time = (_timerScreenState.value as TimerViewState.UpdateTimeAndButtonText).time,
                buttonTextResource = R.string.start
            )
        } else {
            timerUseCase.startTimer()
            startTimerAndCollect()
        }
    }

    private fun startTimerAndCollect() {
        viewModelScope.launch {
            timerUseCase.timerFlow.collect() { currentTime ->
                _timerScreenState.value = TimerViewState.UpdateTimeAndButtonText(
                    time = currentTime.toString(),
                    buttonTextResource = R.string.stop
                )
            }
        }
    }

}

sealed class TimerViewState {
    data class UpdateTimeAndButtonText(val time: String, val buttonTextResource: Int) :
        TimerViewState()
}

