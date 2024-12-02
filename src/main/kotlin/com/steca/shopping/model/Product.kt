package com.steca.shopping.mode

import jakarta.persistence.*
import java.math.BigDecimal
import java.util.UUID
import org.hibernate.annotations.UuidGenerator

@Entity
@Table(name = "products")
data class Product(
    @Column(nullable = false, unique = true) val name: String,
    @Column(nullable = false) val description: String,
    @Column(nullable = false) val price: BigDecimal,
    @Column(nullable = false) val currency: String,
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    val id: UUID? = null,
)

data class ProductWithDiscount(val product: Product, val discountedPrice: BigDecimal)
