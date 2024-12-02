@file:Suppress("DEPRECATION")

package com.steca.shopping.controller

import com.steca.shopping.BaseIntegrationTest
import com.steca.shopping.ShoppingApplication
import com.steca.shopping.mode.Product
import com.steca.shopping.repository.ProductRepository
import com.steca.shopping.service.DiscountService
import java.math.BigDecimal
import java.util.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.eq
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest(classes = [ShoppingApplication::class])
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ProductControllerTest
@Autowired
constructor(private val mockMvc: MockMvc, private val productRepository: ProductRepository) :
    BaseIntegrationTest() {

    @MockBean private lateinit var discountService: DiscountService

    @BeforeEach
    fun setUp() {
        productRepository.deleteAll()
    }

    @Test
    @DisplayName("GET /products/{uuid} - Success")
    fun `GET product by uuid - success`() {
        val product =
            Product(
                name = "Test Product",
                description = "Test Description",
                price = BigDecimal("100.00"),
                currency = "USD",
            )
        val savedProduct = productRepository.save(product)

        mockMvc
            .perform(get("/products/${savedProduct.id}"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(savedProduct.id.toString()))
            .andExpect(jsonPath("$.name").value("Test Product"))
            .andExpect(jsonPath("$.description").value("Test Description"))
            .andExpect(jsonPath("$.price").value(100.00))
            .andExpect(jsonPath("$.currency").value("USD"))
    }

    @Test
    @DisplayName("GET /products/{uuid}/total-price - Success")
    fun `GET total price - success`() {
        val product =
            Product(
                name = "Test Product",
                description = "Test Description",
                price = BigDecimal("100.00"),
                currency = "USD",
            )
        val savedProduct = productRepository.save(product)

        whenever(discountService.calculateTotalDiscount(eq(2), eq(product.price)))
            .thenReturn(BigDecimal("20.00"))

        mockMvc
            .perform(get("/products/${savedProduct.id}/total-price").param("quantity", "2"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.product.id").value(savedProduct.id.toString()))
            .andExpect(jsonPath("$.product.name").value("Test Product"))
            .andExpect(jsonPath("$.discountedPrice").value(180.00))
    }

    @Test
    @DisplayName("GET /products/{uuid} - Not Found")
    fun `GET product by uuid - not found`() {
        val nonExistentUuid = UUID.randomUUID()

        mockMvc
            .perform(get("/products/$nonExistentUuid"))
            .andExpect(status().isNotFound)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.error").value("NOT_FOUND"))
            .andExpect(jsonPath("$.message").value("Product with UUID $nonExistentUuid not found"))
    }

    @Test
    @DisplayName("GET /products/{uuid}/total-price - Invalid Quantity")
    fun `GET total price - invalid quantity`() {
        val product =
            Product(
                name = "Test Product",
                description = "Test Description",
                price = BigDecimal("100.00"),
                currency = "USD",
            )
        val savedProduct = productRepository.save(product)

        mockMvc
            .perform(get("/products/${savedProduct.id}/total-price").param("quantity", "0"))
            .andExpect(status().isBadRequest)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.error").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("Quantity must be greater than zero"))
    }

    @Test
    @DisplayName("GET /products/{uuid}/total-price - Product Not Found")
    fun `GET total price - product not found`() {
        val nonExistentUuid = UUID.randomUUID()

        mockMvc
            .perform(get("/products/$nonExistentUuid/total-price").param("quantity", "1"))
            .andExpect(status().isNotFound)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.error").value("NOT_FOUND"))
            .andExpect(jsonPath("$.message").value("Product with UUID $nonExistentUuid not found"))
    }
}
