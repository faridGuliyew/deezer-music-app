package com.example.deezermusicapplication.media

import android.media.MediaPlayer

object MusicPlayer {
    private val mediaPlayer = MediaPlayer()

    fun setDataSource(
        source: String
    ) {
        println("MusicPlayer - requested to play: $source")
        if (source.isEmpty()) return
        mediaPlayer.reset()
        mediaPlayer.setDataSource(source)
        mediaPlayer.prepare()
    }

    fun start() {
        mediaPlayer.start()
    }

    fun pause() {
        mediaPlayer.pause()
    }
}