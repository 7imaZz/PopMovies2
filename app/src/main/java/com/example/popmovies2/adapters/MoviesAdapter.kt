package com.example.popmovies2.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.popmovies2.R
import com.example.popmovies2.adapters.MoviesAdapter.MoviesViewHolder
import com.example.popmovies2.pojo.Result
import com.squareup.picasso.Picasso

class MoviesAdapter(private val context: Context, var movies: MutableList<Result>) : RecyclerView.Adapter<MoviesViewHolder>() {

    private val BASE_URL = "http://image.tmdb.org/t/p/"
    private val IMAGE_SIZE = "w342"

    class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleTextView: TextView = itemView.findViewById(R.id.movie_title_tv)
        val releaseDateTextView: TextView = itemView.findViewById(R.id.release_date_tv)
        val posterImageView: ImageView = itemView.findViewById(R.id.movie_poster_img)
        val rate: RatingBar = itemView.findViewById(R.id.rating_bar)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MoviesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.titleTextView.text = movies[position].title
        holder.releaseDateTextView.text = movies[position].release_date

        holder.rate.rating = (movies[position].vote_average!!*5)/10

        Picasso.get()
            .load(BASE_URL+IMAGE_SIZE+movies[position].poster_path)
            .placeholder(R.drawable.placeholder_movie)
            .into(holder.posterImageView)

    }
}