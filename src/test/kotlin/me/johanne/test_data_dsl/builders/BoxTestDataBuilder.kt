package me.johanne.test_data_dsl.builders

import me.johanne.test_data_dsl.Box
import me.johanne.test_data_dsl.Product

@ShipmentDsl
class BoxTestDataBuilder(
    var id: Long = 1L,
    var content: List<Product> = emptyList()
) {
    inline fun buildProduct(buildProduct: ProductTestDataBuilder.() -> Unit) {
        val productBuilder = ProductTestDataBuilder()

        productBuilder.buildProduct()
        addProduct(productBuilder.build())
    }

    inline fun buildProduct(id: Long, buildProduct: ProductTestDataBuilder.() -> Unit) {
        if (!productIdExists(id)) {
            throw IllegalArgumentException("Product $id  does not exist")
        }

        val productBuilder = ProductTestDataBuilder()
        val existingCabin = getProduct(id)

        productBuilder.merge(existingCabin)
        productBuilder.buildProduct()

        removeProduct(id)
        addProduct(productBuilder.build())
    }

    fun productIdExists(id: Long): Boolean {
        return this.content.find { it.id == id } != null
    }

    fun getProduct(id: Long): Product {
        return this.content.find { it.id == id }!!
    }

    fun removeProduct(id: Long) {
        val mutableProducts = this.content.toMutableList()
        mutableProducts.removeIf { it.id == id }

        this.content = mutableProducts.toList()
    }

    fun addProduct(product: Product): BoxTestDataBuilder {
        this.content = listOf(product) + content
        return this
    }

    fun build(): Box {
        return Box(
            id = id,
            contents = content,
        )
    }

    fun merge(item: Box): BoxTestDataBuilder {
        this.id = item.id
        this.content = item.contents
        return this
    }
}