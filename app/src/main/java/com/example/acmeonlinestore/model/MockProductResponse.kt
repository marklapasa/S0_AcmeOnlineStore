package com.example.acmeonlinestore.model

import kotlinx.serialization.Serializable

@Serializable
data class MockProductResponse(
    val products: List<Product>? = null
)