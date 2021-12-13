package com.example.datastore

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import com.example.datastore.ui.theme.*
import java.util.prefs.Preferences

class MainActivity : ComponentActivity() {

    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setDataStore(this)
        setContent {
            DataStoreTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Settings(viewModel)
                }
            }
        }
    }
}

@Composable
fun Settings(viewModel: SettingsViewModel) {
    val cardCornerRadiusExpanded = remember { mutableStateOf(false) }
    val cardColorExpanded = remember { mutableStateOf(false) }
    val textColorExpanded = remember { mutableStateOf(false) }
    val cardCornerRadius = viewModel.getCardCornerRadius().collectAsState(cardCornerRadiusValues.first())
    val cardColor = viewModel.getCardBackgroundColor().collectAsState(cardColorValues.first())
    val textColor = viewModel.getTextColor().collectAsState(textColorValues.first())
    val backgroundColor = viewModel.getBackgroundColor().collectAsState(backgroundColorValues.first())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor.value)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = 8.dp,
            backgroundColor = cardColor.value,
            shape = RoundedCornerShape(cardCornerRadius.value)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Радиус карточки",
                    modifier = Modifier.weight(5f),
                    color = textColor.value
                )
                IconButton(
                    modifier = Modifier.weight(1f),
                    onClick = { cardCornerRadiusExpanded.value = !cardCornerRadiusExpanded.value }
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_baseline_keyboard_arrow_down_24),
                        contentDescription = ""
                    )
                }
                DropdownMenu(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(cardColor.value),
                    expanded = cardCornerRadiusExpanded.value,
                    onDismissRequest = { cardCornerRadiusExpanded.value = false }
                ) {
                    cardCornerRadiusValues.forEach {
                        DropdownMenuItem(onClick = {
                            viewModel.updateCornerRadius(it)
                            cardCornerRadiusExpanded.value = false
                        }) {
                            Text(
                                text = "${it.value.toInt()}dp",
                                color = textColor.value
                            )
                        }
                    }
                }
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = 8.dp,
            backgroundColor = cardColor.value,
            shape = RoundedCornerShape(cardCornerRadius.value)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Цвет карточки",
                    modifier = Modifier.weight(5f),
                    color = textColor.value
                )
                IconButton(
                    modifier = Modifier.weight(1f),
                    onClick = { cardColorExpanded.value = !cardColorExpanded.value }
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_baseline_keyboard_arrow_down_24),
                        contentDescription = ""
                    )
                }
                DropdownMenu(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(cardColor.value),
                    expanded = cardColorExpanded.value,
                    onDismissRequest = { cardColorExpanded.value = false }
                ) {
                    cardColorValues.forEachIndexed { index, color ->
                        DropdownMenuItem(onClick = {
                            viewModel.updateCardBackground(index)
                            cardColorExpanded.value = false
                        }) {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Image(
                                    ColorPainter(color), contentDescription = "", modifier = Modifier
                                        .height(32.dp)
                                        .fillMaxWidth()
                                        .border(width = 0.25.dp, color = Color.Black)
                                )
                            }
                        }
                    }
                }
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = 8.dp,
            backgroundColor = cardColor.value,
            shape = RoundedCornerShape(cardCornerRadius.value)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Цвет текста",
                    modifier = Modifier.weight(5f),
                    color = textColor.value
                )
                IconButton(
                    modifier = Modifier.weight(1f),
                    onClick = { textColorExpanded.value = !textColorExpanded.value }
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_baseline_keyboard_arrow_down_24),
                        contentDescription = ""
                    )
                }
                DropdownMenu(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(cardColor.value),
                    expanded = textColorExpanded.value,
                    onDismissRequest = { textColorExpanded.value = false }
                ) {
                    textColorValues.forEachIndexed { index, color ->
                        DropdownMenuItem(onClick = {
                            viewModel.updateTextColor(index)
                            textColorExpanded.value = false
                        }) {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Image(
                                    ColorPainter(color), contentDescription = "", modifier = Modifier
                                        .height(32.dp)
                                        .fillMaxWidth()
                                        .border(width = 0.25.dp, color = Color.Black)
                                )
                            }
                        }
                    }
                }
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = 8.dp,
            backgroundColor = cardColor.value,
            shape = RoundedCornerShape(cardCornerRadius.value)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Цвет фона", color = textColor.value)
                for (index in backgroundColorValues.indices step 2) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 24.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ColorCard(color = backgroundColorValues[index]) {
                            viewModel.updateBackgroundColor(index)
                        }
                        ColorCard(color = backgroundColorValues[index + 1]) {
                            viewModel.updateBackgroundColor(index + 1)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ColorCard(color: Color, onClick: () -> Unit) {
    IconButton(
        onClick = { onClick() },
        modifier = Modifier
            .background(color)
            .size(128.dp, 48.dp)
    ) {}
}

val cardCornerRadiusValues =
    listOf(8.dp, 12.dp, 16.dp, 20.dp, 24.dp)

val cardColorValues =
    listOf(Greyso, WaterBear, NeverDoubt, NeverDreamed)

val textColorValues =
    listOf(Color.White, WaterBearText, NeverDoubtText, NeverDreamedText)

val backgroundColorValues =
    listOf(GreysoBg, WaterBearBg, NeverDoubtBg, NeverDreamedBg)
