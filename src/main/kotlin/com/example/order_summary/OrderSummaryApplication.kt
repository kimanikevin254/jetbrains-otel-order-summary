package com.example.order_summary

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
@EnableAsync
class OrderSummaryApplication

fun main(args: Array<String>) {
	runApplication<OrderSummaryApplication>(*args)
}
