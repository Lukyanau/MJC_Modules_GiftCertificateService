package com.epam.esm.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Table(name = "user_order")
public class Order{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "certificate_id")
    private Long certificateId;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "purchase_time")
    private LocalDateTime purchaseTime;

    public Order(Long userId, Long certificateId, BigDecimal cost, LocalDateTime purchaseTime) {
        this.userId = userId;
        this.certificateId = certificateId;
        this.cost = cost;
        this.purchaseTime = purchaseTime;
    }
}
