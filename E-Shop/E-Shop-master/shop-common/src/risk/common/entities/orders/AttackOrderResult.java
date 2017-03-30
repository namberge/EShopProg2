package risk.common.entities.orders;

import risk.common.entities.Player;
import risk.common.entities.world.Province;

import java.io.Serializable;
import java.util.List;

public class AttackOrderResult implements Serializable {
	private Player attacker;
	private Player defender;
	private Province defenderProvince;
	private Province attackerProvince;
	private List<Integer> attackerDiceNumbers;
	private List<Integer> defenderDiceNumbers;
	private int attackerUnitsLost;
	private int defenderUnitsLost;
	private boolean isConquered;
	
	public AttackOrderResult(
			Player attacker,
			Player defender,
			Province defenderProvince,
			Province attackerProvince,
			List<Integer> attackerDiceNumbers, 
			List<Integer> defenderDiceNumbers, 
			int attackerUnitsLost, 
			int defenderUnitsLost,
			boolean isConquered) {
		this.attacker = attacker;
		this.defender = defender;
		this.attackerProvince = attackerProvince;
		this.defenderProvince = defenderProvince;
		this.attackerDiceNumbers = attackerDiceNumbers;
		this.defenderDiceNumbers = defenderDiceNumbers;
		this.attackerUnitsLost = attackerUnitsLost;
		this.defenderUnitsLost = defenderUnitsLost;
		this.isConquered = isConquered;
	}
	
	/**
	 * @return the attacker
	 */
	public Player getAttacker() {
		return attacker;
	}
	
	/**
	 * @return the defender
	 */
	public Player getDefender() {
		return defender;
	}
	
	/**
	 * @return the target
	 */
	public Province getDefenderProvince() {
		return defenderProvince;
	}
	
	/**
	 * @return the source
	 */
	public Province getAttackerProvince() {
		return attackerProvince;
	}
	
	/**
	 * @return the attackerDiceNumbers
	 */
	public List<Integer> getAttackerDiceNumbers() {
		return attackerDiceNumbers;
	}
	
	/**
	 * @return the defenderDiceNumbers
	 */
	public List<Integer> getDefenderDiceNumbers() {
		return defenderDiceNumbers;
	}
	
	/**
	 * @return the attackerUnitsLost
	 */
	public int getAttackerUnitsLost() {
		return attackerUnitsLost;
	}
	
	/**
	 * @return the defenderUnitsLost
	 */
	public int getDefenderUnitsLost() {
		return defenderUnitsLost;
	}

	/**
	 * @return true, if the attacker has won the defender province, otherwise false
     */
	public boolean isConquered() {
		return isConquered;
	}
}
