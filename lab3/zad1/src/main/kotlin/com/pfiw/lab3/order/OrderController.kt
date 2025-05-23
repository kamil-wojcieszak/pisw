package com.pfiw.lab3.order

import com.pfiw.lab3.order.dto.StatusChangeRequest
import com.pfiw.lab3.order.model.Order
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestClient

@RestController
@RequestMapping("/order")
class OrderController(
    private val orderService: OrderService,
) {
    val restClient = RestClient.create()

    @PostMapping
    fun addStock(@RequestBody order: Order): Order =
        orderService.saveOrder(order).also {
            restClient.post().uri("http://localhost:8081/order")
                .contentType(MediaType.APPLICATION_JSON)
                .body(it.toCreateOrderHistoryRequest())
                .retrieve()
                .toBodilessEntity()
        }


    @PatchMapping("/status/{id}")
    fun changeStatus(
        @PathVariable("id") id: Long,
        @RequestBody statusChangeRequest: StatusChangeRequest
    ) = orderService.changeStatus(id, statusChangeRequest.status).also {
        restClient.patch()
            .uri("http://localhost:8081/order/status/$id")
            .contentType(MediaType.APPLICATION_JSON)
            .body(statusChangeRequest.status.toString())
            .retrieve()
            .toBodilessEntity()
    }


    @GetMapping("{id}")
    fun getOrder(
        @PathVariable("id") id: Long,
    ): Order = orderService.getOrderById(id)

}
