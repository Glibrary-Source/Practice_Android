package com.twproject.practice_mediastore

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri

class AudioPlayer(private val context:Context) {

    private var mediaPlayer: MediaPlayer? = null

    fun playAudio(uri: Uri) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            setDataSource(context, uri)
            prepare()
            start()
        }
    }

    fun stopAudio() {
        mediaPlayer?.apply {
            stop()
            release()
        }
    }
}