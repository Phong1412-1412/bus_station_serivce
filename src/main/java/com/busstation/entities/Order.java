package com.busstation.entities;


import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

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
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "order_id", length = 36, nullable = false)
    private String orderID;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp //Annotation of Hibernate to automatically save the current time when the object is created
    private Date createAt;
  
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
