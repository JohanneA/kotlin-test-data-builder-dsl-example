package me.johanne.test_data_dsl.builders

import org.junit.jupiter.api.Test
import java.math.BigDecimal

class ShipmentBuilderTest {

    @Test
    fun `should build shipment with a box with a product`() {
        val shipment = buildShipment {
            id = 2L
            buildBox {
                id = 1L
                buildProduct {
                    id = 1L
                    name = "Pink Fuzzy Jacket"
                    price = BigDecimal.valueOf(20)
                    weight = 1.0
                }
            }
        }.build()


        assert(shipment.id == 2L)
        assert(shipment.boxes.size == 1)
        assert(shipment.boxes[0].id == 1L)
        assert(shipment.boxes[0].contentSize == 1)
        assert(shipment.boxes[0].contents[0].name == "Pink Fuzzy Jacket")
    }

    @Test
    fun `should build shipment with two boxes`() {
        val shipment = buildShipment {
            id = 2L
            buildBox {
                id = 1L
            }
            buildBox {
                id = 2L
            }
        }.build()


        assert(shipment.id == 2L)
        assert(shipment.boxes.size == 2)
    }

    @Test
    fun `should build shipment with one box with two products`() {
        val shipment = buildShipment {
            id = 2L
            buildBox {
                id = 1L
                buildProduct {
                    id = 1L
                    name = "Pink Fuzzy Jacket"
                    price = BigDecimal.valueOf(20)
                    weight = 1.0
                }
                buildProduct {
                    id = 2L
                    name = "Pink Fuzzy Jacket 2"
                    price = BigDecimal.valueOf(20)
                    weight = 1.0
                }
            }
        }.build()


        assert(shipment.id == 2L)
        assert(shipment.boxes.size == 1)
        assert(shipment.boxes[0].contentSize == 2)
    }

    @Test
    fun `should build shipment with overridden shipping address`() {
        val shipment = buildShipment {
            id = 2L
            buildBox {
                id = 1L
                buildProduct {
                    id = 1L
                    name = "Pink Fuzzy Jacket"
                    price = BigDecimal.valueOf(20)
                    weight = 1.0
                }
            }
        }

        val firstShipment = shipment.build()

        assert(firstShipment.id == 2L)
        assert(firstShipment.shippingAddress == "")

        val secondShipment = shipment.override {
            shippingAddress = "Some address"
        }.build()

        assert(secondShipment.id == 2L)
        assert(secondShipment.shippingAddress == "Some address")
        assert(secondShipment.boxes.size == 1)
        assert(secondShipment.boxes[0].contentSize == 1)
    }

    @Test
    fun `should build shipment with overridden product`() {
        val shipment = buildShipment {
            id = 2L
            buildBox {
                id = 2L
                buildProduct {
                    id = 2L
                    name = "Pink Fuzzy Jacket"
                    price = BigDecimal.valueOf(20)
                    weight = 1.0
                }
            }
        }

        val firstShipment = shipment.build()

        assert(firstShipment.boxes[0].contents[0].name == "Pink Fuzzy Jacket")

        val secondShipment = shipment.override {
            overrideBox(2L) {
                overrideProduct(2L) {
                    name = "Pink Fuzzy Jacket 2"
                    weight = 2.0
                }
            }
        }.build()

        assert(secondShipment.boxes[0].contents[0].id == 2L)
        assert(secondShipment.boxes[0].contents[0].name == "Pink Fuzzy Jacket 2")
        assert(secondShipment.boxes[0].contents[0].price == BigDecimal.valueOf(20))
        assert(secondShipment.boxes[0].contents[0].weight == 2.0)
    }
}