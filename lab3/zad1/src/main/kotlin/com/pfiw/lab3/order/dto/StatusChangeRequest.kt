package com.pfiw.lab3.order.dto

import com.pfiw.lab3.order.model.DeliveryStatus
import com.pfiw.lab3.order.model.Product

data class StatusChangeRequest(
    val status: DeliveryStatus,
)
