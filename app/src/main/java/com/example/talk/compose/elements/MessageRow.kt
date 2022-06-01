package com.example.talk.compose.elements

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.VolumeMute
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.talk.R
import com.example.talk.ui.models.LastMessageType
import com.example.talk.ui.models.MessageListItemModel
import com.example.talk.ui.models.MessageListItemState

@Composable
fun MessageRow(
    messageListItemModel: MessageListItemModel
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Avatar(avatarUrl = messageListItemModel.avatarUrl)
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f)
                .wrapContentHeight()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = messageListItemModel.title,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    fontSize = 18.sp,
                )
                Text(
                    text = messageListItemModel.dateLastMessage,
                    style = TextStyle(fontWeight = FontWeight.Light),
                    fontSize = 12.sp,
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                MessageIcon(type = messageListItemModel.lastMessageType)
                MessageText(
                    modifier = Modifier.weight(1f),
                    messageListItemModel = messageListItemModel
                )
                ChatIcon(state = messageListItemModel.state)
            }
        }
    }
}

@Composable
private fun Avatar(
    modifier: Modifier = Modifier,
    avatarUrl: String? = null,
    contentDescription: String? = null,
) {
    Box(
        modifier = modifier
            .size(64.dp)
            .padding(8.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(avatarUrl)
                .transformations(CircleCropTransformation())
                .crossfade(2000)
                .build(),
            contentDescription = contentDescription,
            placeholder = painterResource(R.drawable.ic_baseline_account),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun MessageIcon(
    modifier: Modifier = Modifier,
    type: LastMessageType = LastMessageType.TEXT
){
    when (type) {
        LastMessageType.IMAGE -> {
            Icon(
                imageVector = Icons.Filled.Image,
                contentDescription = "image",
                modifier = modifier.size(16.dp)
            )
        }
        LastMessageType.VIDEO -> {
            Icon(
                imageVector = Icons.Filled.Videocam,
                contentDescription = "video",
                modifier = modifier.size(16.dp)
            )
        }
        else -> {
            //No icon to show
        }
    }
}

@Composable
private fun MessageText(
    modifier: Modifier = Modifier,
    messageListItemModel: MessageListItemModel,
) {
    val lastMessage: String = messageListItemModel.lastMessage
        ?: messageListItemModel.lastMessageType.name
    Text(
        text = lastMessage,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}

@Composable
private fun ChatIcon(
    modifier: Modifier = Modifier,
    state: MessageListItemState = MessageListItemState.DEFAULT
) {
    when (state) {
        MessageListItemState.MUTED -> {
            Icon(
                imageVector = Icons.Filled.VolumeMute,
                contentDescription = "mute",
                modifier = modifier.size(16.dp)
            )
        }
        MessageListItemState.PINNED -> {
            Icon(
                imageVector = Icons.Filled.PushPin,
                contentDescription = "pinned",
                modifier = modifier.size(16.dp)
            )
        }
        else -> {
            //No icon to show
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    val messageListItemModel1 = MessageListItemModel(
        title = "Omarcito",
        lastMessage = "El PR ya está listo",
        dateLastMessage = "12:00 PM",
    )
    val messageListItemModel2 = MessageListItemModel(
        title = "Mau Hdez",
        state = MessageListItemState.PINNED,
        lastMessage = "Un mensaje de prueba",
        dateLastMessage = "Ayer",
    )
    val messageListItemModel3 = MessageListItemModel(
        title = "Connect Friends",
        state = MessageListItemState.PINNED,
        dateLastMessage = "2/21/20",
    )
    val messageListItemModel4 = MessageListItemModel(
        title = "Compa 1",
        state = MessageListItemState.MUTED,
        lastMessage = "Checa este momazo :V",
        dateLastMessage = "9:31 AM",
        lastMessageType = LastMessageType.IMAGE,
    )
    val messageListItemModel5 = MessageListItemModel(
        title = "English Class 2022",
        lastMessage = "Les comparto el vídeo de la presentación",
        dateLastMessage = "Yesterday",
        lastMessageType = LastMessageType.VIDEO,
    )
    val messageListItemModel6 = MessageListItemModel(
        title = "Pablito",
        dateLastMessage = "1/1/22",
        lastMessageType = LastMessageType.IMAGE,
    )

    Column {
        MessageRow(messageListItemModel1)
        MessageRow(messageListItemModel2)
        MessageRow(messageListItemModel3)
        MessageRow(messageListItemModel4)
        MessageRow(messageListItemModel5)
        MessageRow(messageListItemModel6)
    }
}
