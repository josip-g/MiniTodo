package com.josip.minitodo.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.josip.minitodo.R

@Composable
fun MainHeader(
    onLanguageClick: () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .statusBarsPadding()
            .padding(0.dp)
    ) {
        val logoSize = maxWidth * 0.15f
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(R.string.app_name),
            modifier = Modifier
                .width(logoSize)
                .aspectRatio(1f)
                .align(Alignment.CenterStart)
        )

        IconButton(
            onClick = onLanguageClick,
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Icon(
                Icons.Default.Language,
                contentDescription = stringResource(R.string.choose_language),
                tint = Color.White
            )
        }
    }
}