package com.example.popmovies2.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.popmovies2.R
import com.example.popmovies2.adapters.DetailsItemAdapter
import com.example.popmovies2.pojo.Result
import com.example.popmovies2.viewmodel.MovieDetailsViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import java.util.*


class MovieDetailsActivity : AppCompatActivity() {

    private val BASE_URL = "http://image.tmdb.org/t/p/"
    private val IMAGE_SIZE = "w342"

    private lateinit var posterImageView: ImageView
    private lateinit var directorImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var overview: TextView
    private lateinit var ratingTextView: TextView
    private lateinit var genresTextView: TextView
    private lateinit var languageTextView: TextView
    private lateinit var directorTextView: TextView
    private lateinit var similarMoviesLabel: TextView
    private lateinit var watchlistTextView: TextView
    private lateinit var markAsFavourite: TextView
    private lateinit var ratingBar: RatingBar
    private lateinit var youTubePlayerView: YouTubePlayerView
    private lateinit var castsRV: RecyclerView
    private lateinit var similarMoviesRv: RecyclerView
    private lateinit var favCard: CardView
    private lateinit var watchlistCard: CardView

    private lateinit var castsAdapter: DetailsItemAdapter
    private lateinit var moviesAdapter: DetailsItemAdapter

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(true)

        val bundle: Bundle? = intent.getBundleExtra("b")
        val res: Result? = bundle!!.getParcelable("movie")
        //Toast.makeText(this, "id: ${res!!.id}", Toast.LENGTH_SHORT).show()


        val sharedPref = getSharedPreferences(res!!.id.toString(), Context.MODE_PRIVATE)
        val editor = sharedPref.edit()



        posterImageView = findViewById(R.id.movie_poster_details_img)
        titleTextView = findViewById(R.id.movie_title_details_tv)
        dateTextView = findViewById(R.id.release_date_details_tv)
        overview = findViewById(R.id.overview_tv)
        ratingBar = findViewById(R.id.rating_bar)
        genresTextView = findViewById(R.id.genres_tv)
        ratingTextView = findViewById(R.id.rating_tv)
        languageTextView = findViewById(R.id.language_details_tv)
        directorTextView = findViewById(R.id.director_tv)
        directorImageView = findViewById(R.id.director_img)
        castsRV = findViewById(R.id.casts_rv)
        similarMoviesRv = findViewById(R.id.similar_movies_rv)
        similarMoviesLabel = findViewById(R.id.similar_movies_label_tv)
        markAsFavourite = findViewById(R.id.fav_tv)
        watchlistTextView = findViewById(R.id.watchlist_tv)
        favCard = findViewById(R.id.fav_card)
        watchlistCard = findViewById(R.id.watchlist_card)

        castsRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        similarMoviesRv.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        var favFlag = sharedPref.getInt(res.id.toString(), 0)
        var watchlistFlag = sharedPref.getInt(res.id.toString()+"w", 0)
        if (favFlag == 1){
            markAsFavourite.text = "Marked As Favourite"
            favCard.setCardBackgroundColor(Color.GRAY)
        }

        if (watchlistFlag == 1){
            watchlistTextView.text = "Added To Watchlist"
            watchlistCard.setCardBackgroundColor(Color.GRAY)
        }

        castsAdapter = DetailsItemAdapter(mutableListOf(), this, true)
        moviesAdapter = DetailsItemAdapter(mutableListOf(), this)

        castsRV.adapter = castsAdapter
        similarMoviesRv.adapter = moviesAdapter

        titleTextView.text = res.title
        dateTextView.text = res.release_date
        overview.text = res.overview
        ratingTextView.text = res.vote_average.toString()
        ratingBar.rating = (res.vote_average!!*5)/10
        languageTextView.text = "Language: "+res.original_language.toString().toUpperCase(Locale.ROOT)


        youTubePlayerView =
            findViewById(R.id.youtube_player)
        lifecycle.addObserver(youTubePlayerView)


        Glide.with(this)
            .load(BASE_URL+IMAGE_SIZE+res.poster_path)
            .centerCrop()
            .placeholder(R.drawable.placeholder_movie)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(posterImageView)



        val movieViewModel: MovieDetailsViewModel = ViewModelProvider(this).get(
            MovieDetailsViewModel::class.java
        )

        movieViewModel.getMovieTrailer("3b97af0112652688c49f023ecc57edb9", res.id!!)
        movieViewModel.getMovieDetails("3b97af0112652688c49f023ecc57edb9", res.id!!)
        movieViewModel.getCasts("3b97af0112652688c49f023ecc57edb9", res.id!!)
        movieViewModel.getSimilarMovies("3b97af0112652688c49f023ecc57edb9", res.id!!)

        markAsFavourite.setOnClickListener {
            if(favFlag == 0) {
                movieViewModel.insertMovie(this, res)
                favCard.setCardBackgroundColor(Color.GRAY)
                editor.putInt(res.id.toString(), 1)
                editor.apply()
                favFlag = 1
            }else{
                movieViewModel.deleteMovie(this, res.id!!)
                favCard.setCardBackgroundColor(ContextCompat.getColor(this, R.color.gold))
                editor.putInt(res.id.toString(), 0)
                editor.apply()
                favFlag = 0
            }
        }

        watchlistTextView.setOnClickListener {
            if(watchlistFlag == 0) {
                movieViewModel.insertMovieInWatchList(this, res)
                watchlistCard.setCardBackgroundColor(Color.GRAY)
                editor.putInt(res.id.toString()+"w", 1)
                editor.apply()
                watchlistFlag = 1
            }else{
                movieViewModel.deleteMovieFromWatchlist(this, res.id!!)
                watchlistCard.setCardBackgroundColor(ContextCompat.getColor(this, R.color.gold))
                editor.putInt(res.id.toString()+"w", 0)
                editor.apply()
                watchlistFlag = 0
            }
        }

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

        movieViewModel.moviesLiveData.observe(this, Observer {
            it.genres!!.forEach {
                genresTextView.append(it.name + " | ")
            }
        })

        movieViewModel.castsLiveData.observe(this, Observer { cast ->
            cast.crew!!.forEach {
                if (it.job.equals("Director")) {
                    directorTextView.text = it.originalName
                    Glide.with(this)
                        .load(BASE_URL+IMAGE_SIZE+it.profilePath)
                        .placeholder(R.drawable.placeholder_movie)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(directorImageView)

                    directorImageView.setOnClickListener { _ ->
                        val intent = Intent(
                            this@MovieDetailsActivity,
                            PersonWorksActivity::class.java
                        )
                        val b = Bundle()
                        b.putParcelable("crew", it)
                        intent.putExtra("b", b)
                        intent.putExtra("dir", true)
                        startActivity(intent)
                    }
                }


            }

            castsAdapter.casts = cast.cast!!
            castsAdapter.notifyDataSetChanged()
        })

        movieViewModel.similarMoviesLiveData.observe(this, Observer {
            moviesAdapter.similarMovies = it.results!!
            moviesAdapter.notifyDataSetChanged()
            if (it.results.isNullOrEmpty()) {
                similarMoviesLabel.visibility = View.GONE
            }
        })



    }

    override fun onDestroy() {
        super.onDestroy()
        youTubePlayerView.release()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}