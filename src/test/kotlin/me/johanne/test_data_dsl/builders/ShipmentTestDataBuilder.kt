package me.johanne.test_data_dsl.builders

import me.johanne.test_data_dsl.Box
import me.johanne.test_data_dsl.Shipment

// Entry point for building a shipment
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

    // Add a new box to the list of boxes in shipment
    inline fun buildBox(buildBox: BoxTestDataBuilder.() -> Unit) {
        val boxBuilder = BoxTestDataBuilder()

        boxBuilder.buildBox()
        addBox(boxBuilder.build())
    }

    // Override an existing box, the box must be in the shipment already for this to work
    inline fun overrideBox(id: Long, buildBox: BoxTestDataBuilder.() -> Unit) {
        if (!boxIdExists(id)) {
            throw IllegalArgumentException("Box with id $id does not exist")
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

