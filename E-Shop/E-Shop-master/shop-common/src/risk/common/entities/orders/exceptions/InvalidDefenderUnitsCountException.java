package risk.common.entities.orders.exceptions;

import risk.common.entities.orders.AttackOrder;

public class InvalidDefenderUnitsCountException extends OrderException {
    public InvalidDefenderUnitsCountException(AttackOrder order) {
        super(order, "The defender units count must be either 1 or 2. " + order.getDefenderProvince().getName() + "has got " + order.getDefenderProvince().getUnitsCount() + " units.");
    }
}
