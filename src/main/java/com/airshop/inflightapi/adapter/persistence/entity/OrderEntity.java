package com.airshop.inflightapi.adapter.persistence.entity;

import com.airshop.inflightapi.domain.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItemEntity> items = new ArrayList<>();

    @Embedded
    @AttributeOverrides({
    @AttributeOverride(name = "totalPrice", column = @Column(name = "payment_total_price")),
    @AttributeOverride(name = "cardToken", column = @Column(name = "payment_card_token")),
    @AttributeOverride(name = "status", column = @Column(name = "payment_status")),
    @AttributeOverride(name = "paymentDate", column = @Column(name = "payment_date")),
    @AttributeOverride(name = "gateway", column = @Column(name = "payment_gateway"))
    })
    private PaymentDetailsEmbeddable paymentDetails;

    private String buyerEmail;
    private String seatLetter;
    private int seatNumber;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
