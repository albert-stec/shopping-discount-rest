package com.steca.shopping.config

import com.steca.shopping.service.InvalidQuantityException
import com.steca.shopping.service.ProductNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException::class)
    fun handleProductNotFoundException(
        ex: ProductNotFoundException
    ): ResponseEntity<ErrorResponse> {
        val errorResponse =
            ErrorResponse(error = "NOT_FOUND", message = ex.message ?: "Product not found")
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(InvalidQuantityException::class)
    fun handleInvalidQuantityException(
        ex: InvalidQuantityException
    ): ResponseEntity<ErrorResponse> {
        val errorResponse =
            ErrorResponse(error = "BAD_REQUEST", message = ex.message ?: "Invalid quantity")
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }
}

data class ErrorResponse(val error: String, val message: String)
