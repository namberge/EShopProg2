package risk.common.entities.orders.exceptions;

import risk.common.entities.world.Province;
import risk.common.entities.orders.*;

public class NotEnoughUnitsException extends OrderException {
    public NotEnoughUnitsException(AttackOrder order, Province province, int unitsCount) {
        this((Order)order, province, unitsCount);
    }

    public NotEnoughUnitsException(FortifyOrder order) {
        this(order, order.getSource(), order.getUnitsCount());
    }

    private NotEnoughUnitsException(Order order, Province province, int unitsCount) {
        super(
                order,
                "Not enough units are available in " + province.getCode() + " to move " + unitsCount + " units from there. At least 1 unit has to remain."
        );
    }
}
