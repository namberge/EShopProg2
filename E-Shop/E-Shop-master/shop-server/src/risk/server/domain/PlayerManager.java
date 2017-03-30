package risk.server.domain;

import java.util.ArrayList;
import java.util.List;

import risk.common.entities.Player;

public class PlayerManager {
	private List<Player> players = new ArrayList<>();
	
	public PlayerManager() {

	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public void addPlayer(Player player) {
		if (players.size() > 0) {
			
			// set next player for last player
			Player lastPlayer = players.get(players.size() -1);
			lastPlayer.setNextPlayer(player);
			
			// set next player for new player
			player.setNextPlayer(players.get(0));
		}
		
		// add new player
		players.add(player);
	}

    public void reset() {
        players.clear();
    }
}
