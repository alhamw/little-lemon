package com.fw.littlelemon

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MenuNetwork(
    val menu: List<MenuItemNetwork>
)

@Serializable
data class MenuItemNetwork(
    @SerialName("id")
    val id: Int,

    @SerialName("title")
    val title: String,

    @SerialName("description")
    val description: String,

    @SerialName("price")
    val price: String,

    @SerialName("image")
    val image: String,

    @SerialName("category")
    val category: String
)

fun MenuItemNetwork.toEntity(): MenuItemEntity {
    return MenuItemEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        price = this.price,
        image = this.image,
        category = this.category
    )
}


