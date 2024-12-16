package com.example.deezermusicapplication.data.network.model.response.detail.album


import com.google.gson.annotations.SerializedName

data class AlbumDetailResponse(
    @SerializedName("artist")
    val artist: Artist,
    @SerializedName("available")
    val available: Boolean,
    @SerializedName("contributors")
    val contributors: List<Contributor>,
    @SerializedName("cover")
    val cover: String,
    @SerializedName("cover_big")
    val coverBig: String,
    @SerializedName("cover_medium")
    val coverMedium: String,
    @SerializedName("cover_small")
    val coverSmall: String,
    @SerializedName("cover_xl")
    val coverXl: String,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("explicit_content_cover")
    val explicitContentCover: Int,
    @SerializedName("explicit_content_lyrics")
    val explicitContentLyrics: Int,
    @SerializedName("explicit_lyrics")
    val explicitLyrics: Boolean,
    @SerializedName("fans")
    val fans: Int,
    @SerializedName("genre_id")
    val genreId: Int,
    @SerializedName("genres")
    val genres: Genres,
    @SerializedName("id")
    val id: String,
    @SerializedName("label")
    val label: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("md5_image")
    val md5Image: String,
    @SerializedName("nb_tracks")
    val nbTracks: Int,
    @SerializedName("record_type")
    val recordType: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("share")
    val share: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("tracklist")
    val tracklist: String,
    @SerializedName("tracks")
    val tracks: Tracks,
    @SerializedName("type")
    val type: String,
    @SerializedName("upc")
    val upc: String
)