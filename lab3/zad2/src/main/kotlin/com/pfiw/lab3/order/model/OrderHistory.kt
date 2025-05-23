package com.pfiw.lab3.order.model

import jakarta.persistence.*
import org.springframework.hateoas.RepresentationModel

@Entity
@Table(name = "orders_history")
class OrderHistory(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val customerName: String,
    val courierName: String,
    var deliveryStatus: String,
    val productNames: String,
    val totalPrice: Double,
) : RepresentationModel<OrderHistory>()