package risk.common.network;

import risk.common.entities.Player;
import risk.common.entities.Turn;
import risk.common.entities.orders.*;
import risk.common.entities.orders.exceptions.OrderException;
import risk.common.entities.world.World;

import java.io.IOException;
import java.util.List;

public interface Server {
    void login(Client client);

    void loadGame(String filename) throws IOException;
    void saveGame(String filename) throws IOException;

    void addPlayer(Player player);
    void prepare();

    List<Player> getPlayers();
    World getWorld();

    Turn getTurn();
    void nextPhase();

    void deploy(DeployOrder order) throws OrderException;
    void attack(AttackOrder order) throws OrderException;
    void fortify(FortifyOrder order) throws OrderException;

    boolean isOver();
}
