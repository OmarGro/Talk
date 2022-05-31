package com.example.talk.ui.models

data class MessageListItemModel(
    val avatarUrl: String? = null,
    val title: String,
    val lastMessage: String? = null,
    val dateLastMessage: String = "",
    val state: MessageListItemState = MessageListItemState.DEFAULT,
    val lastMessageType: LastMessageType = LastMessageType.TEXT
)

enum class MessageListItemState {
    DEFAULT,
    MUTED,
    PINNED,
}

enum class LastMessageType {
    TEXT,
    IMAGE,
    VIDEO,
}
