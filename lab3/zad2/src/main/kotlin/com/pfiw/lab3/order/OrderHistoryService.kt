package com.pfiw.lab3.order

import com.pfiw.lab3.order.model.OrderHistory
import org.springframework.stereotype.Service
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.transaction.annotation.Transactional

@Service
class OrderHistoryService(
    private val orderHistoryRepository: OrderHistoryRepository,
) {


    @Transactional(readOnly = true)
    fun getOrderById(id: Long): OrderHistory {
        return orderHistoryRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Order not found with id: $id") }
    }

    @Transactional(readOnly = true)
    fun findOrdersByCriteria(
        specification: Specification<OrderHistory>,
        pageable: Pageable
    ): Page<OrderHistory> {
        return orderHistoryRepository.findAll(specification, pageable)
    }

    fun getAll(): List<OrderHistory> = orderHistoryRepository.findAll()

    @Transactional
    fun changeStatus(id: Long, status: String) =
        orderHistoryRepository
            .save(
                orderHistoryRepository
                    .getReferenceById(id)
                    .apply { this.deliveryStatus = status }
            )

    @Transactional
    fun saveOrder(order: OrderHistory): OrderHistory = orderHistoryRepository.save(order)
}