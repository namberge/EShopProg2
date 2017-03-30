package risk.common.entities;

import java.io.Serializable;

public class Turn implements Serializable {
	private Player player;
	private Phase phase;
	private int unitsToDeploy;

	public Turn(Player player) {
		this(player, Phase.DEPLOY, player.getProvinces().size() / 3);
	}

	public Turn(Player player, Phase phase, int unitsToDeploy) {
		this.player = player;
		this.phase = phase;
		this.unitsToDeploy = unitsToDeploy;
	}
	
	public enum Phase {
//		TRADE,
		DEPLOY,
		ATTACK,
		FORTIFY
	}
	
	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * @return the phase
	 */
	public Phase getPhase() {
		return phase;
	}
	
	/**
	 * @param phase the phase to set
	 */
	public void setPhase(Phase phase) {
		this.phase = phase;
	}

	/**
	 * @return the unitsToDeploy
	 */
	public int getUnitsToDeploy() {
		return unitsToDeploy;
	}
	
	/**
	 * @param unitsToDeploy the unitsToDeploy to decrease
	 */
	public void setUnitsToDeploy(int unitsToDeploy) {
		this.unitsToDeploy = unitsToDeploy;
	}
}
