# Timer
This a simple project of one screen where the user can start and stop a timer with a button. The purpose of the project is to have a better understanding of the "Flow", so the timer is using a flow to emit the data

<img src="https://github.com/lwysAndroid/Timer/assets/30638469/0d6985cb-553a-4d12-aa2a-bc57d1d32fa2" width=25% height=25%>
<img src="https://github.com/lwysAndroid/Timer/assets/30638469/7af1c57f-8448-4b39-9a48-8dd7498ae5c8" width=25% height=25%>

    private var timerIsActive = false
    private var currentTime = 0
    
    val timerFlow: Flow<Int> = flow {
        while (timerIsActive) {
            emit(currentTime++)
            delay(1000)
        }
    }
