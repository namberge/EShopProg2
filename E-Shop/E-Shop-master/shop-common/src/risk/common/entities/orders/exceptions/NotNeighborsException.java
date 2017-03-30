package risk.common.entities.orders.exceptions;

import risk.common.entities.world.Province;
import risk.common.entities.orders.*;

public class NotNeighborsException extends OrderException {
    public NotNeighborsException(AttackOrder order) {
        this(order, order.getAttackerProvince(), order.getDefenderProvince());
    }

    public NotNeighborsException(FortifyOrder order) {
        this(order, order.getSource(), order.getTarget());
    }

    private NotNeighborsException(Order order, Province province1, Province province2) {
        super(
                order,
                "Provinces " + province1.getCode() + " and " + province2.getCode() + " are not neighbors."
        );
    }
}
