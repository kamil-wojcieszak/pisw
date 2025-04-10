package com.pfiw.lab3

import com.pfiw.lab3.order.OrderHistoryRepository
import com.pfiw.lab3.order.OrderHistoryService
import com.pfiw.lab3.order.model.OrderHistory
import jakarta.persistence.EntityNotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.* 
import java.util.* 

@ExtendWith(MockitoExtension::class)
class OrderHistoryServiceTest {

    @Mock
    private lateinit var orderHistoryRepository: OrderHistoryRepository

    @InjectMocks
    private lateinit var orderHistoryService: OrderHistoryService

    @Captor
    private lateinit var orderHistoryCaptor: ArgumentCaptor<OrderHistory>

    
    private lateinit var sampleOrder1: OrderHistory
    private lateinit var sampleOrder2: OrderHistory

    @BeforeEach
    fun setUp() {
        
        sampleOrder1 = OrderHistory(
            id = 1L, 
            customerName = "Alice",
            courierName = "Speedy Courier",
            deliveryStatus = "PENDING",
            productNames = "Laptop, Mouse",
            totalPrice = 1250.99
        )
        sampleOrder2 = OrderHistory(
            id = 2L, 
            customerName = "Bob",
            courierName = "Reliable Delivery",
            deliveryStatus = "PROCESSING",
            productNames = "Keyboard",
            totalPrice = 75.50
        )
    }

    @Test
    fun `getOrderById should return order when found`() {
        
        val orderId = 1L
        val expectedOrder = sampleOrder1 
        whenever(orderHistoryRepository.findById(orderId)).thenReturn(Optional.of(expectedOrder))

        
        val actualOrder = orderHistoryService.getOrderById(orderId)

        
        assertEquals(expectedOrder, actualOrder)
        verify(orderHistoryRepository).findById(orderId)
        verifyNoMoreInteractions(orderHistoryRepository)
    }

    @Test
    fun `getOrderById should throw EntityNotFoundException when not found`() {
        
        val orderId = 99L
        whenever(orderHistoryRepository.findById(orderId)).thenReturn(Optional.empty())

        
        assertThrows(EntityNotFoundException::class.java) {
            orderHistoryService.getOrderById(orderId)
        }
        verify(orderHistoryRepository).findById(orderId)
        verifyNoMoreInteractions(orderHistoryRepository)
    }

    @Test
    fun `changeStatus should update status and save order`() {
        
        val orderId = 1L
        val newStatus = "SHIPPED"
        
        
        val existingOrder = sampleOrder1 

        whenever(orderHistoryRepository.getReferenceById(orderId)).thenReturn(existingOrder)
        
        whenever(orderHistoryRepository.save(any<OrderHistory>())).thenAnswer { invocation ->
            
            invocation.arguments[0] as OrderHistory
        }

        
        val updatedOrder = orderHistoryService.changeStatus(orderId, newStatus)

        
        verify(orderHistoryRepository).getReferenceById(orderId)
        verify(orderHistoryRepository).save(orderHistoryCaptor.capture())
        val savedOrder = orderHistoryCaptor.value

        
        assertEquals(newStatus, savedOrder.deliveryStatus)
        assertEquals(orderId, savedOrder.id)
        assertEquals(sampleOrder1.customerName, savedOrder.customerName) 

        
        assertEquals(newStatus, updatedOrder.deliveryStatus)
        assertEquals(orderId, updatedOrder.id)
        assertEquals(sampleOrder1.customerName, updatedOrder.customerName)

        
        assertEquals(savedOrder, updatedOrder)


        verifyNoMoreInteractions(orderHistoryRepository)
    }


    @Test
    fun `saveOrder should call repository save and return saved order`() {
        
        
        val orderToSave = OrderHistory(
            id = null, 
            customerName = "Charlie",
            courierName = "Quick Post",
            deliveryStatus = "NEW",
            productNames = "Monitor",
            totalPrice = 300.0
        )
        
        val expectedSavedOrder = OrderHistory(
            id = 5L, 
            customerName = "Charlie",
            courierName = "Quick Post",
            deliveryStatus = "NEW",
            productNames = "Monitor",
            totalPrice = 300.0
        )

        whenever(orderHistoryRepository.save(orderToSave)).thenReturn(expectedSavedOrder)

        
        val actualSavedOrder = orderHistoryService.saveOrder(orderToSave)

        
        assertEquals(expectedSavedOrder, actualSavedOrder)
        
        verify(orderHistoryRepository).save(eq(orderToSave))
        verifyNoMoreInteractions(orderHistoryRepository)
    }

    @Test
    fun `getAll should return list of all orders from repository`() {
        
        val expectedOrders = listOf(sampleOrder1, sampleOrder2)
        whenever(orderHistoryRepository.findAll()).thenReturn(expectedOrders)

        
        val actualOrders = orderHistoryService.getAll()

        
        assertEquals(expectedOrders, actualOrders)
        assertEquals(2, actualOrders.size)
        assertEquals("Alice", actualOrders[0].customerName)
        assertEquals("Keyboard", actualOrders[1].productNames)

        verify(orderHistoryRepository).findAll()
        verifyNoMoreInteractions(orderHistoryRepository)
    }

    @Test
    fun `getAll should return empty list when repository has no orders`() {
        
        val expectedOrders = emptyList<OrderHistory>()
        whenever(orderHistoryRepository.findAll()).thenReturn(expectedOrders)

        
        val actualOrders = orderHistoryService.getAll()

        
        assertEquals(expectedOrders, actualOrders)
        assertEquals(0, actualOrders.size)
        verify(orderHistoryRepository).findAll()
        verifyNoMoreInteractions(orderHistoryRepository)
    }
}