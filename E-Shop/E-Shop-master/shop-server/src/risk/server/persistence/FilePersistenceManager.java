package risk.server.persistence;

import risk.common.entities.Turn;
import risk.common.entities.Player;
import risk.common.entities.world.Province;

import java.io.*;
import java.util.List;
import java.util.Set;

public class FilePersistenceManager implements PersistenceManager {
    private BufferedReader reader;
    private PrintWriter writer;

    @Override
    public void openForReading(String filename) throws IOException {
        reader = new BufferedReader(new FileReader(filename));
    }

    @Override
    public void openForWriting(String filename) throws IOException {
        writer = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
    }

    @Override
    public void close() throws IOException {
        if (reader != null) {
            reader.close();
        }
        if (writer != null) {
            writer.close();
        }
    }

    @Override
    public int readPlayersCount() throws IOException {
        int count = Integer.parseInt(reader.readLine());
        reader.readLine();
        return count;
    }
    
    @Override
    public Player readPlayer(Set<Province> provinces) throws IOException {
        // read name
        String name = reader.readLine();

        // read color
        Player.Color color = Player.Color.valueOf(reader.readLine());

        Player player = new Player(name, color);

        // read provinces (and units count each)
        String line;
        while(!(line = reader.readLine()).isEmpty()) {
            // read province pair (code & units count)
            String[] pair = line.split("\\s+"); // split by space character
            String code = pair[0];
            int unitsCount = Integer.parseInt(pair[1]);

            // find province object by code
            Province province = null;
            for (Province p : provinces) {
                if (p.getCode().equals(code)) {
                    province = p;
                    break;
                }
            }
            if (province == null) {
                // TODO: exception
            }

            // set owner
            province.setOwner(player);
            province.setUnitsCount(unitsCount);
        }

        return player;
    }
    
    @Override
    public Turn readTurn(List<Player> players) throws IOException {
        // read player name
        String playerName = reader.readLine();

        // find player object by name
        Player player = null;
        for (Player p : players) {
            if (p.getName().equals(playerName)) {
                player = p;
                break;
            }
        }
        if (player == null) {
            // TODO: exception
        }


        // read phase
        Turn.Phase phase = Turn.Phase.valueOf(reader.readLine());

        int unitsToDeploy = Integer.parseInt(reader.readLine());

        Turn turn = new Turn(player, phase, unitsToDeploy);
        return turn;
    }
   
    @Override
    public void writePlayersCount(int count)	{
    	 //count
    	 writer.println(count);
         writer.println();
    }
    
    @Override
    public void writePlayer(Player player) {
        // write name
        writer.println(player.getName().toString());

        // write color
        writer.println(player.getColor().toString());

        // write provinces
        for(Province province : player.getProvinces()) {
            writer.println(province.getCode() + " " + province.getUnitsCount());
        }

        // write delimiter (empty line)
        writer.println();
    }

    @Override
    public void writeTurn(Turn turn) {
        // write name
        writer.println(turn.getPlayer().getName());

        // write phase
        writer.println(turn.getPhase());

        //write units to deploy
        writer.println(turn.getUnitsToDeploy());

        // write delimiter (empty line)
        writer.println();
    }
}
