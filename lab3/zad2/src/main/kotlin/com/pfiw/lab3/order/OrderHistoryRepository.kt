package com.pfiw.lab3.order

import com.pfiw.lab3.order.model.OrderHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderHistoryRepository : JpaRepository<OrderHistory, Long>