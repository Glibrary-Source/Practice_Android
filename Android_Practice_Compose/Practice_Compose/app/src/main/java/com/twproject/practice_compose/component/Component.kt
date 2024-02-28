@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.twproject.practice_compose.component

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.media.Image
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.twproject.practice_compose.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import java.sql.Time
import java.time.LocalDateTime

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
        var text by remember { mutableStateOf("zzz") }

        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it }
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
            if (isVisible) {
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
        fun load(url: String): Image? {
            return null
        }
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


    @Composable
    fun StepMainView() {
        val paddingState = remember { mutableStateOf(8.dp) }

        Column {
            Button(onClick = {
                paddingState.value = 20.dp
            }) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Padding Change"
                )
            }
            Text(
                text = "Hello",
                modifier = Modifier.padding(paddingState.value)
            )
        }
    }


    @Composable
    private fun OptimizeStateReadsBefore() {
        // [START android_compose_phases_optimize_state_reads_before]
        Box {
            val listState = rememberLazyListState()

            Image(
                // [START_EXCLUDE]
                painterResource(id = android.R.drawable.star_on),
                contentDescription = null,
                // [END_EXCLUDE]
                // Non-optimal implementation!
                Modifier.offset(
                    with(LocalDensity.current) {
                        // State read of firstVisibleItemScrollOffset in composition
                        (listState.firstVisibleItemScrollOffset / 2).toDp()
                    }
                )
            )

            LazyColumn(state = listState) {
                // [START_EXCLUDE]
                // [END_EXCLUDE]
            }
        }
        // [END android_compose_phases_optimize_state_reads_before]
    }

    @Composable
    fun StudyScreen() {
        val scroll = rememberScrollState()
        Title { scroll.value }
        Body(scroll)
    }

    @Composable
    fun Title(value: () -> Int) {
        Text(modifier = Modifier.height(value().dp), text = "title")
        Log.d("testCompose", "리컴포즈")
    }

    @Composable
    fun Body(scroll: ScrollState) {
        Column(
            modifier = Modifier.verticalScroll(scroll)
        ) {
            for (i in 1..20) {
                Text(modifier = Modifier.height(50.dp), text = "test1")
            }
        }
    }

    @Composable
    fun HelloScreen() {
        var name by rememberSaveable { mutableStateOf("") }

        HelloContent(name, onNameChange = { name = it })
    }

    @Composable
    fun HelloContent(name: String, onNameChange: (String) -> Unit) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Hello! $name",
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

//    @Parcelize
//    data class City(val name: String, val country: String) : Parcelable
//
//    @Composable
//    fun CityScreen() {
//        var selectedCity = rememberSaveable {
//            mutableStateOf(City("Madrid", "Spain"))
//        }
//
//        Column(modifier = Modifier.padding(16.dp)) {
//            Button(onClick = {
//                selectedCity.value = City("Madrid", "Korea")
//            }) {
//                Text(text = "Test Parcelize")
//            }
//            Text(text = "Test Parcelize ${selectedCity.value.country}")
//        }
//    }

    @Composable
    fun ChatBubble(
        message: String
    ) {
        var showDetails by rememberSaveable {
            mutableStateOf(false)
        }

        Column(modifier = Modifier.padding(16.dp)) {
            ClickableText(
                text = AnnotatedString("message"),
                onClick = { showDetails = !showDetails })

            if (showDetails) {
                Text("time stamp")
            }
        }
    }

    @SuppressLint("NewApi")
    val messages = listOf(
        Message("User", "body", LocalDateTime.now().toString()),
        Message("User2", "body", LocalDateTime.now().toString()),
        Message("User3", "body", LocalDateTime.now().toString()),
        Message("User4", "body", LocalDateTime.now().toString()),
        Message("User5", "body", LocalDateTime.now().toString()),
        Message("User6", "body", LocalDateTime.now().toString()),
        Message("User7", "body", LocalDateTime.now().toString()),
        Message("User8", "body", LocalDateTime.now().toString()),
        Message("User9", "body", LocalDateTime.now().toString()),
        Message("User10", "body", LocalDateTime.now().toString()),
        Message("User11", "body", LocalDateTime.now().toString()),
        Message("User12", "body", LocalDateTime.now().toString()),
        Message("User13", "body", LocalDateTime.now().toString()),
        Message("User14", "body", LocalDateTime.now().toString()),
        Message("User15", "body", LocalDateTime.now().toString()),
        Message("User16", "body", LocalDateTime.now().toString()),
        Message("User17", "body", LocalDateTime.now().toString()),
        Message("User18", "body", LocalDateTime.now().toString()),
        Message("User19", "body", LocalDateTime.now().toString()),
        Message("User20", "body", LocalDateTime.now().toString()),
        Message("User21", "body", LocalDateTime.now().toString()),
        Message("User22", "body", LocalDateTime.now().toString()),
        Message("User23", "body", LocalDateTime.now().toString()),
        Message("User24", "body", LocalDateTime.now().toString()),
        Message("User25", "body", LocalDateTime.now().toString()),
        Message("User26", "body", LocalDateTime.now().toString()),
        Message("User27", "body", LocalDateTime.now().toString()),
        Message("User28", "body", LocalDateTime.now().toString()),
        Message("User29", "body", LocalDateTime.now().toString()),
        Message("User30", "body", LocalDateTime.now().toString()),
        Message("User31", "body", LocalDateTime.now().toString()),
        Message("User32", "body", LocalDateTime.now().toString()),
        Message("User33", "body", LocalDateTime.now().toString()),
        Message("User34", "body", LocalDateTime.now().toString()),
    )

    @Composable
    fun ConversationScreen() {
        val scope = rememberCoroutineScope()

        val lazyListState = rememberLazyListState() // State hoisted to the ConversationScreen

        Row {
            MessagesList(messages, lazyListState) // Reuse same state in MessageList

            UserInput(
                onMessageSent = { // Apply UI logic to lazyListState
                    scope.launch {
                        lazyListState.scrollToItem(messages.size)
                    }
                }
            )
        }
    }

    data class Message(val id:String, val body: String, val dateTime: String)

    @Composable
    private fun MessagesList(
        messages: List<Message>,
        lazyListState: LazyListState = rememberLazyListState() // LazyListState has a default value
    ) {
        LazyColumn(
            state = lazyListState // Pass hoisted state to LazyColumn
        ) {
            items(messages, key = { message -> message.id }) {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = "That ${it.id} "
                )
            }
        }

        val scope = rememberCoroutineScope()

        JumpToBottom(onClicked = {
            scope.launch {
                lazyListState.scrollToItem(0) // UI logic being applied to lazyListState
            }
        })

    }

    @Composable
    fun UserInput(onMessageSent: () -> Unit) {
        Button(
            onClick =
            onMessageSent
        ) {
            Text(text = "Send Message")
        }
    }

    @Composable
    fun JumpToBottom(onClicked: () -> Unit) {
        Button(onClick = onClicked) {
            Text(text = "Jump Message")
        }
    }

    @Composable
    fun MyAppTopAppBar(topAppBarText: String, onBackPressed: () -> Unit) {
        TopAppBar(
            title = {
                Text(
                    text = topAppBarText,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                )
            },
            navigationIcon = {
                IconButton(onClick =  onBackPressed) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "BackButton"
                    )
                }
            }
        )
    }

    @Composable
    fun FruitText(fruitSize: Int) {
        // Get `resources` from the current value of LocalContext
        val resources = LocalContext.current.resources
        val fruitText = remember(resources, fruitSize) {
            resources.getString(R.string.app_name)
        }
        Text(
            text = fruitText
        )
    }




}

















































































































































































































































