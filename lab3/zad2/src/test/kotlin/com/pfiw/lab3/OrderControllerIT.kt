package com.pfiw.lab3

import com.fasterxml.jackson.databind.ObjectMapper
import com.pfiw.lab3.order.OrderHistoryService
import com.pfiw.lab3.order.dto.StatusChangeRequest
import com.pfiw.lab3.order.model.OrderHistory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerIT {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockitoBean
    private lateinit var orderHistoryService: OrderHistoryService

    private lateinit var sampleOrder1: OrderHistory
    private lateinit var sampleOrder2: OrderHistory
    private lateinit var newOrderInput: OrderHistory
    private lateinit var savedOrderOutput: OrderHistory

    @BeforeEach
    fun setUp() {
        sampleOrder1 = OrderHistory(
            id = 1L,
            customerName = "Alice Integration",
            courierName = "Speedy Courier",
            deliveryStatus = "PENDING",
            productNames = "Laptop, Mouse",
            totalPrice = 1250.99
        )
        sampleOrder2 = OrderHistory(
            id = 2L,
            customerName = "Bob Integration",
            courierName = "Reliable Delivery",
            deliveryStatus = "PROCESSING",
            productNames = "Keyboard",
            totalPrice = 75.50
        )
        newOrderInput = OrderHistory(
            id = null,
            customerName = "Charlie New",
            courierName = "Quick Post",
            deliveryStatus = "NEW",
            productNames = "Monitor",
            totalPrice = 300.0
        )
        savedOrderOutput = OrderHistory(
            id = 3L,
            customerName = "Charlie New",
            courierName = "Quick Post",
            deliveryStatus = "NEW",
            productNames = "Monitor",
            totalPrice = 300.0
        )
    }

    @Test
    fun `GET order by ID should return order when found`() {
        // given
        val orderId = sampleOrder1.id!!
        whenever(orderHistoryService.getOrderById(orderId)).thenReturn(sampleOrder1)

        // when
        mockMvc.perform(
            get("/order/{id}", orderId)
                .accept(MediaType.APPLICATION_JSON)
        )
            // then
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(orderId))
            .andExpect(jsonPath("$.customerName").value(sampleOrder1.customerName))
            .andExpect(jsonPath("$.deliveryStatus").value("PENDING"))

        verify(orderHistoryService).getOrderById(eq(orderId))
        verifyNoMoreInteractions(orderHistoryService)
    }

    @Test
    fun `GET all orders should return list of orders`() {
        // given
        val orders = listOf(sampleOrder1, sampleOrder2)
        whenever(orderHistoryService.getAll()).thenReturn(orders)

        // when
        mockMvc.perform(
            get("/order")
                .accept(MediaType.APPLICATION_JSON)
        )
            // then
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].id").value(sampleOrder1.id!!))
            .andExpect(jsonPath("$[0].customerName").value(sampleOrder1.customerName))
            .andExpect(jsonPath("$[1].id").value(sampleOrder2.id!!))
            .andExpect(jsonPath("$[1].customerName").value(sampleOrder2.customerName))

        verify(orderHistoryService).getAll()
        verifyNoMoreInteractions(orderHistoryService)
    }

    @Test
    fun `GET all orders should return empty list when no orders exist`() {
        // given
        val emptyList = emptyList<OrderHistory>()
        whenever(orderHistoryService.getAll()).thenReturn(emptyList)

        // when
        mockMvc.perform(
            get("/order")
                .accept(MediaType.APPLICATION_JSON)
        )
            // then
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(0))

        verify(orderHistoryService).getAll()
        verifyNoMoreInteractions(orderHistoryService)
    }


    @Test
    fun `POST create order should save and return order`() {
        // given
        whenever(orderHistoryService.saveOrder(any<OrderHistory>())).thenReturn(savedOrderOutput)

        // when
        mockMvc.perform(
            post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newOrderInput))
                .accept(MediaType.APPLICATION_JSON)
        )
            // then
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(savedOrderOutput.id!!))
            .andExpect(jsonPath("$.customerName").value(savedOrderOutput.customerName))
            .andExpect(jsonPath("$.deliveryStatus").value(savedOrderOutput.deliveryStatus))

        val orderCaptor = argumentCaptor<OrderHistory>()
        verify(orderHistoryService).saveOrder(orderCaptor.capture())
        assertEquals(newOrderInput.customerName, orderCaptor.firstValue.customerName)
        assertEquals(newOrderInput.deliveryStatus, orderCaptor.firstValue.deliveryStatus)
        assertEquals(null, orderCaptor.firstValue.id)

        verifyNoMoreInteractions(orderHistoryService)
    }

    @Test
    fun `PUT change status should update status and return order`() {
        // given
        val orderId = sampleOrder1.id!!
        val newStatus = "SHIPPED"
        val statusRequest = StatusChangeRequest(status = newStatus)
        sampleOrder1.deliveryStatus = newStatus
        whenever(orderHistoryService.changeStatus(eq(orderId), eq(newStatus))).thenReturn(sampleOrder1)

        // when
        mockMvc.perform(
            put("/order/status/{id}", orderId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(statusRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
            // then
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(orderId))
            .andExpect(jsonPath("$.deliveryStatus").value(newStatus))
            .andExpect(jsonPath("$.customerName").value(sampleOrder1.customerName))

        verify(orderHistoryService).changeStatus(eq(orderId), eq(newStatus))
        verifyNoMoreInteractions(orderHistoryService)
    }
}