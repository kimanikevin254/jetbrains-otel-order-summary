package com.example.order_summary.repository

import com.example.order_summary.entity.OrderSummary
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderSummaryRepository : JpaRepository<OrderSummary, Long>