package com.example.timer.features.timer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.timer.core.ui.PhonePreview
import com.example.timer.ui.theme.TimerTheme


@Composable
fun TimerViewContainer(
    viewModel: TimerViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    when (val timerState = viewModel.timerScreenState.collectAsState().value) {
        is TimerViewState.UpdateTimeAndButtonText -> {
            TimerView(
                time = timerState.time,
                buttonText = context.getString(timerState.buttonTextResource),
                onClickTimerButton = viewModel::onClickTimerButton
            )
        }
    }
}

@Composable
fun TimerView(
    time: String,
    buttonText: String,
    onClickTimerButton: () -> Unit
) {

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(all = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            Text(
                text = time,
                fontSize = 18.sp,
                maxLines = 2,
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onClickTimerButton) {
                Text(text = buttonText)
            }
        }

    }

}

@PhonePreview
@Composable
fun PhonePreviewPreview() {
    TimerTheme {
        TimerView(time = "36",
            buttonText = "Start",
            onClickTimerButton = {})
    }
}