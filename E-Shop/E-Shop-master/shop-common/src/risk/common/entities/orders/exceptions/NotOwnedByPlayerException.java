package risk.common.entities.orders.exceptions;

import risk.common.entities.world.Province;
import risk.common.entities.orders.*;

public class NotOwnedByPlayerException extends OrderException {
    public NotOwnedByPlayerException(DeployOrder order) {
        this(order, order.getTarget());
    }

    public NotOwnedByPlayerException(AttackOrder order) {
        this(order, order.getAttackerProvince());
    }

    public NotOwnedByPlayerException(FortifyOrder order, Province province) {
        this((Order)order, province);
    }

    private NotOwnedByPlayerException(Order order, Province province) {
        super(
                order,
                "The province " + province.getCode() + " is not owned by " + order.getTurn().getPlayer().getName() + "."
        );
    }
}
