package com.steca.shopping.config

import java.math.BigDecimal
import java.util.Currency
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "discount")
data class DiscountConfig(
    val defaultCurrencyCode: String = "USD",
    val quantity: QuantityDiscountConfig = QuantityDiscountConfig(),
    val percentage: PercentageDiscountConfig = PercentageDiscountConfig(),
) {
    val defaultCurrency: Currency = Currency.getInstance(defaultCurrencyCode)
}

data class QuantityDiscountConfig(val thresholds: List<DiscountThreshold> = listOf()) {
    fun getDiscountRateForQuantity(quantity: Int): BigDecimal {
        return thresholds
            .find { quantity in it.min..(if (it.max == -1) Int.MAX_VALUE else it.max) }
            ?.rate ?: BigDecimal.ZERO
    }
}

data class PercentageDiscountConfig(val discountRate: BigDecimal = BigDecimal.ZERO)

data class DiscountThreshold(
    val min: Int = 0,
    val max: Int = 0,
    val rate: BigDecimal = BigDecimal.ZERO,
)
