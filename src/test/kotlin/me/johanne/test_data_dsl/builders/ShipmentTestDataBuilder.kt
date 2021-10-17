package me.johanne.test_data_dsl.builders

import me.johanne.test_data_dsl.Box
import me.johanne.test_data_dsl.Shipment

inline fun buildShipment(buildShipment: ShipmentTestDataBuilder.() -> Unit): ShipmentTestDataBuilder {
    val shipmentBuilder = ShipmentTestDataBuilder()
    shipmentBuilder.buildShipment()

    return shipmentBuilder
}

@ShipmentDsl
class ShipmentTestDataBuilder(
    var id: Long = 1L,
    var shippingAddress: String = "",
    var boxes: List<Box> = emptyList()
) {

    fun override(buildShipment: ShipmentTestDataBuilder.() -> Unit): ShipmentTestDataBuilder {
        this.buildShipment()
        return this
    }

    inline fun buildBox(buildBox: BoxTestDataBuilder.() -> Unit) {
        val boxBuilder = BoxTestDataBuilder()

        boxBuilder.buildBox()
        addBox(boxBuilder.build())
    }

    inline fun buildBox(id: Long, buildBox: BoxTestDataBuilder.() -> Unit) {
        if (!boxIdExists(id)) {
            throw IllegalArgumentException("Id $id does not exist")
        }

        val boxBuilder = BoxTestDataBuilder()
        val existingCabin = getBox(id)

        boxBuilder.merge(existingCabin)
        boxBuilder.buildBox()

        removeBoxes(id)
        addBox(boxBuilder.build())
    }

    fun boxIdExists(id: Long): Boolean {
        return this.boxes.find { it.id == id } != null
    }

    fun getBox(id: Long): Box {
        return this.boxes.find { it.id == id }!!
    }

    fun removeBoxes(id: Long) {
        val mutableBoxes = this.boxes.toMutableList()
        mutableBoxes.removeIf { it.id == id }

        this.boxes = mutableBoxes.toList()
    }

    fun addBox(box: Box): ShipmentTestDataBuilder {
        this.boxes = listOf(box) + boxes
        return this
    }

    fun build(): Shipment {
        return Shipment(
            id = id,
            shippingAddress = shippingAddress,
            boxes = boxes
        )
    }

}

