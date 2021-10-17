package me.johanne.test_data_dsl

import me.johanne.test_data_dsl.builders.ShipmentTestDataBuilder
import me.johanne.test_data_dsl.builders.buildShipment
import java.math.BigDecimal


fun shipmentWithOneBoxWithOneProduct(): ShipmentTestDataBuilder = buildShipment {
    id = 1L
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

fun shipmentWithOneBoxWithTwoProducts(): ShipmentTestDataBuilder = buildShipment {
    id = 1L
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
            name = "Pink Fuzzy Jacket  2"
            price = BigDecimal.valueOf(40)
            weight = 2.0
        }
    }
}

fun shipmentWithTwoBoxesWithOneProduct(): ShipmentTestDataBuilder = buildShipment {
    id = 1L
    buildBox {
        id = 1L
        buildProduct {
            id = 1L
            name = "Pink Fuzzy Jacket"
            price = BigDecimal.valueOf(20)
            weight = 1.0
        }
    }

    buildBox {
        id = 2L
        buildProduct {
            id = 2L
            name = "Pink Fuzzy Jacket 2"
            price = BigDecimal.valueOf(40)
            weight = 2.0
        }
    }
}