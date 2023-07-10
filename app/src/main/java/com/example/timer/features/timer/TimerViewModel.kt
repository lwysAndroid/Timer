package com.example.timer.features.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timer.R
import com.example.timer.core.data.FakeRepository
import com.example.timer.core.domain.TimerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val timerUseCase: TimerUseCase,
    private val repository: FakeRepository
) : ViewModel() {

    private val _timerScreenState = MutableStateFlow<TimerViewState>(
        TimerViewState.UpdateTimeAndButtonText(
            time = "0",
            buttonTextResource = R.string.start
        )
    )

    val timerScreenState: StateFlow<TimerViewState> = _timerScreenState

    private var counter = 22
    init {
        viewModelScope.launch {
            repository.scores().collect() { currentScore ->
                _timerScreenState.value = TimerViewState.UpdateTimeAndButtonText(
                    time = currentScore.toString(),
                    buttonTextResource = R.string.stop
                )

            }
        }
    }

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
//            testMutableStateFlow()

            collectAllData()
//            collectFirstData()
//            collectNFirstData(numberOfDataToCollect = 5)
        }
    }

    private suspend fun collectAllData() {
        timerUseCase.timerFlow.collect() { currentTime ->
            _timerScreenState.value = TimerViewState.UpdateTimeAndButtonText(
                time = currentTime.toString(),
                buttonTextResource = R.string.stop
            )
        }
    }

    private suspend fun collectFirstData() {
        val firstTimeValue = timerUseCase.timerFlow.first()
        _timerScreenState.value = TimerViewState.UpdateTimeAndButtonText(
            time = firstTimeValue.toString(),
            buttonTextResource = R.string.stop
        )
    }

    private suspend fun collectNFirstData(numberOfDataToCollect: Int) {
        val firstFiveTimeValue = timerUseCase.timerFlow.take(count = 5).toList()
        _timerScreenState.value = TimerViewState.UpdateTimeAndButtonText(
            time = firstFiveTimeValue.joinToString { "$it" },
            buttonTextResource = R.string.stop
        )
    }

    private suspend fun testMutableStateFlow() {
        repository.emit(counter)
        counter += 1
    }


}

sealed class TimerViewState {
    data class UpdateTimeAndButtonText(val time: String, val buttonTextResource: Int) :
        TimerViewState()
}

