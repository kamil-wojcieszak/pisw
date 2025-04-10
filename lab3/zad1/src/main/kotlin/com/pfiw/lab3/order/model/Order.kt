package com.pfiw.lab3.order.model

import com.pfiw.lab3.order.dto.CreateOrderHistoryRequest
import jakarta.persistence.*

@Entity
@Table(name = "orders")
class Order(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val customerName: String,
    @ManyToMany(cascade = [CascadeType.ALL]) val items: List<OrderItem>,
    @OneToOne(cascade = [CascadeType.ALL]) val delivery: Delivery
) {
    fun toCreateOrderHistoryRequest(): CreateOrderHistoryRequest = CreateOrderHistoryRequest(
        customerName = this.customerName,
        courierName = this.delivery.courierName,
        deliveryStatus = this.delivery.status.toString(),
        productNames = this.items.joinToString(separator = ",") { it.product.name },
        totalPrice = this.items.sumOf { it.product.price * it.quantity.toBigDecimal() }.toDouble()
    )
}