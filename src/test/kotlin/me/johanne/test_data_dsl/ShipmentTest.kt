package me.johanne.test_data_dsl

import org.junit.jupiter.api.Test

class ShipmentTest {

    @Test
    fun `shipment should have one box with snowboard in it`() {
        val shipment = shipmentWithOneBoxWithOneProduct().override {
            overrideBox(1L) {
                overrideProduct(1L) {
                    name = "Custom Snowboard"
                }
            }
        }.build()

        assert(shipment.boxes.first().contents.first().name == "Custom Snowboard")
    }

    @Test
    fun `box should have correct contentSize`() {
        val shipment = shipmentWithOneBoxWithTwoProducts().build()

        assert(shipment.boxes.first().contentSize == 2)
    }

    @Test
    fun `box should have correct total weight`() {
        val shipment = shipmentWithOneBoxWithTwoProducts().override {
            overrideBox(1L) {
                overrideProduct(1L) {
                    weight = 2.0
                }
                overrideProduct(2L) {
                    weight = 3.0
                }
            }
        }.build()

        assert(shipment.boxes.first().totalWeight == 5.0)
    }

}