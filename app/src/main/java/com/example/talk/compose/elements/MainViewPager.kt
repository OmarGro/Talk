package com.example.talk.compose.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.talk.ui.models.ElementModel
import com.example.talk.ui.models.MessageListItemModel
import com.example.talk.ui.models.MessageListItemState
import com.example.talk.ui.theme.Cyan
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainViewPager(
    chatStatus: List<ElementModel>,
    chats: List<MessageListItemModel>
) {
    val tabSections = listOf(
        "CHATS" to Icons.Filled.Chat,
        "CALLS" to Icons.Filled.Call,
        "PROFILE" to Icons.Filled.Settings,
    )
    val pagerState = rememberPagerState(
        pageCount = tabSections.size,
        initialOffscreenLimit = 2,
        infiniteLoop = false,
        initialPage = 1,
    )
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TabRow(
            selectedTabIndex = tabIndex,
            indicator = { tabPositions ->
                Spacer(
                    Modifier
                        .tabIndicatorOffset(tabPositions[tabIndex])
                        .height(5.dp)
                        .background(Cyan)
                )
            }
        ) {
            tabSections.forEachIndexed { index, pair ->
                Tab(selected = tabIndex == index, onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }, text = {
                    Text(text = pair.first)
                }, icon = {
                    Icon(imageVector = pair.second, contentDescription = null)
                })
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { index ->
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                when(tabSections[index].first) {
                    "CHATS" -> {
                        LazyRow {
                            items(chatStatus){
                                BubbleCard(it)
                            }
                        }
                        LazyColumn {
                            items(chats){
                                MessageRow(it)
                            }
                        }

                    }
                else -> {
                    Text(
                        text = tabSections[index].first,
                    )
                }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainViewPagerPreview() {
    val item = MessageListItemModel(
        title = "Connect Friends",
        state = MessageListItemState.PINNED,
        dateLastMessage = "2/21/20",
    )
    MainViewPager(emptyList(), listOf(item))
}