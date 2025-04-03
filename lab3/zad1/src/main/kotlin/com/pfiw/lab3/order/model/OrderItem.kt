package com.pfiw.lab3.order.model

import jakarta.persistence.*
import org.hibernate.engine.internal.Cascade

@Entity
@Table(name = "order_items")
class OrderItem(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne(cascade = [CascadeType.ALL]) val product: Product,
    val quantity: Int,
)
