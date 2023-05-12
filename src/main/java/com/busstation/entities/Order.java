package com.busstation.entities;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.busstation.enums.TripStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_order")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Order  implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "order_id", length = 15, nullable = false)
    private String orderID;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Date createAt;
  
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @Column(name = "is_send_mail", columnDefinition = "boolean default false")
    private boolean isSendMail;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "paymentId")
    @JsonFormat
    private PaymentMethod paymentMethod;

}
