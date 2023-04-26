package com.busstation.event;

import com.busstation.entities.Order;
import com.busstation.entities.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class SubmitOrderCompleteEvent extends ApplicationEvent {
    private User user;
    private Order order;

    public SubmitOrderCompleteEvent(User user, Order order) {
        super(user);
        this.user = user;
        this.order = order;
    }
}
