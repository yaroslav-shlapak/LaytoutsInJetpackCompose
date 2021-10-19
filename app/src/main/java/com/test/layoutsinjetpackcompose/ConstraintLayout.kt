package com.test.layoutsinjetpackcompose

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.test.layoutsinjetpackcompose.ui.theme.LayoutsInJetpackComposeTheme


@Composable
fun ConstraintLayoutContent() {
    ConstraintLayout {
        val (button1, button2, text) = createRefs()

        Button(
            onClick = { },
            modifier = Modifier.constrainAs(button1) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        )
        {
            Text("Button 1")
        }

        Text(
            text = "Text lorem",
            modifier = Modifier.constrainAs(text) {
                top.linkTo(button1.bottom, margin = 16.dp)
                centerHorizontallyTo(parent)
            }
        )
        
        val barrier = createEndBarrier(button1, text)
        Button(
            onClick = { },
            modifier = Modifier.constrainAs(button2) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(barrier)
            }
        ) {
            Text(text = "Button 2")
        }
    }
}

@Composable
fun DecoupledConstraintLayout() {
    BoxWithConstraints {
        val constraints = if (maxWidth < maxHeight) {
            decoupledConstraints(margin = 16.dp) // Portrait constraints
        } else {
            decoupledConstraints(margin = 32.dp) // Landscape constraints
        }

        ConstraintLayout(constraints) {
            Button(
                onClick = { /* Do something */ },
                modifier = Modifier.layoutId(BUTTON_NAME)
            ) {
                Text("Button")
            }

            Text("Text", Modifier.layoutId(TEXT_NAME))
        }
    }
}

private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val button = createRefFor(BUTTON_NAME)
        val text = createRefFor(TEXT_NAME)

        constrain(button) {
            top.linkTo(parent.top, margin= margin)
        }
        constrain(text) {
            top.linkTo(button.bottom, margin)
        }
    }
}

private val BUTTON_NAME = "button"
private val TEXT_NAME = "text"


@Preview
@Composable
fun ConstraintLayoutContentPreview() {
    LayoutsInJetpackComposeTheme {
        ConstraintLayoutContent()
    }
}

@Preview
@Composable
fun DecoupledConstraintLayoutPreview() {
    LayoutsInJetpackComposeTheme {
        DecoupledConstraintLayout()
    }
}
