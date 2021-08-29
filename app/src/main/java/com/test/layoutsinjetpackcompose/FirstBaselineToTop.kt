package com.test.layoutsinjetpackcompose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp

@Composable
fun Modifier.firstBaselineToTop(
    firstBaselineToTop: Dp
): Modifier = this.then(
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)

        // check the composable has a first baseline
        check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
        val firstBaseline = placeable[FirstBaseline]

        // Height of composable with padding - first baseline
        val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
        val height = placeable.height + placeableY
        layout(width = placeable.width, height = height) {
            // Where the composable gets placed
            placeable.placeRelative(0, placeableY)
        }
    }
)
