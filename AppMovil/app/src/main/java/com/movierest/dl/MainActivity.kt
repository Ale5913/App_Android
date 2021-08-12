package com.movierest.dl

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.movierest.dl.activities.user.LoginActivity
import com.movierest.dl.activities.user.UserActivity
import com.movierest.dl.adapter.MovieAdapter
import com.movierest.dl.adapter.TypeMoviesSpinnerAdapter
import com.movierest.dl.model.Movie
import com.movierest.dl.model.TypeMovie
import com.movierest.dl.restapi.ResoucesURL
import com.movierest.dl.restapi.RestApiAdapter
import com.movierest.dl.restapi.model.MoviesReponse
import com.movierest.dl.restapi.model.TypesMovieReponse
import com.movierest.dl.utils.UserPreferences
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.movieIV
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.bottom_sheet_filter.*
import retrofit2.Call
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    val PAGE_SIZE = 4

    var movies = mutableListOf<Movie>()
    var typesMovies = arrayListOf<TypeMovie>()
    var moviesAux = mutableListOf<Movie>()
    var offset = 0
    var filterSearch = ""
    var filterYear = ""

    var bottomSheetBehavior: BottomSheetBehavior<View>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        toolbar.inflateMenu(R.menu.toolbar)

        _init()
        _showMovies()
        _bottomSheet()
        _imagePromotional()
        _showTypeMovie()

        Log.i(TAG, "onCreate")

    }

    override fun onResume() {
        super.onResume()

        if(UserPreferences.getId(this) > 0){
            // esta autenticado
            isLogin()
        }


        Log.i(TAG, "onResume")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val menuInflate = menuInflater
        menuInflate.inflate(R.menu.toolbar, menu)

        val searchItem = menu.findItem(R.id.action_search)


        val searchView = searchItem.actionView as SearchView

        val queryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                //Log.i(TAG,query)
                offset = 0
                filterSearch = query
                filterYear = ""
                _showMovies()
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                //Log.i(TAG,query)
                return true
            }
        }

        searchView.setOnQueryTextListener(queryTextListener)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_filter -> {
                bottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
            }
            R.id.action_login -> {
                var intent = if(UserPreferences.getId(this) > 0){
                    Intent(this, UserActivity::class.java)
                }else{
                    Intent(this, LoginActivity::class.java)
                }

                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun _init() {
        swipeRefreshLayout.setOnRefreshListener {
            //offset = offset + PAGE_SIZE
            offset += PAGE_SIZE
            _showMovies()
        }
    }

    private fun _bottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLL)

        searchButton.setOnClickListener {
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
            offset = 0
            filterSearch = searchET.text.toString()
            filterYear = yearET.text.toString()
            _showMovies()
        }
    }

    private fun _showMovies(/*name: String = "", year: String = ""*/) {

        swipeRefreshLayout.setEnabled(false)
        swipeRefreshLayout.setRefreshing(true)

        var restApiAdapter = RestApiAdapter()
        var gson = restApiAdapter.gsonDeserizerMovies()
        val endPointsApi = restApiAdapter.conection(gson)

        val call = if (filterSearch == "")
            endPointsApi.show(offset)
        else
            endPointsApi.search(filterSearch, filterYear, offset)

        call.enqueue(object : retrofit2.Callback<MoviesReponse> {

            override fun onResponse(call: Call<MoviesReponse>, response: Response<MoviesReponse>) {

                var data = response.body()

                with(data?.movies) {
                    if (this != null) {

                        moviesAux = this;

                        if (offset == 0) {
                            // carga inicial
                            movies = moviesAux
                            moviesRV.layoutManager =
                                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                            moviesRV.adapter = MovieAdapter(this@MainActivity, movies)
                        } else {
                            if (moviesAux.size > 0) {
                                movies.addAll(moviesAux)
                                moviesRV.adapter!!.notifyItemRangeInserted(movies.size - moviesAux.size, movies.size)
                            }
                        }
                    }
                }

                swipeRefreshLayout.setEnabled(true)
                swipeRefreshLayout.setRefreshing(false)
            }

            override fun onFailure(call: Call<MoviesReponse>, t: Throwable) {
                Log.e(TAG, t.toString())
                swipeRefreshLayout.setEnabled(true)
                swipeRefreshLayout.setRefreshing(false)
            }
        })
    }

    private fun _showTypeMovie() {

        var restApiAdapter = RestApiAdapter()
        var gson = restApiAdapter.gsonDeserizerTypeMovie()
        val endPointsApi = restApiAdapter.conection(gson)

        val call = endPointsApi.typesMovies()


        call.enqueue(object : retrofit2.Callback<TypesMovieReponse> {

            override fun onResponse(call: Call<TypesMovieReponse>, response: Response<TypesMovieReponse>) {

                var data = response.body()

                with(data?.typesMovie) {
                    if (this != null) {
                        typesMovies = this
                        Log.i(TAG+"__",typesMovies[0].name)

                        // llenar Spinnes
                        typeMovieSpinner.adapter = TypeMoviesSpinnerAdapter(this@MainActivity,R.layout.adapter_spinner_type_movie,typesMovies)


                    }
                }
            }

            override fun onFailure(call: Call<TypesMovieReponse>, t: Throwable) {
                Log.e(TAG, t.toString())
            }
        })
    }

    private fun _imagePromotional() {
        var restApiAdapter = RestApiAdapter()
        var gson = restApiAdapter.gsonDeserizerSimple()
        val endPointsApi = restApiAdapter.conection(gson)

        val call = endPointsApi.imagePromotional()

        call.enqueue(object : retrofit2.Callback<String> {

            override fun onResponse(call: Call<String>, response: Response<String>) {

                var imageName = response.body()

                if (imageName != null) {

                    Picasso.get()
                        .load(ResoucesURL.URL_RESOURCE_API + imageName)
                        .error(R.mipmap.ic_launcher)
                        .into(movieIV);
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(TAG, t.toString())
            }
        })
    }

    private fun isLogin() {
        var restApiAdapter = RestApiAdapter()
        var gson = restApiAdapter.gsonDeserizerSimple()
        val endPointsApi = restApiAdapter.conection(gson)

        val call = endPointsApi.isLogin(UserPreferences.getToken(this))

        call.enqueue(object : retrofit2.Callback<String> {

            override fun onResponse(call: Call<String>, response: Response<String>) {

                var token = response.body()

                if (token == null || token == "") {
                    UserPreferences.closeSession(this@MainActivity)
                }

                Log.i(TAG,"TOKEN: $token")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(TAG, t.toString())
            }
        })
    }
}
