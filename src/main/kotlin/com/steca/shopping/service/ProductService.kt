package com.steca.shopping.service

import com.steca.shopping.model.dbo.Product
import com.steca.shopping.repository.ProductRepository
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun getByUuid(uuid: UUID): Product {
        return productRepository.findById(uuid).orElseThrow {
            ProductNotFoundException("Product with UUID $uuid not found")
        }
    }
}

class ProductNotFoundException(message: String) : RuntimeException(message)
