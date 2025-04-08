package com.pfiw.lab3.order.dto

data class CreateOrderHistoryRequest(
    val customerName: String,
    val courierName: String,
    var deliveryStatus: String,
    val productNames: String,
    val totalPrice: Double,
)
