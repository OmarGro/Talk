package com.example.talk.compose.elements

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.talk.R
import com.example.talk.ui.models.ColorStyles
import com.example.talk.ui.models.ElementModel
import com.example.talk.ui.theme.Black

@Composable
fun BubbleCard(
    model: ElementModel,
    modifier: Modifier = Modifier,
    onClickCallback: ((ElementModel) -> Unit )? = null
) {
    var background = "#FFFFFF"
    var iconTint = "#000000"
    var iconId = R.drawable.ic_baseline_account
    val context = LocalContext.current

    when (model.style) {
        ColorStyles.STYLES1 -> {
            if (model.primaryColor.isNotBlank()) {
                background = model.primaryColor
            }
            iconTint = "#FFFFFF"
        }
        ColorStyles.STYLES2 -> {
            if(model.primaryColor.isNotBlank()){
                iconTint = model.primaryColor
            }
            background = "#FFFFFF"
        }
    }

    Box(modifier) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            when {
                model.url.contains("https") -> {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(model.url)
                            .transformations(CircleCropTransformation())
                            .crossfade(2000)
                            .build(),
                        contentDescription = "",
                        placeholder = painterResource(R.drawable.ic_baseline_account),
                        contentScale = ContentScale.Crop,
                    )
                }
                model.image.isNotEmpty() -> {
                    if (model.image.isNotBlank()) {
                        iconId = context.resources.getIdentifier(
                            model.image,
                            "drawable",
                            context.packageName
                        )
                        if (iconId == 0) {
                            iconId = R.drawable.ic_baseline_account
                        }
                    }
                    Image(
                        painter = painterResource(id = iconId),
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .size(48.dp)
                            .fillMaxSize()
                    )
                }
                else -> {
                    if (model.image.isNotBlank()) {
                        iconId = context.resources.getIdentifier(
                            model.image,
                            "drawable",
                            context.packageName
                        )
                        if (iconId == 0) {
                            iconId = R.drawable.ic_baseline_account
                        }
                    }
                    Box(modifier = Modifier.size(64.dp)) {
                        Card(
                            shape = RoundedCornerShape(50),
                            elevation = 4.dp,
                            backgroundColor = Color(android.graphics.Color.parseColor(background)),
                            modifier = Modifier
                                .activeBorder(model.isActive)
                                .align(Alignment.Center)
                                .clickable {
                                    onClickCallback?.invoke(model)
                                }
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .fillMaxSize()
                            ) {
                                Icon(
                                    painter = painterResource(id = iconId),
                                    contentDescription = "",
                                    tint = if (iconTint.isNotEmpty()) Color(
                                        android.graphics.Color.parseColor(
                                            iconTint
                                        )
                                    ) else Black,
                                    modifier = Modifier
                                        .size(24.dp)
                                        .align(Alignment.Center)
                                        .fillMaxSize()
                                )
                            }
                        }
                    }
                }
            }
            if (model.title.isNotEmpty()) {
                Text(
                    text = model.title,
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    color = Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .width(64.dp)
                        .height(36.dp)
                )
            }
        }
    }
}

fun Modifier.activeBorder(isActive: Boolean): Modifier {
    if (!isActive) {
        return this
            .size(48.dp)
            .shadow(4.dp, CircleShape)
    }
    return this
        .border(
            border = BorderStroke(1.dp, Color.Green),
            shape = RoundedCornerShape(50)
        )
        .size(48.dp)
        .shadow(4.dp, CircleShape)
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    val model = ElementModel(title = "My Status", isActive = true)
    BubbleCard(model = model)
}