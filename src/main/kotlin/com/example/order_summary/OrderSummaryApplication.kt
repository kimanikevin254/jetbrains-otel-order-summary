package com.example.order_summary

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class OrderSummaryApplication

fun main(args: Array<String>) {
	runApplication<OrderSummaryApplication>(*args)
}
