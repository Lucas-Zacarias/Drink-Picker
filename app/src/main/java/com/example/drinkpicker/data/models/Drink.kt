package com.example.drinkpicker.data.models

class Drink (
    val id: Long?,
    val name: String?,
    val description: String?,
    val imageId: Int?,
    val imageDescription: String?){

    data class Builder(
        var id: Long? = null,
        var name: String? = null,
        var description: String? = null,
        var imageId: Int?= null,
        var imageDescription: String? = null){

        fun id(id: Long) = apply {this.id = id}
        fun name(name: String) = apply {this.name = name}
        fun description(description: String?) = apply {this.description = description}
        fun imageId(imageId: Int) = apply {this.imageId = imageId}
        fun imageDescription(imageDescription: String) = apply {this.imageDescription = imageDescription}
        fun build() = Drink(id, name, description, imageId, imageDescription)
    }
}