package com.example.order_summary.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
data class Order (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val customerId: String,

    @Column(nullable = false)
    val amount: BigDecimal,

    @Column(nullable = false)
    val status: String,

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)