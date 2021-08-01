package com.movierest.dl.restapi

object ResourceURL {
    const val URL_BASE="http://192.168.100.6/MovieRest/"
    const val URL_RESOURCE_IMAGE ="uploads/peliculas/"
    const val URL_GET_SHOW_ALL_MOVIES=URL_BASE+"api/Peliculas/show"
    const val URL_GET_SHOW_MOVIE=URL_BASE+"api/Peliculas/show/{idPelicula}"
}