package com.steca.shopping.service

import com.steca.shopping.mode.Product
import com.steca.shopping.mode.ProductWithDiscount
import com.steca.shopping.repository.ProductRepository
import java.math.BigDecimal
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val discountService: DiscountService,
) {

    fun getByUuid(uuid: UUID): Product {
        return productRepository.findById(uuid).orElseThrow {
            ProductNotFoundException("Product with UUID $uuid not found")
        }
    }

    fun getByUuidWithDiscount(uuid: UUID, quantity: Int): ProductWithDiscount {
        if (quantity <= 0) throw InvalidQuantityException("Quantity must be greater than zero")
        val product = getByUuid(uuid)
        val discountedPrice = calculateDiscountedPrice(product.price, quantity)
        return ProductWithDiscount(product, discountedPrice)
    }

    private fun calculateDiscountedPrice(price: BigDecimal, quantity: Int): BigDecimal {
        val totalPrice = price.multiply(BigDecimal(quantity))
        val totalDiscount = discountService.calculateTotalDiscount(quantity, price)
        return totalPrice.subtract(totalDiscount)
    }
}

class ProductNotFoundException(message: String) : RuntimeException(message)

class InvalidQuantityException(message: String) : RuntimeException(message)
