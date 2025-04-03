package com.pfiw.lab3.order.dto

import com.pfiw.lab3.order.model.Delivery
import com.pfiw.lab3.order.model.OrderItem


data class CreateOrderRequest(
    val customerName: String,
    val items: List<OrderItem>,
    val delivery: Delivery
)
