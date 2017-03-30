package risk.common.entities.orders;

import risk.common.entities.Turn;
import risk.common.entities.world.Province;
import risk.common.entities.orders.exceptions.*;

public class FortifyOrder extends Order {
    private Province source;
    private Province target;
    private int unitsCount;

    public FortifyOrder(Turn turn, Province source, Province target, int unitsCount) {
        super(turn);
        this.source = source;
        this.target = target;
        this.unitsCount = unitsCount;
    }

    //region Methods: Getters / Setters

    public Province getSource() {
        return source;
    }

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
        if (turn.getPhase() != Turn.Phase.FORTIFY) {
            throw new InvalidPhaseException(this);
        }

        // check source
        if (!source.isOwnedBy(turn.getPlayer())) {
            throw new NotOwnedByPlayerException(this, source);
        }

        // check target
        if (!target.isOwnedBy(turn.getPlayer())) {
            throw new NotOwnedByPlayerException(this, target);
        }
        if (!target.isNeighborOf(source)) {
            throw new NotNeighborsException(this);
        }

        // check units count
        if (unitsCount > source.getUnitsCount() - 1) {
            throw new NotEnoughUnitsException(this);
        }
    }

    //endregion
}
