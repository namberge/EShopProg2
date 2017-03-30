package risk.common.entities.orders;

import risk.common.entities.Player;
import risk.common.entities.Turn;
import risk.common.entities.world.Province;
import risk.common.entities.orders.exceptions.*;

public class AttackOrder extends Order {
    private Player attacker;
    private Province attackerProvince;
    private int attackerUnitsCount;
    private Player defender;
    private Province defenderProvince;
    private int defenderUnitsCount;

    public AttackOrder(
            Turn turn,
            Province attackerProvince,
            int attackerUnitsCount,
            Province defenderProvince,
            int defenderUnitsCount) {
        super(turn);
        this.attacker = attackerProvince.getOwner();
        this.attackerProvince = attackerProvince;
        this.attackerUnitsCount = attackerUnitsCount;
        this.defender = defenderProvince.getOwner();
        this.defenderProvince = defenderProvince;
        this.defenderUnitsCount = defenderUnitsCount;
    }

    //region Methods: Getters / Setters

    public Player getAttacker() {
        return attacker;
    }

    public Province getAttackerProvince() {
        return attackerProvince;
    }

    public int getAttackerUnitsCount() {
        return attackerUnitsCount;
    }

    public Player getDefender() {
        return defender;
    }

    public Province getDefenderProvince() {
        return defenderProvince;
    }

    public int getDefenderUnitsCount() {
        return defenderUnitsCount;
    }

    //endregion

    //region Methods: inherited

    @Override
    public void check() throws OrderException {
        // check turn phase
        if (turn.getPhase() != Turn.Phase.ATTACK) {
            throw new InvalidPhaseException(this);
        }

        // check attacker province
        if (!attackerProvince.isOwnedBy(turn.getPlayer())) {
            throw new NotOwnedByPlayerException(this);
        }
        if (!attackerProvince.isNeighborOf(defenderProvince)) {
            throw new NotNeighborsException(this);
        }

        // check attacker units count
        if (attackerUnitsCount > attackerProvince.getUnitsCount() - 1) {
            throw new NotEnoughUnitsException(this, attackerProvince, attackerUnitsCount);
        }
        if (attackerUnitsCount < 1 || attackerUnitsCount > 3) {
            throw new InvalidAttackerUnitsCountException(this);
        }

        // check defender province
        if (defenderProvince.isOwnedBy(turn.getPlayer())) {
            throw new NotOwnedByEnemyException(this);
        }

        // check defender units count
        if (defenderUnitsCount > defenderProvince.getUnitsCount()) {
            throw new InvalidDefenderUnitsCountException(this);
        }
        if (defenderUnitsCount < 1 || defenderUnitsCount > 2) {
            throw new InvalidDefenderUnitsCountException(this);
        }
    }

    //endregion
}
