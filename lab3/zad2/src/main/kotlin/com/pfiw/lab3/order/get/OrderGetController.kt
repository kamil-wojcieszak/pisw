package com.pfiw.lab3.order.get

import com.pfiw.lab3.order.OrderHistoryService
import com.pfiw.lab3.order.model.OrderHistory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/order")
class OrderGetController(
    private val orderHistoryService: OrderHistoryService,
) {
    @GetMapping("{id}")
    fun getOrderById(
        @PathVariable("id") id: Long,
    ): OrderHistory = orderHistoryService.getOrderById(id)


    @GetMapping
    fun getAllOrders(
    ): List<OrderHistory> = orderHistoryService.getAll()

}