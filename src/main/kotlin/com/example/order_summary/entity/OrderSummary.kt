package com.example.order_summary.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "order_summaries")
data class OrderSummary(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val totalOrders: Int,

    @Column(nullable = false)
    val totalAmount: BigDecimal,

    @Column(nullable = false)
    val periodStart: LocalDateTime,

    @Column(nullable = false)
    val periodEnd: LocalDateTime,

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)