package com.pfiw.lab3.order

import com.pfiw.lab3.order.model.OrderHistory
import org.springframework.stereotype.Service

@Service
class OrderHistoryService(
    private val orderHistoryRepository: OrderHistoryRepository,
) {
    fun getOrderById(id: Long): OrderHistory = orderHistoryRepository.findById(id).get()

    fun changeStatus(id: Long, status: String) =
        orderHistoryRepository.save(
            orderHistoryRepository.getReferenceById(id)
                .apply { this.deliveryStatus = status })

    fun saveOrder(order: OrderHistory): OrderHistory = orderHistoryRepository.save(order)

    fun getAll(): List<OrderHistory> = orderHistoryRepository.findAll()

}