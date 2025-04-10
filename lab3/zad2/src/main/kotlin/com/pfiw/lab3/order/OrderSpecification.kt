package com.pfiw.lab3.order

import com.pfiw.lab3.order.model.OrderHistory
import jakarta.persistence.criteria.Predicate
import org.springframework.data.jpa.domain.Specification
import org.springframework.util.StringUtils

object OrderSpecifications {

    fun byCriteria(
        customerName: String?,
        deliveryStatus: String?,
        minTotalPrice: Double?
    ): Specification<OrderHistory> {
        return Specification { root, _, criteriaBuilder ->
            val predicates = mutableListOf<Predicate>()

            if (StringUtils.hasText(customerName)) {
                predicates.add(
                    criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("customerName")),
                        "%${customerName!!.lowercase()}%"
                    )
                )
            }

            if (StringUtils.hasText(deliveryStatus)) {
                predicates.add(
                    criteriaBuilder.equal(
                        criteriaBuilder.lower(root.get("deliveryStatus")),
                        deliveryStatus!!.lowercase()
                    )
                )
            }

            minTotalPrice?.let {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("totalPrice"), it))
            }
            criteriaBuilder.and(*predicates.toTypedArray())
        }
    }
}