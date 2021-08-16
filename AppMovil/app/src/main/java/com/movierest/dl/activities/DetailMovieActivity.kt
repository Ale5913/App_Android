package com.movierest.dl.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.MediaController
import android.widget.RatingBar
import com.movierest.dl.R
import com.movierest.dl.adapter.UserCalificationAdapter
import com.movierest.dl.model.Movie
import com.movierest.dl.model.Qualification
import com.movierest.dl.restapi.ResoucesURL
import com.movierest.dl.restapi.RestApiAdapter
import com.movierest.dl.utils.UserPreferences
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_movie.*
import kotlinx.android.synthetic.main.adapter_movie.view.*
import kotlinx.android.synthetic.main.bottom_sheet_califications.*
import retrofit2.Call
import retrofit2.Response

class DetailMovieActivity : AppCompatActivity() {

    var movie_id: Int = 0
    val TAG = "DetailMovieActivity"
    var favorite = 0

    var bottomSheetBehavior: BottomSheetBehavior<View>? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        val extras = intent.extras
        if (extras != null) {
            movie_id = extras.getInt("movie_id")
        }

        Log.i(TAG, "movie_id: $movie_id")

        if (UserPreferences.getId(this) == 0) {
            favoriteIV.visibility = View.GONE
            favoriteTV.visibility = View.GONE
        }

        favoriteIV.setOnClickListener {
            _favorite()
        }

        ratingBar.setOnRatingBarChangeListener {
                ratingBar: RatingBar, rating: Float, b: Boolean ->
                _qualification(rating)
        }

        _showMovie()
        _bottomSheet()

        shareIV.setOnClickListener {
            _share()
        }

    }

    private fun init(qualifications: ArrayList<Qualification>){
        usersCalificationRV.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        usersCalificationRV.adapter = UserCalificationAdapter(this, qualifications)
    }

    private fun _share() {

        var intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_SUBJECT, movieTV.text.toString())
        intent.putExtra(Intent.EXTRA_TEXT, descriptionTV.text.toString())

        intent.type = "text/plain"
        startActivity(Intent.createChooser(intent, "Compartir"))

    }

    private fun _showMovie() {

        var user_id: Int? = null
        if (UserPreferences.getId(this) > 0) {
            user_id = UserPreferences.getId(this)
        }

        var restApiAdapter = RestApiAdapter()
        var gson = restApiAdapter.gsonDeserizerMovie()
        val endPointsApi = restApiAdapter.conection(gson)

        val call = endPointsApi.detailMovie(movie_id, user_id)

        call.enqueue(object : retrofit2.Callback<Movie> {

            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {

                var movie = response.body()

                if (movie != null) {


                    nombreTV.text = movie.name
                    descriptionTV.text = movie.description
                    qualificationTV.text = getString(R.string.qualification) + ": ${movie.average}"

                    if (movie.favorite?.movie_favorite_id != null) {
                        favorite = 1
                        favoriteIV.setImageResource(R.drawable.ic_favorite_40dp)
                    }

                    init(movie.qualifications)
                    movieIV.setVideoPath(ResoucesURL.URL_RESOURCE_IMAGE + movie.video)

                  val  mediaController=MediaController(this@DetailMovieActivity)
                    movieIV.setMediaController(mediaController)
                    mediaController.setAnchorView(movieIV)
                    movieIV.start()


                    // Picasso.get()
                    //     .load("https://images.unsplash.com/photo-1628779238951-be2c9f2a59f4?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80")
                    //    .error(R.mipmap.ic_launcher)
                    //    .into(movieIV);

                }
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Log.e(TAG, t.toString())
            }
        })
    }

    private fun _qualification(qualification: Float) {

        var restApiAdapter = RestApiAdapter()
        var gson = restApiAdapter.gsonDeserizerSimple()
        val endPointsApi = restApiAdapter.conection(gson)

        val call = endPointsApi.qualification(
            UserPreferences.getId(this),
            UserPreferences.getToken(this),
            movie_id,
            qualification)

        call.enqueue(object : retrofit2.Callback<String> {

            override fun onResponse(call: Call<String>, response: Response<String>) {

                var res = response.body()

                if (res == "ok") {

                }

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(TAG, t.toString())
            }
        })
    }

    private fun _favorite() {

        if (favorite == 0)
            favorite = 1
        else
            favorite = 0

        var restApiAdapter = RestApiAdapter()
        var gson = restApiAdapter.gsonDeserizerSimple()
        val endPointsApi = restApiAdapter.conection(gson)

        val call = endPointsApi.favorite(
            UserPreferences.getId(this),
            UserPreferences.getToken(this),
            movie_id, favorite)

        call.enqueue(object : retrofit2.Callback<String> {

            override fun onResponse(call: Call<String>, response: Response<String>) {

                var res = response.body()

                if (res == "ok") {
                    if (favorite == 0) {
                        favoriteIV.setImageResource(R.drawable.ic_favorite_border_40dp)
                    } else {
                        favoriteIV.setImageResource(R.drawable.ic_favorite_40dp)
                    }
                }

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(TAG, t.toString())
            }
        })
    }

    private fun _bottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLL)

        calificationIV.setOnClickListener {
            Log.i("aaaaa","click")
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }
}