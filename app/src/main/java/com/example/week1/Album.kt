package com.example.week1

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AlbumTable")
data class Album(
    @PrimaryKey(autoGenerate = false) var id : Int = 0,
    val title : String? = "",
    var singer : String? = "",
    var coverImg : Int? = null,
//    var sings : ArrayList<Song>? = null
 )