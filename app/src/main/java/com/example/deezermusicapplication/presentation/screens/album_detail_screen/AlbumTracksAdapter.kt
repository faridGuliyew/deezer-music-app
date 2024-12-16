package com.example.deezermusicapplication.presentation.screens.album_detail_screen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.Coil
import coil.load
import com.example.deezermusicapplication.R
import com.example.deezermusicapplication.data_models.SearchResultItem

class AlbumTracksAdapter(
    private val tracks: List<SearchResultItem>,
    private val onClick: (SearchResultItem) -> Unit,
    private val onLongClick: (SearchResultItem) -> Unit
) : RecyclerView.Adapter<AlbumTracksAdapter.TrackViewHolder>() {

    inner class TrackViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val photo: ImageView = view.findViewById(R.id.photo)
        private val songName: TextView = view.findViewById(R.id.song_name)
        private val artist: TextView = view.findViewById(R.id.artist)

        fun bind(track: SearchResultItem) {
            // Libraries lik Glide or Picasso could also be used
            photo.load(track.photo)
            songName.text = track.name
            artist.text = track.artist

            itemView.setOnClickListener { onClick(track) }
            itemView.setOnLongClickListener {
                onLongClick(track)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_album_track, parent, false)
        return TrackViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(tracks[position])
    }

    override fun getItemCount(): Int = tracks.size
}
