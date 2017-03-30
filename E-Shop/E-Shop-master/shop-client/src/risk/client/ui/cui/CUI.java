package risk.client.ui.cui;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import de.root1.simon.Lookup;
import de.root1.simon.Simon;
import de.root1.simon.annotation.SimonRemote;
import de.root1.simon.exceptions.EstablishConnectionFailed;
import de.root1.simon.exceptions.LookupFailedException;
import risk.common.entities.Turn;
import risk.common.entities.orders.AttackOrderResult;
import risk.common.entities.Player;
import risk.common.entities.world.Province;
import risk.common.entities.orders.*;
import risk.common.entities.orders.exceptions.OrderException;
import risk.common.network.Client;
import risk.common.network.Server;

@SimonRemote(value = {Client.class})
public class CUI implements Client {
	//region Fields

    private Scanner sc = new Scanner(System.in);
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	private Server server;
	private Lookup nameLookup;

	private Turn turn;
	private Player currentPlayer;

	private String defaultSaveFilename = "default";

    //endregion

	public CUI() {
		
	}

	@Override
	public void update() {

	}
	
	public void run() throws IOException {
		try {
			connect();
		} catch (UnknownHostException e) {
			e.printStackTrace(); // TODO
		} catch (EstablishConnectionFailed e) {
			e.printStackTrace(); // TODO
		} catch (LookupFailedException e) {
			e.printStackTrace(); // TODO
		}

		if (!loadGame()) {
			prepareGame();
		}
		playGame();
	}

	private void connect() throws UnknownHostException, EstablishConnectionFailed, LookupFailedException {
		nameLookup = Simon.createNameLookup("127.0.0.1", 22222);
		server = (Server) nameLookup.lookup("server");
		server.login(this);
	}

	private void disconnect() {
		nameLookup.release(server);
	}

	private boolean loadGame() throws IOException {

		// ask for action
		System.out.println("Do you want to load a previous game? y/n");
		if (!askYesNo()) {
			return false;
		}

		boolean saveNameIsValid = false;

		while (!saveNameIsValid) {

			// ask for filename
			System.out.println("Please enter the filename: [" + defaultSaveFilename + "]");
			String filename = "save/" + br.readLine() + ".txt";
			filename = filename.isEmpty() ? defaultSaveFilename : filename;

			// load
			try {
				server.loadGame(filename);
				saveNameIsValid = true;
			} catch (FileNotFoundException e) {
				System.out.println("Error: No such file exists.");
				saveNameIsValid = false;
			}
		}
		return true;
	}

	private void prepareGame() throws IOException {
		// ask for number of player
		System.out.println("Enter the number of players (2 - 5):");
		int playersCount = askNumber();

		for (int i = 1; i <= playersCount; i++) {
			// ask for player name
			System.out.println("Enter the name for player " + i + ":");
			String name = askPlayerName();

			// ask for player color
			System.out.println("Enter the color name for player " + i + " (PURPLE, BLUE, RED, GREEN, YELLOW, BROWN):");
			Player.Color color = askPlayerColor();
			
			// add player
			server.addPlayer(new Player(name, color));
		}

		server.prepare();
	}
	
	private void playGame() throws IOException {
		System.out.println("-------------------------------------------------------");
		System.out.println("The match begins");

		while(!server.isOver()) {
			turn = server.getTurn();
			if (!turn.getPlayer().equals(currentPlayer)) {
				currentPlayer = turn.getPlayer();
				System.out.println("It is " + currentPlayer.getName() + "'s turn!");
			}

			System.out.println(currentPlayer.getName() + " got following provinces: ");
			System.out.println(provincesCodesToString(currentPlayer.getProvinces()));

			switch (turn.getPhase()) {
				case DEPLOY:
					playPhaseDeploy();
					break;
				case ATTACK:
					playPhaseAttack();
					break;
				case FORTIFY:
					playPhaseFortify();
					break;
			}

			saveGame();
		}
		System.out.println(turn.getPlayer() + " has gained world domination !");
	}

	private void saveGame() throws IOException {
		// ask for action
		System.out.println("Do you want to save the server? y/n (Cancel with Enter)");
		if (!askYesNoDefaultNo()) {
			return;
		}

		// ask for filename
		System.out.println("Please enter the filename: [" + defaultSaveFilename + "]");
		String filename = "save/" + br.readLine() + ".txt";
		filename = filename.isEmpty() ? defaultSaveFilename : filename;

		// save
		server.saveGame(filename);
	}
	
	private void playPhaseDeploy() throws IOException {
        boolean isValid = false;
        while(!isValid) {
            // ask for target province
            System.out.println("Where would you like to deploy some of your " + turn.getUnitsToDeploy() + " units?");
            Province target = askProvince();

            // ask for units count
            System.out.println("How many of your " + turn.getUnitsToDeploy() + " units would you like to deploy to " + target.getName() + "?");
            int unitsCount = askNumber();

            // issue order
            try {
                server.deploy(new DeployOrder(turn, target, unitsCount));
                isValid = true;
            } catch (OrderException e) {
                System.out.println(e.getMessage());
            }
        }

		// display info
		if (turn.getUnitsToDeploy() > 0) {
			System.out.println("You have " + turn.getUnitsToDeploy() + " units left to deploy.");
		}
	}
	
	private void playPhaseAttack() throws IOException {
        AttackOrderResult result = null;

		boolean isValid = false;
        while(!isValid) {
            // ask for action
            System.out.println("Do you want to attack a province? y/n");
            if (!askYesNo()) {
                server.nextPhase();
                return;
            }

            // ask for attacker province
            System.out.println("From where would you like to attack?");
            Province attackerProvince = askProvince();

            // ask for attacker units count
            System.out.println("With how many of " + attackerProvince.getName() + "'s " + attackerProvince.getUnitsCount() + " units would you like to attack? (1 - 3)");
            System.out.println("One unit always has to remain in your province.");
            int attackerUnitsCount = askNumber();

            // ask for defender province
            System.out.println("Which province would you like to attack?");
            Province defenderProvince = askProvince();

            // ask for defender units count
            int defenderUnitsCount;
            if (defenderProvince.getUnitsCount() > 1) {
                System.out.println("Defender, would you like to defend with one or two of " + defenderProvince.getName() + "'s " + defenderProvince.getUnitsCount() + " units?");
                defenderUnitsCount = askNumber();
            } else {
                defenderUnitsCount = 1;
            }

            // issue order
            try {
				//result = server.attack(new AttackOrder(turn, attackerProvince, attackerUnitsCount, defenderProvince, defenderUnitsCount));
				server.attack(new AttackOrder(turn, attackerProvince, attackerUnitsCount, defenderProvince, defenderUnitsCount));
                isValid = true;
            } catch (OrderException e) {
                System.out.println(e.getMessage());
            }
        }

		// display info
		//System.out.println("Attacker province: " + result.getAttackerProvince().getName() + "has lost " + result.getAttackerUnitsLost() + " units.");
		//System.out.println("Defender province: " + result.getDefenderProvince().getName() + "has lost " + result.getDefenderUnitsLost() + " units.");
		//if(result.isConquered()) {
		//	System.out.println(result.getAttacker() + " has conquered " + result.getDefenderProvince() + " from " + result.getAttackerProvince());
		//}
	}
	
	private void playPhaseFortify() throws IOException {
        boolean isValid = false;
        while (!isValid) {
            // ask for action
            System.out.println("Do you want to fortify a province? y/n");
            if (!askYesNo()) {
                server.nextPhase();
                return;
            }

            // ask for source
            System.out.println("From where do you want to move your units?");
            Province source = askProvince();

            // ask for target
            System.out.println("Where do you want your units to move to?");
            Province target = askProvince();

            // ask for units count
            System.out.println("How many units do you want to move?");
            int unitsCount = askNumber();

            // issue order
            try {
                server.fortify(new FortifyOrder(turn, source, target, unitsCount));
				isValid = true;
            } catch (OrderException e) {
                System.out.println(e.getMessage());
            }
        }
	}

    private String askPlayerName() throws IOException {
        String name = br.readLine();
        while (name.isEmpty()){
            System.out.println("Enter a player name that is not empty.");
            name = br.readLine();
        }
        return name;
    }

    private Player.Color askPlayerColor() throws IOException {
        Player.Color color = null;
        while (color == null){
            String colorName = br.readLine();
            try {
                color = Player.Color.valueOf(colorName);
            } catch (IllegalArgumentException e) {
                System.out.println("Enter a valid player color name.");
            }
        }
        return color;
    }

    private int askNumber() {
        int number = 0;
        boolean isValid = false;
        while (!isValid) {
            try {
                number = sc.nextInt();
                isValid = true;
            } catch (InputMismatchException e) {
                System.out.println("Enter a number.");
                sc.next(); // discards invalid input ("consumes the invalid token")
            }
        }
        return number;
    }

    private Province askProvince() throws IOException {
        Province province = null;
        while(province == null) {
            String provinceCode = br.readLine();
            province = server.getWorld().getProvinceByCode(provinceCode);
            if(province == null) {
                System.out.println("Enter the code of a province.");
            }
        }
        return province;
    }

	private boolean askYesNo() throws IOException {
		String s = br.readLine();
		while (!s.equals("y") && !s.equals("n")) {
			System.out.println("Type either 'y' or 'n'.");
			s = br.readLine();
		}
		return s.equals("y");
	}

	private boolean askYesNoDefaultNo() throws IOException {
		String s = br.readLine();
		while (!s.equals("y") && !s.equals("n") && !s.equals("")) {
			System.out.println("Either type 'y' for yes or type 'n' or press enter for no.");
			s = br.readLine();
		}
		return s.equals("y");
	}

	private String provincesCodesToString(List<Province> provinces){
		String string = "";
		for (Province province : provinces){
			string += province.getCode() + ", ";
		}
		return string;
	}
	
	private String provincesNamesToString(List<Province> provinces){
		String string = "";
		for (Province province : provinces){
			string += province.getName() + ", ";
		}
		return string;
	}
}