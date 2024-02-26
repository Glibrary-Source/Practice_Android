package com.twproject.practice_compose.component

import android.annotation.SuppressLint
import android.content.Context
import android.media.Image
import android.os.Message
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object Component {

//    @Composable
//    fun MyScreen() {
//        val header = remember { mutableStateOf("Title") }
//        val names = remember { mutableStateListOf("John", "Tayeon", "IU") }
//
//        NamePicker(
//            header = header.value,
//            names = names,
//            onNameClicked = {
//                names.add(it)
//            }
//        )
//    }

    @Composable
    fun GreetingList(names: List<String>) {
        Column(modifier = Modifier.padding(4.dp)) {
            for (name in names) {
                Text(
                    "Hello $name",
                    modifier = Modifier.padding(bottom = 2.dp)
                )
            }
        }
    }

    @Composable
    fun ClickCounter(clicks: Int, onClick: () -> Unit) {
        Button(onClick = onClick) {
            Text("I've been clicked $clicks times")
        }
    }

    @Composable
    fun SharedPrefsToggle(
        text: String,
        value: Boolean,
        onValueChanged: (Boolean) -> Unit
    ) {
        Row {
            Text(text)
            Checkbox(checked = value, onCheckedChange = onValueChanged)
        }
    }

    @Composable
    fun InVisibleViewText(isChecked: Boolean) {
        if (isChecked) {
            Text(text = "Checked")
        }
    }

    @Composable
    fun ListComposable(myList: List<String>) {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                for (item in myList) {
                    Text("Item: $item")
                }
            }

            Text("Count: ${myList.size}")
        }
    }

    // 잘못된 코드
    @Composable
    @Deprecated("Example with bug")
    fun ListWithBug(myList: List<String>) {
        var items = 0

        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                for (item in myList) {
                    Text("Item: $item")
                    items++ // Avoid! Side-effect of the column recomposing.
                }
            }
            Text("Count: $items")
        }
    }

    /**
     * Display a list of names the user can click with a header
     */
    @Composable
    fun NamePicker(
        header: String,
        names: List<String>,
        onNameClicked: (String) -> Unit
    ) {
        Column {
            // this will recompose when [header] changes, but not when [names] changes
            Text(header, style = MaterialTheme.typography.bodyLarge)
            Divider()

            // LazyColumn is the Compose version of a RecyclerView.
            // The lambda passed to items() is similar to a RecyclerView.ViewHolder.
            LazyColumn {
                items(names) { name ->
                    // When an item's [name] updates, the adapter for that item
                    // will recompose. This will not recompose when [header] changes
                    NamePickerItem(name, onNameClicked)
                }
            }
        }
    }

    /**
     * Display a single name the user can click.
     */
    @Composable
    private fun NamePickerItem(name: String, onClicked: (String) -> Unit) {
        Text(
            name,
            Modifier
                .clickable(onClick = { onClicked(name) })
                .padding(start = 16.dp, end = 16.dp, bottom = 4.dp),
        )
    }

    // LaunchedEffect 예시코드
    @Composable
    fun MyScreen(
        state: UiState<List<Movie>>,
        snackbarHostState: SnackbarHostState
    ) {

        // If the UI state contains an error, show snackbar
        if (state.hasError) {
            // 'LaunchedEffect' will cancel and re-launch if
            // 'scaffoldState.snackbarHostState' changes
            LaunchedEffect(snackbarHostState) {
                // Show snackbar using a coroutine, when the coroutine is cancelled the
                // snackbar will automatically dismiss. This coroutine will cancel whenever
                // `state.hasError` is false, and only start when `state.hasError` is true
                // (due to the above if-check), or if `scaffoldState.snackbarHostState` changes.
                snackbarHostState.showSnackbar(
                    message = "Error message",
                    actionLabel = "Retry message"
                )
            }
        }

        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }
        ) { contentPadding ->
            //...
            Box(Modifier.padding(contentPadding))
        }
    }

    class Movie {
        val url = ""
        val id = ""
    }

    class UiState<T> {
        val hasError = true
    }

    // rememberCoroutineScope예시코드
    @Composable
    fun MoviesScreen(snackbarHostState: SnackbarHostState) {

        // Creates a CoroutineScope bound to the MoviesScreen's lifecycle
        val scope = rememberCoroutineScope()

        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }
        ) { contentPadding ->
            Column(Modifier.padding(contentPadding)) {
                Button(
                    onClick = {
                        // Create a new coroutine in the event handler to show a snackbar
                        scope.launch {
                            snackbarHostState.showSnackbar("Something happened!")
                        }
                    }
                ) {
                    Text("Press me")
                }
            }
        }
    }


    @Composable
    fun Kotlinkorldscreen() {
        val snackbarHostState = remember { SnackbarHostState() }
        var text by rememberSaveable { mutableStateOf("") } // 1. text변수

        LaunchedEffect(text) {
            // 2. Text 변수를 LaunchedEffect의 key로 설정
            // 3. 이 블록은 text가 바뀔 때마다 취소되고 재수행됨
            snackbarHostState.showSnackbar(
                message = "Current Text is $text"
            )
        }

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            content = { padding ->
                Column(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = text,
                        onValueChange = { text = it }
                    )
                }
            }
        )
    }

    private const val SplashWaitTimeMillis = 1000L
    @Composable
    fun LandingScreen(onTimeout: () -> Unit) {

        // This will always refer to the latest onTimeout function that
        // LandingScreen was recomposed with
        val currentOnTimeout by rememberUpdatedState(onTimeout)

        // Create an effect that matches the lifecycle of LandingScreen.
        // If LandingScreen recomposes, the delay shouldn't start again.
        LaunchedEffect(true) {
            delay(SplashWaitTimeMillis)
            currentOnTimeout()
        }

        /* Landing screen content */
    }

    /***
     * rememberUpdatedState를 사용하지 않았을때 mutableStateOf 값을 하양 Composable에 사용하고 싶을때
     *
     * @Composable
     *     fun TextFieldExample() {
     *         val text = remember { mutableStateOf("")}
     *
     *         Column(modifier = Modifier.padding(16.dp)) {
     *             OutlinedTextField(
     *                 value = text.value,
     *                 onValueChange = { text.value = it}
     *             )
     *             TextData(input = text.value)
     *         }
     *     }
     *
     *     @Composable
     *     private fun TextData(input: String) {
     *         val rememberedInput by remember { mutableStateOf(input) }.apply {
     *             value = input
     *         }
     *         Text("rememberInput: $rememberedInput")
     *     }
     *
     */

    // rememberUpdatedState를 사용했을때
    @Composable
    fun TextFieldExample() {
        var text by remember { mutableStateOf("zzz")}

        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it}
            )
            TextData(input = text)
        }
    }

    @Composable
    private fun TextData(input: String) {
        val rememberedInput by remember { mutableStateOf(input) }
        val rememberedUpdatedInput by rememberUpdatedState(input)
        Text(" rememberedInput: $rememberedInput")
        Text(" rememberedUpdatedInput: $rememberedUpdatedInput")
    }
    //


    // DisposableEffect 를 사용 했을때
    @Composable
    fun HomeScreen(
        lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
        onStart: () -> Unit, // Send the 'started' analytics event
        onStop: () -> Unit // Send the 'stopped' analytics event
    ) {
        // Safely update the current lambdas when a new one is provided
        val currentOnStart by rememberUpdatedState(onStart)
        val currentOnStop by rememberUpdatedState(onStop)

        // If `lifecycleOwner` changes, dispose and reset the effect
        DisposableEffect(lifecycleOwner) {
            // Create an observer that triggers our remembered callbacks
            // for sending analytics events
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_START) {
                    currentOnStart()
                } else if (event == Lifecycle.Event.ON_STOP) {
                    currentOnStop()
                }
            }

            // Add the observer to the lifecycle
            lifecycleOwner.lifecycle.addObserver(observer)

            // When the effect leaves the Composition, remove the observer
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }

        /* Home screen content */
    }


    //SideEffect 예시 코드 -> 왠만하면 사용 x
    @Composable
    fun HomeScreen() {
        var isVisible by remember { mutableStateOf(false) }
        val focusRequester = remember { FocusRequester() }

        Column(modifier = Modifier.fillMaxSize()) {
            Button(onClick = { isVisible = true }) {
                Text(text = "버튼 클릭")
            }
            if(isVisible) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxSize()
                        .focusRequester(focusRequester),
                    value = "", onValueChange = {})
            }
        }

        SideEffect {
            if (isVisible) {
                focusRequester.requestFocus()
            }
        }
    }

    // produceState 예시 코드
    @Composable
    fun loadNetworkImage(
        url: String,
        imageRepository: ImageRepository = ImageRepository()
    ): State<Result<Image>> {

        // Creates a State<T> with Result.Loading as initial value
        // If either `url` or `imageRepository` changes, the running producer
        // will cancel and will be re-launched with the new inputs.
        return produceState<Result<Image>>(initialValue = Result.Loading, url, imageRepository) {

            // In a coroutine, can make suspend calls
            val image = imageRepository.load(url)

            // Update State with either an Error or Success result.
            // This will trigger a recomposition where this State is read
            value = if (image == null) {
                Result.Error
            } else {
                Result.Success(image)
            }
        }
    }

    // [START_EXCLUDE silent]
    class ImageRepository {
        fun load(url: String): Image? { return null }
    }
    sealed class Result<out T> {
        object Loading : Result<Nothing>()
        object Error : Result<Nothing>()
        class Success<T>(t: T?) : Result<T>()
    }
    // [END_EXCLUDE]

    // derivedStateOf... 예시코드
    @Composable
    // When the messages parameter changes, the MessageList
    // composable recomposes. derivedStateOf does not
    // affect this recomposition.
    fun MessageList(messages: List<Message>) {
        Box {
            val listState = rememberLazyListState()

            LazyColumn(state = listState) {
                // ...
            }

            // Show the button if the first visible item is past
            // the first item. We use a remembered derived state to
            // minimize unnecessary compositions
            val showButton by remember {
                derivedStateOf {
                    listState.firstVisibleItemIndex > 0
                }
            }

            AnimatedVisibility(visible = showButton) {
                ScrollToTopButton()
            }
        }
    }

    @Composable
    fun ScrollToTopButton() {
        // Button to scroll to the top of list.
    }
    // derivedStateOf...



    



}

















































































































































































































































