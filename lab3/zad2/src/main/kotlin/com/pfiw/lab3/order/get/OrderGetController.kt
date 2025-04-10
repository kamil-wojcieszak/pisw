package com.pfiw.lab3.order.get

import com.pfiw.lab3.order.OrderHistoryService
import com.pfiw.lab3.order.OrderSpecifications
import com.pfiw.lab3.order.model.OrderHistory
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.data.web.PagedModel
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/order", produces = [MediaTypes.HAL_JSON_VALUE])
class OrderGetController(
    private val orderHistoryService: OrderHistoryService,
    private val pagedResourcesAssembler: PagedResourcesAssembler<OrderHistory>
) {
    @GetMapping("{id}")
    fun getOrderById(
        @PathVariable("id") id: Long,
    ): ResponseEntity<EntityModel<OrderHistory>> {
        val order = orderHistoryService.getOrderById(id)

        val orderModel = EntityModel.of(
            order,
            linkTo(methodOn(OrderGetController::class.java).getOrderById(id)).withSelfRel(),
            linkTo(
                methodOn(OrderGetController::class.java).getAllOrders(
                    null,
                    null,
                    null,
                    Pageable.unpaged(),
                    pagedResourcesAssembler
                )
            ).withRel("orders")
        )

        return ResponseEntity.ok(orderModel)
    }


    @GetMapping
    fun getAllOrders(
        @RequestParam("customerName", required = false) customerName: String?,
        @RequestParam("status", required = false) deliveryStatus: String?,
        @RequestParam("minPrice", required = false) minTotalPrice: Double?,
        @PageableDefault(size = 20, sort = ["id"]) pageable: Pageable,
        assembler: PagedResourcesAssembler<OrderHistory>
    ): ResponseEntity<PagedModel<EntityModel<OrderHistory>>> {
        val spec = OrderSpecifications.byCriteria(customerName, deliveryStatus, minTotalPrice)

        val orderPage = orderHistoryService.findOrdersByCriteria(spec, pageable)

        val pagedModel = assembler.toModel(orderPage) { order ->
            EntityModel.of(
                order,
                linkTo(methodOn(OrderGetController::class.java).getOrderById(order.id!!)).withSelfRel()
            )
        }

        return ResponseEntity.ok(pagedModel)
    }
}