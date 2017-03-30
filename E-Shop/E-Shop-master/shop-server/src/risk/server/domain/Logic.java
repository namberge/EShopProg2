package risk.server.domain;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.io.IOException;

import risk.common.entities.orders.AttackOrderResult;
import risk.common.entities.Player;
import risk.common.entities.missions.*;
import risk.common.entities.world.Continent;
import risk.common.entities.world.Province;
import risk.common.entities.Turn;
import risk.common.entities.orders.*;
import risk.common.entities.orders.exceptions.OrderException;
import risk.server.persistence.FilePersistenceManager;
import risk.server.persistence.PersistenceManager;

public class Logic {
	private WorldManager worldManager;
	private PlayerManager playerManager;
	private Turn turn;
	private Player startingPlayer;
	private PersistenceManager pm = new FilePersistenceManager();
	private boolean isOver = false;
	
	public Logic(WorldManager worldManager, PlayerManager playerManager) {
		this.worldManager = worldManager;
		this.playerManager = playerManager;
	}

	public void loadGame(String filename) throws IOException {
		reset();

        pm.openForReading(filename);

		// read players count
		int playersCount = pm.readPlayersCount();
		
		// read players
		for(int i = 0; i < playersCount; i++) {
			Player player = pm.readPlayer(worldManager.getWorld().getProvinces());
			playerManager.addPlayer(player);
		}
	
		// read turn
		turn = pm.readTurn(playerManager.getPlayers());

		pm.close();
	}

	public void saveGame(String filename) throws IOException {
		pm.openForWriting(filename);

		// write players count
		pm.writePlayersCount(playerManager.getPlayers().size());

		// write players
		for(Player player : playerManager.getPlayers()) {
			pm.writePlayer(player);
		}

		// write turn
		pm.writeTurn(turn);

		pm.close();
	}

    public void reset() {
        worldManager.reset();
        playerManager.reset();
        turn = null;
        startingPlayer = null;
    }

	public void prepare() {
        distributeProvinces();
		distributeMissions();
		
		// set first turn
		turn = new Turn(startingPlayer);
	}

    /**
     * Distributes all 42 provinces among all players clockwise.
     */
    private void distributeProvinces() {
        // distribute all 42 province cards among all players clockwise
        Player player = playerManager.getPlayers().get(0);

        // copy list of all provinces and shuffle
        List<Province> provinces = new ArrayList<>(worldManager.getWorld().getProvinces());
        Collections.shuffle(provinces);

        for (Province province : provinces) {
            province.setOwner(player);
            province.setUnitsCount(1);

            // the player next (left) to the current player
            // will receive the next province card
            player = player.getNextPlayer();
        }

        // the player next (left) to the last player
        // that received a province card
        // starts with the first turn
        startingPlayer = player;
    }

    /**
     * Distributes missions. Every player receives one random mission out of 9.
     */
    private void distributeMissions() {
        Continent northAmerica = worldManager.getWorld().getContinentByName("North America");
        Continent southAmerica = worldManager.getWorld().getContinentByName("South America");
        Continent asia = worldManager.getWorld().getContinentByName("Asia");
        Continent australia = worldManager.getWorld().getContinentByName("Australia");
        Continent africa = worldManager.getWorld().getContinentByName("Africa");
        Continent europe = worldManager.getWorld().getContinentByName("Europe");

        List<Mission> missions = new ArrayList<>(9);

        missions.add(new TwoContinentsMission(northAmerica, australia));
        missions.add(new TwoContinentsMission(northAmerica, africa));
        missions.add(new TwoContinentsMission(asia, africa));
        missions.add(new TwoContinentsMission(asia, southAmerica));

        missions.add(new TwoContinentsAndChoiceMission(europe, australia));
        missions.add(new TwoContinentsAndChoiceMission(europe, southAmerica));
        missions.add(new TwoContinentsAndChoiceMission(europe, africa));

        missions.add(new TwentyFourProvincesMission());

        missions.add(new EighteenProvincesMission());

        // shuffle missions cards
        Collections.shuffle(missions);

        for (Player p : playerManager.getPlayers()) {
            Mission m = missions.remove(0);
			p.setMission(m);
			m.setPlayer(p);
        }
    }

	public Turn getTurn() {
		return turn;
	}

	public void nextPhase() {
		// get next player
		Player currentPlayer = turn.getPlayer();
		Player nextPlayer = currentPlayer.getNextPlayer();
		
		switch(turn.getPhase()) {
			case DEPLOY:
				turn.setPhase(Turn.Phase.ATTACK);
				break;
			case ATTACK:
				turn.setPhase(Turn.Phase.FORTIFY);
				break;
			case FORTIFY:
				turn = new Turn(nextPlayer);
				break;
		}
	}
	
	public void deploy(DeployOrder order) throws OrderException {
		order.check();

		// increase target units count
		Province target = order.getTarget();
		target.setUnitsCount(target.getUnitsCount() + order.getUnitsCount());

		// decrease units to deploy count
		turn.setUnitsToDeploy(turn.getUnitsToDeploy() - order.getUnitsCount());

		// progress to next phase if no more units to deploy
		if (turn.getUnitsToDeploy() == 0) {
			nextPhase();
		}
	}

	public AttackOrderResult attack(AttackOrder order) throws OrderException {
		order.check();

		// roll dice
		List<Integer> attackerDiceNumbers = rollDice(order.getAttackerUnitsCount());
		List<Integer> defenderDiceNumbers = rollDice(order.getDefenderUnitsCount());
		int diceToCompare = Math.min(order.getAttackerUnitsCount(), order.getDefenderUnitsCount());
		
		// compare dice to calculate lost units
		int attackerUnitsLost = 0;
		int defenderUnitsLost = 0;
		for (int i = 0; i < diceToCompare; i++) {
			if (attackerDiceNumbers.get(i) > defenderDiceNumbers.get(i)) {
				defenderUnitsLost++;
			} else {
				attackerUnitsLost++;
			}
		}

		// decrease units count in attacker/defender province
		Province attackerProvince = order.getAttackerProvince();
		Province defenderProvince = order.getDefenderProvince();
		attackerProvince.setUnitsCount(attackerProvince.getUnitsCount() - attackerUnitsLost);
		defenderProvince.setUnitsCount(defenderProvince.getUnitsCount() - defenderUnitsLost);

		// if all defender units are destroyed
		boolean isConquered = defenderProvince.getUnitsCount() == 0;
		if (isConquered){
			// the defender province is owned by the attacker
			defenderProvince.setOwner(order.getAttacker());

			// all attacker units that remain move there
			int remainingUnitsCount = order.getAttackerUnitsCount() - attackerUnitsLost;
			defenderProvince.setUnitsCount(remainingUnitsCount);
		}

		// check whether the game is over
		isOver = order.getAttacker().getMission().isComplete();

		// return the attack order result
		return new AttackOrderResult(
				order.getDefender(),
				order.getAttacker(),
				defenderProvince,
				attackerProvince,
				attackerDiceNumbers, 
				defenderDiceNumbers, 
				attackerUnitsLost,
				defenderUnitsLost,
				isConquered);
	}

	public void fortify(FortifyOrder order) throws OrderException {
		order.check();

		// decrease/increase units count in source/target province
		Province source = order.getSource();
		Province target = order.getTarget();
		int unitsCount = order.getUnitsCount();
		source.setUnitsCount(source.getUnitsCount() - unitsCount);
		target.setUnitsCount(target.getUnitsCount() + unitsCount);
	}

	public boolean isOver() {
		return isOver;
	}
	
	private List<Integer> rollDice(int diceCount) {
		List<Integer> results = new ArrayList<>(diceCount);
		for (int i = 0; i < diceCount; i++) {
			// nextInt is normally exclusive of the top value, 
			// so add 1 to make it inclusive
			int result = ThreadLocalRandom.current().nextInt(1, 6 + 1);
			results.add(result);
		}
		Collections.sort(results);
		Collections.reverse(results);
		return results;
	}
}
