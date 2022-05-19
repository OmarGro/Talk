package com.example.talk.ui.models

data class ElementModel(
    val id: Int = 0,
    val title: String = "",
    val visibility: Boolean = true,
    val clickEnabled: Boolean = true,
    //BubbleCard
    val style: ColorStyles = ColorStyles.STYLES1,
    val icon: String = "",
    val url: String = "",
    val image: String = "",
    val primaryColor: String = "",
    val isActive: Boolean = false,
)
