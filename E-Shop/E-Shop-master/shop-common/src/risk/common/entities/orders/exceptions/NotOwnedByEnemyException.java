package risk.common.entities.orders.exceptions;

import risk.common.entities.orders.AttackOrder;

public class NotOwnedByEnemyException extends OrderException {
    public NotOwnedByEnemyException(AttackOrder order) {
        super(
                order,
                "The province " + order.getDefenderProvince().getCode() + " is not owned by an enemy of " + order.getTurn().getPlayer().getName() + "."
        );
    }
}
