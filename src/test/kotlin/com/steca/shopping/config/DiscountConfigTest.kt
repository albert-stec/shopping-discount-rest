package com.steca.shopping.config

import java.math.BigDecimal
import java.util.Currency
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DiscountConfigTest @Autowired constructor(private val discountConfig: DiscountConfig) {

    @Test
    fun `defaultCurrency should be USD`() {
        assertThat(discountConfig.defaultCurrency).isEqualTo(Currency.getInstance("USD"))
    }

    @Test
    fun `quantity thresholds should be correctly loaded`() {
        val thresholds = discountConfig.quantity.thresholds
        assertThat(thresholds).hasSize(4)

        val threshold1 = thresholds[0]
        assertThat(threshold1.min).isEqualTo(1)
        assertThat(threshold1.max).isEqualTo(9)
        assertThat(threshold1.rate).isEqualByComparingTo(BigDecimal("0.00"))

        val threshold2 = thresholds[1]
        assertThat(threshold2.min).isEqualTo(10)
        assertThat(threshold2.max).isEqualTo(19)
        assertThat(threshold2.rate).isEqualByComparingTo(BigDecimal("0.05"))

        val threshold3 = thresholds[2]
        assertThat(threshold3.min).isEqualTo(20)
        assertThat(threshold3.max).isEqualTo(49)
        assertThat(threshold3.rate).isEqualByComparingTo(BigDecimal("0.10"))

        val threshold4 = thresholds[3]
        assertThat(threshold4.min).isEqualTo(50)
        assertThat(threshold4.max).isEqualTo(-1)
        assertThat(threshold4.rate).isEqualByComparingTo(BigDecimal("0.15"))
    }

    @Test
    fun `percentage discountRate should be 10 percent`() {
        assertThat(discountConfig.percentage.discountRate).isEqualByComparingTo(BigDecimal("0.10"))
    }
}
