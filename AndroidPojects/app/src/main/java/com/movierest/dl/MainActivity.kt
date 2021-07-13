package com.movierest.dl

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.movierest.dl.adapter.MovieAdapter
import kotlinx.android.synthetic.main.activity_main.*
import com.movierest.dl.model.Movie
import com.movierest.dl.restapi.RestApiAdapter
import com.movierest.dl.restapi.model.MoviesResponse
import kotlinx.android.synthetic.main.bottom_sheet_filter.*
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    val PAGE_SIZE = 2

    var movies = mutableListOf<Movie>()
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

        Log.i(TAG, "onCreate")

    }

    override fun onResume() {
        super.onResume()

        Log.i(TAG, "onResume")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val menuInflate = menuInflater
        menuInflate.inflate(R.menu.toolbar, menu)

        val searchItem = menu.findItem(R.id.action_serach)


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
        }

        return super.onOptionsItemSelected(item)
    }

    private fun _init(){
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

        call.enqueue(object : retrofit2.Callback<MoviesResponse> {

            override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {

                var data = response.body()

                with(data?.movies) {
                    if (this != null) {

                        moviesAux = this;

                        if(offset == 0){
                            // carga inicial
                            movies = moviesAux
                            moviesRV.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                            moviesRV.adapter = MovieAdapter(this@MainActivity, movies)
                        }else{
                            if( moviesAux.size > 0){
                                movies.addAll(moviesAux)
                                moviesRV.adapter!!.notifyItemRangeInserted(movies.size - moviesAux.size, movies.size)
                            }
                        }
                    }
                }

                swipeRefreshLayout.setEnabled(true)
                swipeRefreshLayout.setRefreshing(false)
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                Log.e(TAG, t.toString())
                swipeRefreshLayout.setEnabled(true)
                swipeRefreshLayout.setRefreshing(false)
            }
        })
    }
}
