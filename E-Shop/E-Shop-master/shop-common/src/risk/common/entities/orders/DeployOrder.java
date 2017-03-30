package risk.common.entities.orders;

import risk.common.entities.Turn;
import risk.common.entities.world.Province;
import risk.common.entities.orders.exceptions.*;

public class DeployOrder extends Order {
    private Province target;
    private int unitsCount;

    public DeployOrder(Turn turn, Province target, int unitsCount) {
        super(turn);
        this.target = target;
        this.unitsCount = unitsCount;
    }

    //region Methods: Getters / Setters

    public Province getTarget() {
        return target;
    }

    public int getUnitsCount() {
        return unitsCount;
    }

    //endregion

    //region Methods: inherited

    @Override
    public void check() throws OrderException {
        // check turn phase
        if (turn.getPhase() != Turn.Phase.DEPLOY) {
            throw new InvalidPhaseException(this);
        }

        // check target
        if (!target.isOwnedBy(turn.getPlayer())) {
            throw new NotOwnedByPlayerException(this);
        }

        // check units count
        if (unitsCount < 1 || unitsCount > turn.getUnitsToDeploy()) {
            throw new InvalidDeployUnitsCountException(this);
        }
    }

    //endregion
}
