package com.twproject.practice_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import com.twproject.practice_compose.component.Component
import com.twproject.practice_compose.ui.theme.Practice_ComposeTheme


class PracticeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Practice_ComposeTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
//                    Component.MyScreen(
//                        Component.UiState(),
//                        SnackbarHostState()
//                    )
//                    Component.Kotlinkorldscreen()
//                    Component.TextFieldExample()
//                    Component.HomeScreen()
//                    Component.StepMainView()
//                    Component.StudyScreen()
//                    Component.HelloContent()
//                    Component.CityScreen()
                    Component.ConversationScreen()
                }
            }
        }

    }
}



