package com.example.popmovies2.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.popmovies2.R
import com.example.popmovies2.pojo.Result
import com.example.popmovies2.viewmodel.MovieDetailsViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.squareup.picasso.Picasso


class MovieDetailsActivity : AppCompatActivity() {

    private val BASE_URL = "http://image.tmdb.org/t/p/"
    private val IMAGE_SIZE = "w342"

    private lateinit var posterImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var overview: TextView
    private lateinit var ratingBar: RatingBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val bundle: Bundle? = intent.getBundleExtra("b")
        val res: Result? = bundle!!.getParcelable("movie")
        //Toast.makeText(this, "id: ${res!!.id}", Toast.LENGTH_SHORT).show()



        posterImageView = findViewById(R.id.movie_poster_details_img)
        titleTextView = findViewById(R.id.movie_title_details_tv)
        dateTextView = findViewById(R.id.release_date_details_tv)
        overview = findViewById(R.id.overview_tv)
        ratingBar = findViewById(R.id.rating_bar)

        titleTextView.text = res!!.title
        dateTextView.text = res.release_date
        overview.text = res.overview


        val youTubePlayerView =
            findViewById<YouTubePlayerView>(R.id.youtube_player)
        lifecycle.addObserver(youTubePlayerView)




        Picasso.get()
            .load(BASE_URL+IMAGE_SIZE+ res.poster_path)
            .placeholder(R.drawable.placeholder_movie)
            .into(posterImageView)

        val movieViewModel: MovieDetailsViewModel = ViewModelProvider(this).get(MovieDetailsViewModel::class.java)

        movieViewModel.getMovieTrailer("3b97af0112652688c49f023ecc57edb9", res.id!!)

        movieViewModel.videoLiveData.observe(this, Observer {
            youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    if (it.isNotEmpty()) {
                        val videoId = it[0].key
                        if (videoId != null) {
                            youTubePlayer.cueVideo(videoId, 0F)
                        }
                    }
                }
            })
        })
    }
}