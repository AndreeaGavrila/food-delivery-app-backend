package com.example.fooddelivery.model;

import com.example.fooddelivery.enums.OrderStatus;
import com.example.fooddelivery.enums.PaymentType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "placed_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer number;
    private Double value;
    private Double deliveryTax;
    private LocalDateTime dateTime;
    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Notification> notifications;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> products;

    @ManyToOne
    private ClientUser clientUser;

    @ManyToOne
    private DeliveryUser deliveryUser;

    @ManyToOne
    private UserAddress deliveryAddress;

}
