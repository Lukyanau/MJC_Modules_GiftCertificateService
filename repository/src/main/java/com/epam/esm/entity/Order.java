package com.epam.esm.entity;

import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Audited
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Table(name = "user_order")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "user_name")
  private String userName;

  @Column(name = "certificates")
  private String certificates;

  @Column(name = "cost")
  private BigDecimal cost;

  @Column(name = "purchase_time")
  private LocalDateTime purchaseTime;

  public Order(
      Long userId,
      String userName,
      String certificates,
      BigDecimal cost,
      LocalDateTime purchaseTime) {
    this.userId = userId;
    this.userName = userName;
    this.certificates = certificates;
    this.cost = cost;
    this.purchaseTime = purchaseTime;
  }
}
