package risk.client.ui.gui.order;

import risk.common.entities.orders.Order;

public class OrderIssueEvent {
    private Order order;

    public OrderIssueEvent(Order order) {
        this.order = order;
    }

    /**
     * @return the order
     */
    public Order getOrder() {
        return order;
    }
}
