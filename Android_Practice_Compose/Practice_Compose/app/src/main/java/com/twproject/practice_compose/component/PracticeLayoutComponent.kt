@file:OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)

package com.twproject.practice_compose.component

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.twproject.practice_compose.R

object PracticeLayoutComponent {

    data class Artist(val image: ImageBitmap, val name: String, val lastSeenOnline: String)

    @Composable
    fun MainApp(resource: Resources) {
        val bitmap = BitmapFactory.decodeResource(resource, R.drawable.leonardo_dicaprio_2010).asImageBitmap()
        val artist = Artist(bitmap, "leo", "3 ago")
        val backBitMap = BitmapFactory.decodeResource(resource, R.drawable.refrigerator).asImageBitmap()
//        ArtistCardArrangement(artist = artist)
//        ArtistCardModifiers(artist = artist, backImage = backBitMap) {
//            Log.d("TestModifier", "yesMD")
//        }
//        WithConstraintsComposable()
//        HomeScreen()
//        ImageMap()
//        HorizontalPager()
        TestFlow()
    }

    @Composable
    fun ArtistCardColumn() {
        Column {
            Text("Alfred Sisley")
            Text("3 minutes ago")
        }
    }

    @Composable
    fun ArtistCardRow(artist: Artist) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(bitmap = artist.image, contentDescription = artist.name)
            Column {
                Text(artist.name)
                Text(artist.lastSeenOnline)
            }
        }
    }


    @Composable
    fun ArtistAvatar(artist: Artist) {
        Box {
            Image(bitmap = artist.image, contentDescription = "Artist image")
            Icon(Icons.Filled.Check, contentDescription = "Check mark")
        }
    }

    @Composable
    fun Profile(
        resource: Resources
    ) {
        val bitmap = BitmapFactory.decodeResource(resource, R.drawable.leonardo_dicaprio_2010).asImageBitmap()
        val artist = Artist(bitmap, "name", "date")

        Box(
            modifier =
            Modifier
                .padding(4.dp)
                .size(width = 48.dp, height = 48.dp)
            ,
        ) {
            Image(bitmap = artist.image, contentDescription = "Artist image")
            Icon(
                Icons.Filled.Check,
                contentDescription = "Check mark",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(24.dp)
            )
        }

    }

    @Composable
    fun ArtistCardArrangement(artist: Artist) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                bitmap = artist.image,
                contentDescription = "Artist image",
                modifier = Modifier.size(width = 52.dp, height = 52.dp)
            )
            Column { /*...*/ }
        }
    }

    @Composable
    fun ArtistCardModifiers(
        artist: Artist,
        backImage: ImageBitmap,
        onClick: () -> Unit
    ) {
        val padding = 8.dp
        Column(
            Modifier
                .clickable(onClick = onClick)
                .padding(padding)
                .fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    bitmap = artist.image, contentDescription = artist.name, Modifier.size(width = 64.dp, height = 64.dp))
                Column {
                    Text(text = artist.name)
                    Text(text = artist.lastSeenOnline)
                }
            }
            Spacer(Modifier.size(padding))
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                border = BorderStroke(width = 1.dp, color = Color(R.color.black))
            ) {
                Image(bitmap = backImage, contentDescription = artist.name)
            }
        }
    }

    @Composable
    fun WithConstraintsComposable() {
        BoxWithConstraints {
            Text("My minHeight is $minHeight while my maxWidth is $maxWidth")
        }
    }

    @Composable
    fun HomeScreen(/*...*/) {
        ModalNavigationDrawer(drawerContent = { /* ... */ }) {
            Scaffold(
                topBar = { /*...*/ }
            ) { contentPadding ->
                Text(
                    text = "Padding Test",
                    modifier = Modifier.padding(contentPadding)
                )
            }
        }
    }

//    @Composable
//    fun ImageMap() {
//        Row {
//            Image(
//                painterResource(id = R.drawable.refrigerator),
//                contentDescription = null,
//                //modifier 수정자도 계층이 있음 옵션 순서에따라 적용이 다름
//                modifier = Modifier.myBackground(colorResource(id = R.color.onPrimary))
//            )
//            Column(modifier = Modifier.padding(8.dp)) {
//                Text(text = "testText")
//            }
//        }
//    }

    // 수정자(Modifier) 커스텀 사용 예시
//    fun Modifier.clip(shape: Shape) = graphicsLayer(shape = shape, clip = true)
//    fun Modifier.myBackground(color: Color) = padding(16.dp)
//        .clip(RoundedCornerShape(8.dp))
//        .background(color)
//    fun Modifier.fade(enable: Boolean): Modifier {
//        val alpha by animateFloatAsState(if (enable) 0.5f else 1.0f, label = "test")
//        return this then Modifier.graphicsLayer { this.alpha = alpha }
//    }
//
//    @Composable
//    fun Modifier.fadedBackground(): Modifier {
//        val color = LocalContentColor.current
//        return this then Modifier.background(color.copy(alpha = 0.5f))
//    }
// 주석 테스트 추가
    @Composable
    fun HorizontalPager() {
        val itemList = (0..9).toList()
        LazyRow {
            items(itemList) {
                Text(
                    text = "Item is $it",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(10.dp)
                )
            }
        }
    }

    @Composable
    fun TestFlow() {
        val rows = 3
        val columns = 3
        FlowRow(
            modifier = Modifier.padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            maxItemsInEachRow = rows
        ) {
            val itemModifier = Modifier
                .padding(4.dp)
                .height(80.dp)
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.onBackground)
            repeat(rows * columns) {
                Spacer(modifier = itemModifier)
            }
        }
    }


}
























































