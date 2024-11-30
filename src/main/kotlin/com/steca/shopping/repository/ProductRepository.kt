package com.steca.shopping.repository

import com.steca.shopping.mode.Product
import java.util.Optional
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, UUID> {
    fun findByName(name: String): Optional<Product>

    fun existsByName(name: String): Boolean
}
