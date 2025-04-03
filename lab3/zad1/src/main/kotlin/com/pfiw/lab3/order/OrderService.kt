package com.pfiw.lab3.order

import com.pfiw.lab3.order.model.DeliveryStatus
import com.pfiw.lab3.order.model.Order
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository,
) {
    fun getOrderById(id: Long): Order = orderRepository.findById(id).get()

    fun changeStatus(id: Long, status: DeliveryStatus) =
        orderRepository.save(
            orderRepository.getReferenceById(id)
                .apply { this.delivery.status = status })

    fun saveOrder(order: Order): Order = orderRepository.save(order)

}