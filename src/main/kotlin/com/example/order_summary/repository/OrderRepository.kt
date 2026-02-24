package com.example.order_summary.repository

import com.example.order_summary.entity.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface OrderRepository : JpaRepository<Order, Long> {
    fun findByCreatedAtAfter(datetime: LocalDateTime): List<Order>
}