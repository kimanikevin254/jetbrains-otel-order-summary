package com.example.order_summary.config

import com.example.order_summary.entity.Order
import com.example.order_summary.repository.OrderRepository
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime
import kotlin.random.Random

@Component
class DataInitializer(private val orderRepository: OrderRepository) {
    @PostConstruct
    fun initializeData() {
        // Only initialize if DB is empty
        if (orderRepository.count() > 0) {
            println("Database already contains ${orderRepository.count()} orders. Skipping initialization...")
            return
        }

        val statuses = listOf("PENDING", "COMPLETED", "CANCELLED")
        val orders = (1..20).map { i ->
            Order(
                customerId = "CUST-${10000 + i}",
                amount = BigDecimal(Random.nextDouble(10.0, 500.0)).setScale(2, RoundingMode.HALF_UP),
                status = statuses.random(),
                createdAt = LocalDateTime.now().minusHours(Random.nextLong(0, 48))
            )
        }

        orderRepository.saveAll(orders)
        println("Initialized ${orders.size} sample orders")
    }
}