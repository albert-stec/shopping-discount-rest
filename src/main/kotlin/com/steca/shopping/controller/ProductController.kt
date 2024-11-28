package com.steca.shopping.controller

import com.steca.shopping.service.ProductService
import java.util.UUID
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products")
class ProductController(private val productService: ProductService) {

    @GetMapping("/{uuid}")
    fun getProduct(@PathVariable uuid: UUID): ResponseEntity<String> {
        val product = "test"
        return ResponseEntity.ok(product)
    }
}
