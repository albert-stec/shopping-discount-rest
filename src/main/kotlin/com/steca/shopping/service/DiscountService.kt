package com.steca.shopping.service

import com.steca.shopping.config.DiscountConfig
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.Currency
import org.springframework.stereotype.Service

@Service
class DiscountService(private val discountConfig: DiscountConfig) {

    fun calculateQuantityDiscount(quantity: Int, unitPrice: BigDecimal): BigDecimal {
        val currency = discountConfig.defaultCurrency

        if (quantity <= 0 || unitPrice < BigDecimal.ZERO) {
            return ZERO_DISCOUNT.withCurrencyScale(currency)
        }

        val discountRate = discountConfig.quantity.getDiscountRateForQuantity(quantity)
        val discountAmount = unitPrice.multiply(BigDecimal(quantity)).multiply(discountRate)

        return discountAmount.withCurrencyScale(currency)
    }

    fun calculatePercentageDiscount(totalAmount: BigDecimal): BigDecimal {
        val currency = discountConfig.defaultCurrency

        if (totalAmount <= BigDecimal.ZERO) {
            return ZERO_DISCOUNT.withCurrencyScale(currency)
        }

        val discountAmount = totalAmount.multiply(discountConfig.percentage.discountRate)

        return discountAmount.withCurrencyScale(currency)
    }

    fun calculateTotalDiscount(quantity: Int, unitPrice: BigDecimal): BigDecimal {
        if (quantity <= 0 || unitPrice < BigDecimal.ZERO) {
            val currency = discountConfig.defaultCurrency
            return ZERO_DISCOUNT.withCurrencyScale(currency)
        }

        val quantityDiscount = calculateQuantityDiscount(quantity, unitPrice)
        val totalAmount = unitPrice.multiply(BigDecimal(quantity))
        val percentageDiscount = calculatePercentageDiscount(totalAmount)

        return maxDiscount(quantityDiscount, percentageDiscount)
    }

    private fun BigDecimal.withCurrencyScale(currency: Currency): BigDecimal =
        this.setScale(currency.defaultFractionDigits, RoundingMode.HALF_UP)

    private fun maxDiscount(discount1: BigDecimal, discount2: BigDecimal): BigDecimal =
        if (discount1 >= discount2) discount1 else discount2

    companion object {
        private val ZERO_DISCOUNT = BigDecimal.ZERO
    }
}
