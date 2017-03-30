package risk.server.persistence;

import risk.common.entities.Turn;
import risk.common.entities.Player;
import risk.common.entities.world.Province;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface PersistenceManager {
    void openForReading(String dataSource) throws IOException;

    void openForWriting(String dataSource) throws IOException;

    void close() throws IOException;

    int readPlayersCount() throws IOException;
    
    Player readPlayer(Set<Province> provinces) throws IOException;

    Turn readTurn(List<Player> players) throws IOException;
    
    void writePlayersCount(int count);
    
    void writePlayer(Player player);
    
    void writeTurn(Turn turn);
    
}
