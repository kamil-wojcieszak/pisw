package com.pfiw.lab3.order.post

import com.pfiw.lab3.order.OrderHistoryService
import com.pfiw.lab3.order.dto.StatusChangeRequest
import com.pfiw.lab3.order.model.OrderHistory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/order")
class OrderPostController(
    private val orderHistoryService: OrderHistoryService,
) {
    @PostMapping
    fun createOrderHistory(@RequestBody order: OrderHistory): OrderHistory = orderHistoryService.saveOrder(order)


    @PatchMapping("/status/{id}")
    fun changeStatus(
        @PathVariable("id") id: Long,
        @RequestBody statusChangeRequest: StatusChangeRequest
    ) = orderHistoryService.changeStatus(id, statusChangeRequest.status)
}
