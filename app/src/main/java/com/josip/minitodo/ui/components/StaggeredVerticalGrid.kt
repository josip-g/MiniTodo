package com.josip.minitodo.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import kotlin.math.ceil

@Composable
fun StaggeredVerticalGrid(
    modifier: Modifier = Modifier,
    maxColumnWidth: Dp,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->

        val columnCount = ceil(constraints.maxWidth / maxColumnWidth.toPx()).toInt().coerceAtLeast(1)
        val columnWidth = constraints.maxWidth / columnCount

        val itemConstraints = constraints.copy(
            minWidth = columnWidth,
            maxWidth = columnWidth
        )

        val columnHeights = IntArray(columnCount) { 0 }
        val placeables = measurables.map { measurable ->
            val column = columnHeights.indexOf(columnHeights.minOrNull() ?: 0)
            val placeable = measurable.measure(itemConstraints)
            columnHeights[column] += placeable.height
            Pair(column, placeable)
        }

        val height = columnHeights.maxOrNull()
            ?.coerceIn(constraints.minHeight, constraints.maxHeight)
            ?: constraints.minHeight

        layout(constraints.maxWidth, height) {
            val columnY = IntArray(columnCount) { 0 }
            placeables.forEach { (column, placeable) ->
                placeable.place(
                    x = column * columnWidth,
                    y = columnY[column]
                )
                columnY[column] += placeable.height
            }
        }
    }
}