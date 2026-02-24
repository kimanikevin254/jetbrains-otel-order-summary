package com.example.order_summary.service

import com.example.order_summary.entity.OrderSummary
import com.example.order_summary.repository.OrderRepository
import com.example.order_summary.repository.OrderSummaryRepository
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
class OrderSummaryJob(
    private val orderRepository: OrderRepository,
    private val orderSummaryRepository: OrderSummaryRepository
) {
    private val logger = LoggerFactory.getLogger(OrderSummaryJob::class.java)

    @Scheduled(fixedDelay = 1800000) // 30mins in ms
    fun generateSummary() {
        logger.info("Starting order summary job...")

        val periodEnd = LocalDateTime.now()
        val periodStart = periodEnd.minusHours(24)

        val orders = orderRepository.findByCreatedAtAfter(periodStart)
        if (orders.isEmpty()) {
            logger.info("No orders found in the last 24 hours. Skipping summary generation.")
            return
        }
        logger.info("Found ${orders.size} orders to process")

        var processedCount = 0
        var totalAmount = BigDecimal.ZERO

        for (order in orders) {
            try {
                logger.info("Processing order ${order.id} for customer ${order.customerId}...")

                // Simulate processing work
                Thread.sleep(2000)

                // Simulate occasional failures
                if (order.amount > BigDecimal("400")) {
                    throw RuntimeException("Order amount exceeds threshold: ${order.amount}")
                }

                totalAmount = totalAmount.add(order.amount)
                processedCount++
            } catch (e: Exception) {
                logger.error("Failed to process order ${order.id}: ${e.message}")
                // Continue processing other orders
            }
        }

        val summary = OrderSummary(
            totalOrders = orders.size,
            totalAmount = totalAmount,
            periodStart = periodStart,
            periodEnd = periodEnd
        )

        orderSummaryRepository.save(summary)
        logger.info("Order summary job completed. Total: ${orders.size} orders, Amount $totalAmount")
    }
}