package com.crestinfosystems_jinay.happyplaces.model


import java.io.Serializable

data class HappyPlace(
    var id: Int = 0,
    var title: String = "",
    var description: String = "",
    var location: String = "",
    var date: String = "",
    var imageURL: String = ""
) : Serializable
