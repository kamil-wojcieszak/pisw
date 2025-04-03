package com.pfiw.lab3.order.model

import jakarta.persistence.*

@Entity
@Table(name = "orders")
class Order(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val customerName: String,
    @ManyToMany(cascade = [CascadeType.ALL]) val items: List<OrderItem>,
    @OneToOne(cascade = [CascadeType.ALL]) val delivery: Delivery
)