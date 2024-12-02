package com.steca.shopping.service

import com.steca.shopping.mode.Product
import com.steca.shopping.repository.ProductRepository
import java.math.BigDecimal
import java.util.Optional
import java.util.UUID
import kotlin.test.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*

@ExtendWith(MockitoExtension::class)
class ProductServiceTest {

    @Mock private lateinit var productRepository: ProductRepository

    @Mock private lateinit var discountService: DiscountService

    @InjectMocks private lateinit var productService: ProductService

    private lateinit var product: Product

    @BeforeEach
    fun setUp() {
        product =
            Product(
                id = UUID.randomUUID(),
                name = "Test Product",
                description = "Test Description",
                price = BigDecimal("100.00"),
                currency = "USD",
            )
    }

    @Test
    fun `getByUuid - success`() {
        whenever(productRepository.findById(product.id!!)).thenReturn(Optional.of(product))

        val result = productService.getByUuid(product.id!!)

        assertEquals(product, result)
        verify(productRepository).findById(product.id!!)
    }

    @Test
    fun `getByUuid - product not found`() {
        whenever(productRepository.findById(product.id!!)).thenReturn(Optional.empty())

        val exception =
            assertThrows<ProductNotFoundException> { productService.getByUuid(product.id!!) }

        assertEquals("Product with UUID ${product.id} not found", exception.message)
        verify(productRepository).findById(product.id!!)
    }

    @Test
    fun `calculateTotalPrice - success`() {
        val quantity = 2
        val totalDiscount = BigDecimal("20.00")
        whenever(productRepository.findById(product.id!!)).thenReturn(Optional.of(product))
        whenever(discountService.calculateTotalDiscount(quantity, product.price))
            .thenReturn(totalDiscount)

        val result = productService.calculateTotalPrice(product.id!!, quantity)

        val expectedTotalPrice = product.price.multiply(BigDecimal(quantity))
        val expectedDiscountedPrice = expectedTotalPrice.subtract(totalDiscount)

        assertEquals(expectedDiscountedPrice, result.discountedPrice)
        assertEquals(product, result.product)
        verify(productRepository).findById(product.id!!)
        verify(discountService).calculateTotalDiscount(quantity, product.price)
    }

    @Test
    fun `calculateTotalPrice - invalid quantity`() {
        val quantity = 0

        val exception =
            assertThrows<InvalidQuantityException> {
                productService.calculateTotalPrice(product.id!!, quantity)
            }

        assertEquals("Quantity must be greater than zero", exception.message)
        verifyNoInteractions(productRepository)
        verifyNoInteractions(discountService)
    }

    @Test
    fun `calculateTotalPrice - product not found`() {
        val quantity = 1
        whenever(productRepository.findById(product.id!!)).thenReturn(Optional.empty())

        val exception =
            assertThrows<ProductNotFoundException> {
                productService.calculateTotalPrice(product.id!!, quantity)
            }

        assertEquals("Product with UUID ${product.id} not found", exception.message)
        verify(productRepository).findById(product.id!!)
        verifyNoInteractions(discountService)
    }
}
