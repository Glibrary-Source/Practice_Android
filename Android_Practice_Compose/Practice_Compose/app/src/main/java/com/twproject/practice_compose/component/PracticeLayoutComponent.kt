package com.twproject.practice_compose.component

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
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
        HomeScreen()
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

    @Composable
    fun ImageMap() {
        Image(
            R.drawable.refrigerator,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
                .size(50.dp)
        )
    }

    Image(
    painterResource(R.drawable.hero),
    contentDescription = null,
    Modifier
    .clip(CircleShape)
    .padding(10.dp)
    .size(100.dp)
    )
    Image(
    painterResource(R.drawable.hero),
    contentDescription = null,
    Modifier
    .clip(CircleShape)
    .padding(10.dp)
    .size(100.dp)
    )
    Image(
    painterResource(R.drawable.hero),
    contentDescription = null,
    Modifier
    .clip(CircleShape)
    .padding(10.dp)
    .size(100.dp)
    )
    Image(
    painterResource(R.drawable.hero),
    contentDescription = null,
    Modifier
    .clip(CircleShape)
    .padding(10.dp)
    .size(100.dp)
    )




}
























































