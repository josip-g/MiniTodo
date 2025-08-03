package com.josip.minitodo.ui.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import com.josip.minitodo.common.Constants.NAV_ANIMATION_DURATION

object NavigationTransitions {
    val enter = slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(NAV_ANIMATION_DURATION)) + fadeIn()
    val exit = slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(NAV_ANIMATION_DURATION)) + fadeOut()
    val popEnter = slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(NAV_ANIMATION_DURATION)) + fadeIn()
    val popExit = slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(NAV_ANIMATION_DURATION)) + fadeOut()
}