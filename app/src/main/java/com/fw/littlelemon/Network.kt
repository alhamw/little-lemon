package com.fw.littlelemon

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// This data class represents the entire JSON payload,
// which contains a list of menu items.
@Serializable
data class MenuNetwork(
    // The @SerialName("menu") annotation tells the serializer that
    // the "menu" key in the JSON should be mapped to this "menu" property.
    val menu: List<MenuItemNetwork>
)

// This data class represents a single item in the menu list.
@Serializable
data class MenuItemNetwork(
    // The @SerialName("id") annotation maps the "id" JSON key.
    @SerialName("id")
    val id: Int,

    @SerialName("title")
    val title: String,

    @SerialName("description")
    val description: String,

    @SerialName("price")
    val price: String, // Keep as String to match server response, can be converted to Double later.

    @SerialName("image")
    val image: String
)


