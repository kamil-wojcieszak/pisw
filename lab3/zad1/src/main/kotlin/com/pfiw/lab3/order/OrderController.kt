package com.pfiw.lab3.order

import com.pfiw.lab3.order.dto.StatusChangeRequest
import com.pfiw.lab3.order.model.Order
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/order")
class OrderController(
    val orderRepository: OrderRepository,
    private val orderService: OrderService,
) {
    @PostMapping
    fun addStock(@RequestBody order: Order): Order = orderService.saveOrder(order)


    @PutMapping("/status/{id}")
    fun changeStatus(
        @PathVariable("id") id: Long,
        @RequestBody statusChangeRequest: StatusChangeRequest
    ) = orderService.changeStatus(id, statusChangeRequest.status)


    @GetMapping("{id}")
    fun getOrder(
        @PathVariable("id") id: Long,
    ): Order = orderService.getOrderById(id)

}
