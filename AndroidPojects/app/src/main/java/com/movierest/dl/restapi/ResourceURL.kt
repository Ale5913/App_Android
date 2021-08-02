package com.movierest.dl.restapi

object ResourceURL {
    const val URL_BASE="http://192.168.100.6/MovieRest/"
    //recursos
    const val URL_RESOURCE_IMAGE =URL_BASE+"uploads/peliculas/"
    //usuario
    const val URL_POST_USER_LOGIN = URL_BASE+"api/usuarios/login"
    //peliculas
    const val URL_GET_SHOW_ALL_MOVIES=URL_BASE+"api/Peliculas/show"
    const val URL_GET_SHOW_MOVIE=URL_BASE+"api/Peliculas/show/{idPelicula}"
}