package com.lbcalbums.domain.model

data class Album(
    val id: Int,
    val albumId: Int?,
    val title: String?,
    val url: String?,
    val thumbnailUrl: String?
)
