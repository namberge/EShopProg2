package risk.common.entities.orders.exceptions;

import risk.common.entities.orders.Order;

import java.io.Serializable;

public abstract class OrderException extends Exception implements Serializable {
    private Order order;

    public OrderException(Order order, String message) {
        super(message);
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}
