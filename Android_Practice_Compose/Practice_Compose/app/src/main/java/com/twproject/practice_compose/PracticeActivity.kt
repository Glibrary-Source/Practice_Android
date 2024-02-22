package com.twproject.practice_compose

import android.content.res.Resources
import android.graphics.BitmapShader
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twproject.practice_compose.ui.theme.Practice_ComposeTheme
import kotlinx.parcelize.Parcelize


class PracticeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Practice_ComposeTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    ChatBubble(message = Message2(AnnotatedString("body"), timestamp = "2022, 22"))
                }
            }
        }

    }
}

data class Message2(val content: AnnotatedString, val timestamp: String)

@Composable
fun ChatBubble(message: Message2) {
    var showDetails by rememberSaveable {
        mutableStateOf(true)
    }

    ClickableText(text = message.content, onClick = { showDetails = !showDetails })

    if(showDetails) {
        Text(message.timestamp)
    }

}

@Composable
fun BackgroundBanner(
    @DrawableRes avatarRes: Int,
    modifier: Modifier = Modifier,
    res: Resources = LocalContext.current.resources
) {
    val brush = remember(key1 = avatarRes) {
        ShaderBrush(
            BitmapShader(
                ImageBitmap.imageResource(res, avatarRes).asAndroidBitmap(),
                android.graphics.Shader.TileMode.REPEAT,
                android.graphics.Shader.TileMode.REPEAT
            )
        )
    }

    Box(
        modifier = modifier.background(brush)
    ) {
        // ...
    }
}



@Parcelize
data class City(val name: String, val country: String) : Parcelable

@Composable
fun CityScreen() {
    var selectedCity = rememberSaveable {
        mutableStateOf(City("Madrid", "Spain"))
    }
}

@Composable
fun HelloScreen() {
    var name by rememberSaveable { mutableStateOf("") }

    HelloContent(name = name, onNameChange = { name = it})
}

@Composable
fun HelloContent(name: String, onNameChange: (String) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Hello, $name",
            modifier = Modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.bodyMedium
        )
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Name") }
        )
    }
}

//@Composable
//fun HelloContent() {
//    Column(modifier = Modifier.padding(16.dp)) {
//        var name by remember { mutableStateOf("") }
//        if (name.isNotEmpty()) {
//            Text(
//                text = "Hello, $name!",
//                modifier = Modifier.padding(bottom = 8.dp),
//                style = MaterialTheme.typography.bodyMedium
//            )
//        }
//        OutlinedTextField(
//            value = name,
//            onValueChange = { name = it },
//            label = { Text("Name") }
//        )
//    }
//}

/**
 * Display a list of names the user can click with a header
 */
@Composable
fun NamePicker(
    header: String,
    names: List<String>,
) {
    val stateStringList = remember {
        mutableStateListOf(*names.toTypedArray())
    }

    Column {
        // this will recompose when [header] changes, but not when [names] changes
        Text(header, style = MaterialTheme.typography.bodyMedium)
        Divider()

        // LazyColumn is the Compose version of a RecyclerView.
        // The lambda passed to items() is similar to a RecyclerView.ViewHolder.
        LazyColumn(modifier = Modifier.padding(8.dp)) {
            items(stateStringList) { name ->
                // When an item's [name] updates, the adapter for that item
                // will recompose. This will not recompose when [header] changes
                NamePickerItem(name, stateStringList)
            }
        }
    }
}

/**
 * Display a single name the user can click.
 */
@Composable
private fun NamePickerItem(name: String, stateStringList: SnapshotStateList<String>) {
    Text(name, Modifier.clickable(onClick = {stateStringList.add(name)}))
}

@Composable
fun ListComposable(myList: List<String>) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(all = 8.dp)
    ) {
        Column {
            for (item in myList) {
                Text("Item: $item")
            }
        }

        Spacer(modifier = Modifier.width(4.dp))

        Text("Count: ${myList.size}")
    }
}

@Composable
fun ListWithBug(myList: List<String>) {
    var items = 0
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            for (item in myList) {
                Text("Item: $item")
                items++
            }
        }
        Text("Count: $items")
    }
}



@Composable
fun ClickCounter2(clicks: MutableIntState, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text("I've been clicked ${clicks.intValue} times")
    }
}

@Composable
fun Counter() {
    val count = remember {
        mutableIntStateOf(0)
    }

    Column {
        // 상태 값을 사용하여  UI를 구성
        Text(text = "Count: ${count.intValue}")

        // 버튼을 추가합니다
        Button(onClick = { count.intValue++ }) {
            Text(text = "Increase Count")
        }
    }
}

@Composable
fun ClickCounter(clicks: Int, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text("I've been clicked $clicks times")
    }
}

@Preview
@Composable
fun ClickCounterPreview() {
    ClickCounter(clicks = 1) {

    }
}

@Composable
fun Greeting(names: List<String>) {
    Column {
        for (name in names) {
            Text(
                "Hello $name",
                modifier = Modifier.padding(all = 4.dp),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Practice_ComposeTheme {
        Greeting(
            listOf(
                "hogu",
                "john",
                "sam"
            )
        )
    }
}