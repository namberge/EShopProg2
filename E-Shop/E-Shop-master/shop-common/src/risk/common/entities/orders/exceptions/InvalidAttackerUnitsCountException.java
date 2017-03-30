package risk.common.entities.orders.exceptions;

import risk.common.entities.orders.AttackOrder;

public class InvalidAttackerUnitsCountException extends OrderException {
    public InvalidAttackerUnitsCountException(AttackOrder order) {
        super(order, "The attacker units count must be either 1, 2 or 3.");
    }
}
