package com.steca.shopping.service

import java.math.BigDecimal
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DiscountServiceTest @Autowired constructor(private val discountService: DiscountService) {

    @Nested
    @DisplayName("calculateQuantityDiscount Tests")
    inner class CalculateQuantityDiscountTests {

        @Test
        @DisplayName("Should return 0.00 discount for quantity between 1 and 9")
        fun `should return 0 discount for quantity 5`() {
            val quantity = 5
            val unitPrice = BigDecimal("100.00")
            val expectedDiscount = BigDecimal("0.00")

            val actualDiscount = discountService.calculateQuantityDiscount(quantity, unitPrice)

            assertThat(actualDiscount)
                .withFailMessage(
                    "Expected discount of %s, but got %s",
                    expectedDiscount,
                    actualDiscount,
                )
                .isEqualByComparingTo(expectedDiscount)
        }

        @Test
        @DisplayName("Should return 5% discount for quantity between 10 and 19")
        fun `should return 5 percent discount for quantity 15`() {
            val quantity = 15
            val unitPrice = BigDecimal("200.00")
            val expectedDiscount = BigDecimal("150.00")

            val actualDiscount = discountService.calculateQuantityDiscount(quantity, unitPrice)

            assertThat(actualDiscount)
                .withFailMessage(
                    "Expected discount of %s, but got %s",
                    expectedDiscount,
                    actualDiscount,
                )
                .isEqualByComparingTo(expectedDiscount)
        }

        @Test
        @DisplayName("Should return 10% discount for quantity between 20 and 49")
        fun `should return 10 percent discount for quantity 25`() {
            val quantity = 25
            val unitPrice = BigDecimal("50.00")
            val expectedDiscount = BigDecimal("125.00")

            val actualDiscount = discountService.calculateQuantityDiscount(quantity, unitPrice)

            assertThat(actualDiscount)
                .withFailMessage(
                    "Expected discount of %s, but got %s",
                    expectedDiscount,
                    actualDiscount,
                )
                .isEqualByComparingTo(expectedDiscount)
        }

        @Test
        @DisplayName("Should return 15% discount for quantity 50 and above")
        fun `should return 15 percent discount for quantity 100`() {
            val quantity = 100
            val unitPrice = BigDecimal("10.00")
            val expectedDiscount = BigDecimal("150.00")

            val actualDiscount = discountService.calculateQuantityDiscount(quantity, unitPrice)

            assertThat(actualDiscount)
                .withFailMessage(
                    "Expected discount of %s, but got %s",
                    expectedDiscount,
                    actualDiscount,
                )
                .isEqualByComparingTo(expectedDiscount)
        }

        @Test
        @DisplayName("Should return 0.00 discount for quantity 0")
        fun `should return 0 discount for quantity 0`() {
            val quantity = 0
            val unitPrice = BigDecimal("100.00")
            val expectedDiscount = BigDecimal("0.00")

            val actualDiscount = discountService.calculateQuantityDiscount(quantity, unitPrice)

            assertThat(actualDiscount)
                .withFailMessage(
                    "Expected discount of %s, but got %s",
                    expectedDiscount,
                    actualDiscount,
                )
                .isEqualByComparingTo(expectedDiscount)
        }

        @Test
        @DisplayName("Should return 0.00 discount for negative quantity")
        fun `should return 0 discount for negative quantity`() {
            val quantity = -5
            val unitPrice = BigDecimal("100.00")
            val expectedDiscount = BigDecimal("0.00")

            val actualDiscount = discountService.calculateQuantityDiscount(quantity, unitPrice)

            assertThat(actualDiscount)
                .withFailMessage(
                    "Expected discount of %s, but got %s",
                    expectedDiscount,
                    actualDiscount,
                )
                .isEqualByComparingTo(expectedDiscount)
        }

        @Test
        @DisplayName("Should return 0.00 discount for negative unit price")
        fun `should return 0 discount for negative unit price`() {
            val quantity = 10
            val unitPrice = BigDecimal("-100.00")
            val expectedDiscount = BigDecimal("0.00")

            val actualDiscount = discountService.calculateQuantityDiscount(quantity, unitPrice)

            assertThat(actualDiscount)
                .withFailMessage(
                    "Expected discount of %s, but got %s",
                    expectedDiscount,
                    actualDiscount,
                )
                .isEqualByComparingTo(expectedDiscount)
        }
    }

    @Nested
    @DisplayName("calculatePercentageDiscount Tests")
    inner class CalculatePercentageDiscountTests {

        @Test
        @DisplayName("Should return 10% discount for total amount 1000.00")
        fun `should return 10 percent discount for total amount 1000`() {
            val totalAmount = BigDecimal("1000.00")
            val expectedDiscount = BigDecimal("100.00")

            val actualDiscount = discountService.calculatePercentageDiscount(totalAmount)

            assertThat(actualDiscount)
                .withFailMessage(
                    "Expected discount of %s, but got %s",
                    expectedDiscount,
                    actualDiscount,
                )
                .isEqualByComparingTo(expectedDiscount)
        }

        @Test
        @DisplayName("Should return 0.00 discount for total amount 0.00")
        fun `should return 0 discount for total amount 0`() {
            val totalAmount = BigDecimal("0.00")
            val expectedDiscount = BigDecimal("0.00")

            val actualDiscount = discountService.calculatePercentageDiscount(totalAmount)

            assertThat(actualDiscount)
                .withFailMessage(
                    "Expected discount of %s, but got %s",
                    expectedDiscount,
                    actualDiscount,
                )
                .isEqualByComparingTo(expectedDiscount)
        }

        @Test
        @DisplayName("Should return 0.00 discount for negative total amount")
        fun `should return 0 discount for negative total amount`() {
            val totalAmount = BigDecimal("-500.00")
            val expectedDiscount = BigDecimal("0.00")

            val actualDiscount = discountService.calculatePercentageDiscount(totalAmount)

            assertThat(actualDiscount)
                .withFailMessage(
                    "Expected discount of %s, but got %s",
                    expectedDiscount,
                    actualDiscount,
                )
                .isEqualByComparingTo(expectedDiscount)
        }

        @Test
        @DisplayName("Should return correctly rounded discount for decimal total amount")
        fun `should return correctly rounded discount for decimal total amount`() {
            val totalAmount = BigDecimal("1234.56")
            val expectedDiscount = BigDecimal("123.46")

            val actualDiscount = discountService.calculatePercentageDiscount(totalAmount)

            assertThat(actualDiscount)
                .withFailMessage(
                    "Expected discount of %s, but got %s",
                    expectedDiscount,
                    actualDiscount,
                )
                .isEqualByComparingTo(expectedDiscount)
        }
    }

    @Nested
    @DisplayName("calculateTotalDiscount Tests")
    inner class CalculateTotalDiscountTests {

        @Test
        @DisplayName(
            "Should return higher percentage discount when it is greater than quantity discount"
        )
        fun `should return percentage discount when it is higher`() {
            val quantity = 5
            val unitPrice = BigDecimal("100.00")
            val expectedTotalDiscount = BigDecimal("50.00")

            val actualTotalDiscount = discountService.calculateTotalDiscount(quantity, unitPrice)

            assertThat(actualTotalDiscount)
                .withFailMessage(
                    "Expected total discount of %s, but got %s",
                    expectedTotalDiscount,
                    actualTotalDiscount,
                )
                .isEqualByComparingTo(expectedTotalDiscount)
        }

        @Test
        @DisplayName(
            "Should return higher quantity discount when it is greater than percentage discount"
        )
        fun `should return quantity discount when it is higher`() {
            val quantity = 15
            val unitPrice = BigDecimal("200.00")
            val expectedTotalDiscount = BigDecimal("300.00")

            val actualTotalDiscount = discountService.calculateTotalDiscount(quantity, unitPrice)

            assertThat(actualTotalDiscount)
                .withFailMessage(
                    "Expected total discount of %s, but got %s",
                    expectedTotalDiscount,
                    actualTotalDiscount,
                )
                .isEqualByComparingTo(expectedTotalDiscount)
        }

        @Test
        @DisplayName("Should return either discount when both discounts are equal")
        fun `should return either discount when both are equal`() {
            val quantity = 20
            val unitPrice = BigDecimal("50.00")
            val expectedTotalDiscount = BigDecimal("100.00")

            val actualTotalDiscount = discountService.calculateTotalDiscount(quantity, unitPrice)

            assertThat(actualTotalDiscount)
                .withFailMessage(
                    "Expected total discount of %s, but got %s",
                    expectedTotalDiscount,
                    actualTotalDiscount,
                )
                .isEqualByComparingTo(expectedTotalDiscount)
        }

        @Test
        @DisplayName("Should return 0.00 discount when quantity is 0 and total amount is 0")
        fun `should return 0 discount when no discounts apply`() {
            val quantity = 0
            val unitPrice = BigDecimal("100.00")
            val expectedTotalDiscount = BigDecimal("0.00")

            val actualTotalDiscount = discountService.calculateTotalDiscount(quantity, unitPrice)

            assertThat(actualTotalDiscount)
                .withFailMessage(
                    "Expected total discount of %s, but got %s",
                    expectedTotalDiscount,
                    actualTotalDiscount,
                )
                .isEqualByComparingTo(expectedTotalDiscount)
        }

        @Test
        @DisplayName("Should return 0.00 discount for negative quantity and unit price")
        fun `should return 0 discount for negative quantity and unit price`() {
            val quantity = -10
            val unitPrice = BigDecimal("-50.00")
            val expectedTotalDiscount = BigDecimal("0.00")

            val actualTotalDiscount = discountService.calculateTotalDiscount(quantity, unitPrice)

            assertThat(actualTotalDiscount)
                .withFailMessage(
                    "Expected total discount of %s, but got %s",
                    expectedTotalDiscount,
                    actualTotalDiscount,
                )
                .isEqualByComparingTo(expectedTotalDiscount)
        }
    }
}
