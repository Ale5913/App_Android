package com.movierest.dl.model

class Movie {
    var idPelicula: Int= 0
    var nombre: String =""
    var anio: Int= 0
    var descripcion: String=""
    var imagen: String =""
    var idGenero: Int=0
    var genero: String=""
    var favorito : Favorite? = null
    var average : Float = 0.0F
    var calificacion : Qualification? = null
    var favoritos = ArrayList<Favorite>()
    var calificaciones = ArrayList<Qualification>()
}