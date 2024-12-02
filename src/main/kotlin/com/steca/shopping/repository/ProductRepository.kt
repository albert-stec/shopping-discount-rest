package com.steca.shopping.repository

import com.steca.shopping.mode.Product
import java.util.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository interface ProductRepository : JpaRepository<Product, UUID>
