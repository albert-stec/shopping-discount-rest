package com.steca.shopping.controller

import com.steca.shopping.config.DiscountConfig
import com.steca.shopping.service.ProductService
import java.util.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products")
class ProductController(
    private val productService: ProductService,
    private val discountConfig: DiscountConfig,
) {

    @GetMapping("/{uuid}")
    fun getProduct(@PathVariable uuid: UUID): ResponseEntity<DiscountConfig?> {
        return ResponseEntity.ok(discountConfig)
    }
}
