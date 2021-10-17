package me.johanne.test_data_dsl

import java.math.BigDecimal

data class Shipment(
    val id: Long,
    val shippingAddress: String,
    val boxes: List<Box>
)

data class Box(
    val id: Long,
    val contents: List<Product>
) {
    val contentSize = contents.size
    val totalWeight = contents.sumByDouble { it.weight }
}

data class Product(
    val id: Long,
    val name: String,
    val price: BigDecimal,
    val weight: Double
)