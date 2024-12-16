package com.example.deezermusicapplication.data.network.api

import com.example.deezermusicapplication.data.network.RetrofitClient
import com.example.deezermusicapplication.data.network.model.response.detail.album.AlbumDetailResponse
import com.example.deezermusicapplication.data.network.model.response.detail.track.TrackDetailResponse
import com.example.deezermusicapplication.data.network.model.response.search.album.SearchAlbumResponse
import com.example.deezermusicapplication.data.network.model.response.search.track.SearchTrackResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface DeezerApi {

    @GET("/search/track")
    suspend fun searchByTrack(@Query("q") trackName: String) : SearchTrackResponse

    @GET("/search/album")
    suspend fun searchAlbum(@Query("q") albumName: String) : SearchAlbumResponse

    @GET("/track/{track_id}")
    suspend fun getTrackDetails(@Path("track_id") trackId: String) : TrackDetailResponse

    @GET("/album/{album_id}")
    suspend fun getAlbumDetails(@Path("album_id") albumId: String) : AlbumDetailResponse

    companion object {
        val instance: DeezerApi = RetrofitClient.instance.create(DeezerApi::class.java)
    }
}