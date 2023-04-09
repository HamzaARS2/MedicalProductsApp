package com.ars.data.network.model

import java.math.BigDecimal

data class NetworkOrderDetails(
    val trackNumber: String,
    val status: String,
    val estimatedDeliveryDate: String,
    val deliveryAddress: NetworkAddress,
    val paymentInfo: String,
    val totalPrice: BigDecimal,
    val orderItems: List<NetworkOrderItem>
)

//private String trackNumber;
//private String status;
//private LocalDate estimatedDeliveryDate;
//private Address deliveryAddress;
//private String paymentInfo;
//private BigDecimal totalPrice;
//private List<OrderItem> orderItems;