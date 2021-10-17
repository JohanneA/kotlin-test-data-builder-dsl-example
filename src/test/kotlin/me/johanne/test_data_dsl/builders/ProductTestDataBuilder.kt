package me.johanne.test_data_dsl.builders

import me.johanne.test_data_dsl.Product
import java.math.BigDecimal

@ShipmentDsl
class ProductTestDataBuilder(
    var id: Long = 1L,
    var name: String = "Test product",
    var price: BigDecimal = BigDecimal.TEN,
    var weight: Double = 1.0
) {

    fun build(): Product {
        return Product(
            id = id,
            name = name,
            price = price,
            weight = weight
        )
    }

    fun merge(item: Product): ProductTestDataBuilder {
        this.id = item.id
        this.name = item.name
        this.price = item.price
        this.weight = item.weight
        return this
    }

}