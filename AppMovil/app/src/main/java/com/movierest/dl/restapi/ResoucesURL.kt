package com.movierest.dl.restapi

object ResoucesURL {
    const val URL_BASE = "https://youupp.000webhostapp.com/"

    // *** recursos
    const val URL_RESOURCE_IMAGE = URL_BASE+"uploads/movies/"
    const val URL_RESOURCE_AVATAR = URL_BASE+"uploads/avatar/"

    const val URL_RESOURCE_API = URL_BASE+"uploads/api/"


    // *** usuario
    const val URL_POST_USER_LOGIN = URL_BASE+"api/users/login"
    const val URL_POST_USER_REGISTER= URL_BASE+"api/users/sign_up"
    const val URL_POST_USER_UPLOAD_AVATAR= URL_BASE+"api/users/cargar_avatar/{user_id}"
    const val URL_GET_IS_LOGIN = URL_BASE+"api/users/is_login/{token}"

    // *** peliculas
    const val URL_GET_SHOW_ALL_MOVIES = URL_BASE+"api/movies/show"
    const val URL_GET_SHOW_MOVIE = URL_BASE+"api/movies/show/{movie_id}"
    const val URL_POST_FAVORITE = URL_BASE+"api/movies/favorite"
    const val URL_POST_QUALIFICATION = URL_BASE+"api/movies/qualification"
    const val URL_GET_TYPES_MOVIE = URL_BASE+"api/movies/types_movie"

    // *** api
    const val URL_GET_IMAGE_PROMOTIONAL= URL_BASE+"api/movies/image_promotional"
}