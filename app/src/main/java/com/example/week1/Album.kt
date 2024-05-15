package com.example.week1

data class Album(
    val title : String? = "",
    var singer : String? = "",
    var coverImg : Int? = null,
    var sings : ArrayList<Song>? = null
 )