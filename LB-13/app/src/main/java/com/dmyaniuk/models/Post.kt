package com.dmyaniuk.models

data class Post (
    override var id: Int,
    val userId: Int,
    val title: String,
    val body: String
) : GenericEntity {
    override fun toString(): String {
        return this.title
    }
}