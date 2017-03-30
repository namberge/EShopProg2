package risk.common.entities.missions;

import risk.common.entities.Player;

import java.io.Serializable;

public abstract class Mission implements Serializable {
	protected Player player;
	protected String description;

	public abstract boolean isComplete();

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public String getDescription(){
		return description;
	}
}
