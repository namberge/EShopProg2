package risk.common.entities.orders.exceptions;

import risk.common.entities.orders.DeployOrder;

public class InvalidDeployUnitsCountException extends OrderException {
    public InvalidDeployUnitsCountException(DeployOrder order) {
        super(
                order,
                order.getUnitsCount() + " units cannot be deployed. Only 1 to " + order.getTurn().getUnitsToDeploy() + " units can be deployed."
        );
    }
}
