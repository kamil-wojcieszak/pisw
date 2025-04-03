package com.pfiw.lab3.order.model

import jakarta.persistence.*

@Entity
@Table(name = "deliveries")
class Delivery(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val courierName: String,
    var status: DeliveryStatus
)
