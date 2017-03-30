package risk.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.root1.simon.Registry;
import de.root1.simon.Simon;
import de.root1.simon.annotation.SimonRemote;
import de.root1.simon.exceptions.NameBindingException;
import risk.common.entities.Player;
import risk.common.entities.Turn;
import risk.common.entities.orders.AttackOrder;
import risk.common.entities.orders.DeployOrder;
import risk.common.entities.orders.FortifyOrder;
import risk.common.entities.orders.exceptions.OrderException;
import risk.common.entities.world.World;
import risk.common.network.Client;
import risk.common.network.Server;
import risk.server.domain.Logic;
import risk.server.domain.PlayerManager;
import risk.server.domain.WorldManager;

@SimonRemote(value = {Server.class})
public class ServerImpl implements Server {
    private WorldManager worldManager;
    private PlayerManager playerManager;
    private Logic logic;

    private List<Client> clients = new ArrayList<>(5);

    public static void main(String[] args) throws IOException, NameBindingException {
        ServerImpl serverImpl = new ServerImpl();

        Registry registry = Simon.createRegistry(22222);
        registry.start();
        registry.bind("server", serverImpl);

        //registry.unbind("server");
        //registry.stop();
    }

    public ServerImpl() {
        worldManager = new WorldManager();
        playerManager = new PlayerManager();
        logic = new Logic(worldManager, playerManager);
    }

    @Override
    public void login(Client client) {
        clients.add(client);
    }

    @Override
    public void loadGame(String filename) throws IOException {
        logic.loadGame(filename);
        clients.forEach(Client::update);
    }

    @Override
    public void saveGame(String filename) throws IOException {
        logic.saveGame(filename);
        clients.forEach(Client::update);
    }

    @Override
    public void addPlayer(Player player) {
        playerManager.addPlayer(player);
        clients.forEach(Client::update);
    }

    @Override
    public void prepare() {
        logic.prepare();
        clients.forEach(Client::update);
    }

    @Override
    public List<Player> getPlayers() {
        return playerManager.getPlayers();
    }

    @Override
    public World getWorld() {
        return worldManager.getWorld();
    }

    @Override
    public Turn getTurn() {
        return logic.getTurn();
    }

    @Override
    public void nextPhase() {
        logic.nextPhase();
        clients.forEach(Client::update);
    }

    @Override
    public void deploy(DeployOrder order) throws OrderException {
        // fix object references (RMI)
        order = new DeployOrder(
                logic.getTurn(),
                worldManager.getWorld().getProvinceByCode(order.getTarget().getCode()),
                order.getUnitsCount());

        logic.deploy(order);
        clients.forEach(Client::update);
    }

    @Override
    public void attack(AttackOrder order) throws OrderException {
        // fix object references (RMI)
        order = new AttackOrder(
                logic.getTurn(),
                worldManager.getWorld().getProvinceByCode(order.getAttackerProvince().getCode()),
                order.getAttackerUnitsCount(),
                worldManager.getWorld().getProvinceByCode(order.getDefenderProvince().getCode()),
                order.getDefenderUnitsCount());

        logic.attack(order);
        clients.forEach(Client::update);
    }

    @Override
    public void fortify(FortifyOrder order) throws OrderException {
        // fix object references (RMI)
        order = new FortifyOrder(
                logic.getTurn(),
                worldManager.getWorld().getProvinceByCode(order.getSource().getCode()),
                worldManager.getWorld().getProvinceByCode(order.getTarget().getCode()),
                order.getUnitsCount());

        logic.fortify(order);
        clients.forEach(Client::update);
    }

    @Override
    public boolean isOver() {
        return logic.isOver();
    }
}
