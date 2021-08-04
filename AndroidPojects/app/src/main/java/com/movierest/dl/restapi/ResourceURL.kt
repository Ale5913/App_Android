package com.movierest.dl.restapi

object ResourceURL {
    const val URL_BASE="http://192.168.100.6/MovieRest/"
    //recursos
    const val URL_RESOURCE_IMAGE =URL_BASE+"uploads/peliculas/"
    const val URL_RESOURCE_API =URL_BASE+"uploads/api/"

    //api
    const val URL_GET_IMAGE_PROMOTIONAL =URL_BASE+"api/Peliculas/IMAGEN_PROMOCIONAL"
    //usuario
    const val URL_POST_USER_LOGIN = URL_BASE+"api/usuarios/login"
    const val URL_POST_USER_REGISTER= URL_BASE+"api/usuarios/singUp"
    //peliculas
    const val URL_GET_SHOW_ALL_MOVIES=URL_BASE+"api/Peliculas/show"
    const val URL_GET_SHOW_MOVIE=URL_BASE+"api/Peliculas/show/{idPelicula}"
}