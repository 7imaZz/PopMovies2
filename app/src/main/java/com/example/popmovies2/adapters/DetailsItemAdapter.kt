package com.example.popmovies2.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.popmovies2.R
import com.example.popmovies2.pojo.Cast
import com.example.popmovies2.pojo.Result
import com.example.popmovies2.ui.MovieDetailsActivity
import com.example.popmovies2.ui.PersonWorksActivity
import com.squareup.picasso.Picasso

class DetailsItemAdapter() : RecyclerView.Adapter<DetailsItemAdapter.DetailsItemViewHolder>() {

    private val BASE_URL = "http://image.tmdb.org/t/p/"
    private val IMAGE_SIZE = "w342"

    class DetailsItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView1 = itemView.findViewById<TextView>(R.id.details_item_tv1)!!
        val textView2 = itemView.findViewById<TextView>(R.id.details_item_tv2)!!
        val itemImageView = itemView.findViewById<ImageView>(R.id.details_item_img)!!

    }

    lateinit var casts: List<Cast>
    lateinit var similarMovies: List<Result>
    lateinit var context: Context

    var isCast = false

    constructor(casts: List<Cast>, context: Context,isCast: Boolean): this(){
        this.casts = casts
        this.isCast = isCast
        this.context = context
    }

    constructor(similar: List<Result>, context: Context): this(){
        this.similarMovies = similar
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.details_item, parent, false)
        return DetailsItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (isCast){
            casts.size
        }else
            similarMovies.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DetailsItemViewHolder, position: Int) {
        if (isCast){
            holder.textView1.text = casts[position].name
            holder.textView2.text = "( ${casts[position].character} )"

            Picasso.get()
                    .load(BASE_URL+IMAGE_SIZE+casts[position].profilePath)
                    .placeholder(R.drawable.placeholder_movie)
                    .into(holder.itemImageView)

            holder.itemView.setOnClickListener {
                val intent = Intent(context, PersonWorksActivity::class.java)
                val b = Bundle()
                b.putParcelable("cast", casts[position])
                intent.putExtra("b", b)
                intent.putExtra("dir", false)
                context.startActivity(intent)
            }
        }else{
            holder.textView1.visibility = View.GONE
            holder.textView2.text = similarMovies[position].title

            Picasso.get()
                    .load(BASE_URL+IMAGE_SIZE+similarMovies[position].poster_path)
                    .placeholder(R.drawable.placeholder_movie)
                    .into(holder.itemImageView)

            holder.itemView.setOnClickListener {
                val intent = Intent(context, MovieDetailsActivity::class.java)
                val b = Bundle()
                b.putParcelable("movie", similarMovies[position])
                intent.putExtra("b", b)
                context.startActivity(intent)
            }
        }
    }
}