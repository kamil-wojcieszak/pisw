package com.pfiw.lab3.order

import com.pfiw.lab3.order.dto.StatusChangeRequest
import com.pfiw.lab3.order.model.Order
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/order")
class OrderController(
    val orderRepository: OrderRepository,
) {
    @PostMapping
    fun addStock(@RequestBody order: Order): Order {
        return orderRepository.save(order)
    }


    @PutMapping("/status/{id}")
    fun addStock(
        @PathVariable("id") id: Long,
        @RequestBody statusChangeRequest: StatusChangeRequest
    ) {
        orderRepository.save(
            orderRepository.getReferenceById(id)
                .apply { this.delivery.status = statusChangeRequest.status })

    }
}
