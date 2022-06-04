package com.example.talk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.talk.compose.elements.MainViewPager
import com.example.talk.ui.models.ElementModel
import com.example.talk.ui.models.MessageListItemModel
import com.example.talk.ui.models.MessageListItemState
import com.example.talk.ui.theme.TalkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val item = MessageListItemModel(
            title = "Connect Friends",
            state = MessageListItemState.PINNED,
            dateLastMessage = "2/21/20",
        )

        val chats = listOf(item)

        val status = ElementModel(
            title = "My Status",
            isActive = true
        )
        val chatStatus = listOf(status)

        setContent {
            TalkTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainViewPager(
                        chatStatus,
                        chats
                    )
                }
            }
        }
    }
}