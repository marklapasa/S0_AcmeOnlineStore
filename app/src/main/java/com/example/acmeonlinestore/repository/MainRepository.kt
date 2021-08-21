package com.example.acmeonlinestore.repository

import com.example.acmeonlinestore.AcmeOnlineStoreApp
import com.example.acmeonlinestore.R
import com.example.acmeonlinestore.extensions.readAsString
import com.example.acmeonlinestore.model.MockProductResponse
import com.example.acmeonlinestore.model.Product
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class MainRepository {

    /**
     * Provide a list of Products
     */
    val products: List<Product>? = getProductsFromJson()

    /**
     * Load products from Json file
     */
    private fun getProductsFromJson(): List<Product>? {
        return AcmeOnlineStoreApp.getResources()?.openRawResource(R.raw.products)?.readAsString()
            ?.let { jsonStr ->
                Json.decodeFromString<MockProductResponse>(jsonStr).products
            }?.toList()?.sortedBy {
                it.name
            }
    }
}
