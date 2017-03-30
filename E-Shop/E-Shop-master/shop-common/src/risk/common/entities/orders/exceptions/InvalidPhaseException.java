package risk.common.entities.orders.exceptions;

import risk.common.entities.Turn;
import risk.common.entities.orders.*;

public class InvalidPhaseException extends OrderException {
    public InvalidPhaseException(DeployOrder order) {
        this(order, Turn.Phase.DEPLOY);
    }

    public InvalidPhaseException(AttackOrder order) {
        this(order, Turn.Phase.ATTACK);
    }

    public InvalidPhaseException(FortifyOrder order) {
        this(order, Turn.Phase.FORTIFY);
    }

    private InvalidPhaseException(Order order, Turn.Phase expectedPhase) {
        super(
                order,
                "The turn phase is expected to be '" + expectedPhase + "' but is '" + order.getTurn().getPhase().toString() + "'."
        );
    }
}
