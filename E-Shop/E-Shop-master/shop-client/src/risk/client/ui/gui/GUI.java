package risk.client.ui.gui;

import de.root1.simon.Lookup;
import de.root1.simon.Simon;
import de.root1.simon.annotation.SimonRemote;
import de.root1.simon.exceptions.EstablishConnectionFailed;
import de.root1.simon.exceptions.LookupFailedException;
import risk.common.network.Client;
import risk.common.network.Server;
import risk.common.entities.Player;
import risk.common.entities.orders.AttackOrder;
import risk.common.entities.orders.DeployOrder;
import risk.common.entities.orders.FortifyOrder;
import risk.common.entities.orders.Order;
import risk.common.entities.orders.exceptions.OrderException;
import risk.client.ui.gui.file.*;
import risk.client.ui.gui.map.*;
import risk.client.ui.gui.mission.*;
import risk.client.ui.gui.order.*;
import risk.client.ui.gui.status.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.UnknownHostException;

@SimonRemote(value = {Client.class})
public class GUI implements Client {
    private Server server;
    private Lookup nameLookup;

    private JFrame frame;
    private MapPanel mapPanel;
    private StatusPanel statusPanel;
    private OrderPanel orderPanel;
    private MissionPanel missionPanel;
    private FilePanel filePanel;

    public GUI() {
        try {
            connect();
        } catch (UnknownHostException e) {
            e.printStackTrace(); // TODO
        } catch (EstablishConnectionFailed e) {
            e.printStackTrace(); // TODO
        } catch (LookupFailedException e) {
            e.printStackTrace(); // TODO
        }

        frame = new JFrame("Risk");

        // closing behavior
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new CloseWindowListener());

        //region create panels, add listeners

        mapPanel = new MapPanel(server);
        mapPanel.addProvinceClickListener(new MapPanelListener());

        statusPanel = new StatusPanel(server);

        orderPanel = new OrderPanel(server);
        orderPanel.addOrderIssueListener(new OrderPanelListener());

        missionPanel = new MissionPanel(server);

        filePanel = new FilePanel(server);
        filePanel.getPlayButton().addActionListener(new PlayButtonListener());
        filePanel.getSaveButton().addActionListener(new SaveButtonListener());
        filePanel.getLoadButton().addActionListener(new LoadButtonListener());
        filePanel.getExitButton().addActionListener(new ExitButtonListener());

        //endregion

        //region layout frame

        GroupLayout layout = new GroupLayout(frame.getContentPane());
        frame.setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup()
                        .addComponent(mapPanel)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(statusPanel)
                                .addComponent(orderPanel)
                                .addComponent(missionPanel)
                                .addComponent(filePanel))
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(mapPanel)
                        .addGroup(layout.createParallelGroup()
                                .addComponent(statusPanel)
                                .addComponent(orderPanel)
                                .addComponent(missionPanel)
                                .addComponent(filePanel))
        );

        //endregion

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        addPlayer();
    }

    private void connect() throws UnknownHostException, EstablishConnectionFailed, LookupFailedException {
        nameLookup = Simon.createNameLookup("127.0.0.1", 22222);
        server = (Server) nameLookup.lookup("server");
    }

    private void addPlayer() {
        server.login(this);

        String name = askPlayerName();
        Player.Color color = askPlayerColor();

        frame.setTitle("Risk: " + name);
        server.addPlayer(new Player(name, color));
    }

    private String askPlayerName() {
        String name = "";
        while (name.isEmpty()){
            name = JOptionPane.showInputDialog(frame, "Enter player name:", "Add player", JOptionPane.QUESTION_MESSAGE);
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter a player name that is not empty.");
            }
        }
        return name;
    }

    private Player.Color askPlayerColor() {
        Player.Color color = null;
        while (color == null){
            String colorName = JOptionPane.showInputDialog(frame, "Enter player color:", "Add player", JOptionPane.QUESTION_MESSAGE);
            try {
                color = Player.Color.valueOf(colorName);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, "Enter a valid player color name.");
            }
        }
        return color;
    }

    private void disconnect() {
        nameLookup.release(server);
    }

    @Override
    public void update() {
        mapPanel.refresh();
        statusPanel.refresh();
        orderPanel.refresh();
        missionPanel.refresh();

        SwingUtilities.updateComponentTreeUI(frame);
    }

    class MapPanelListener implements ProvinceClickListener {
        @Override
        public void provinceClicked(ProvinceClickEvent e) {
            orderPanel.selectProvince(e.getProvince());
        }
    }

    class OrderPanelListener implements OrderIssueListener {
        @Override
        public void orderIssued(OrderIssueEvent e) {
            if (e.getOrder() == null) {
                server.nextPhase();
            }
            try {
                Order o = e.getOrder();
                if (o instanceof DeployOrder) {
                    server.deploy((DeployOrder)o);
                } else if (o instanceof AttackOrder) {
                    server.attack((AttackOrder)o);
                } else if (o instanceof FortifyOrder) {
                    server.fortify((FortifyOrder)o);
                }
            } catch (OrderException ex) {
                JOptionPane.showMessageDialog(frame,
                        ex.getMessage(),
                        "Invalid order",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }

    class PlayButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            server.prepare();
        }
    }

    class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                server.saveGame("save/default.txt");
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(frame,
                        "Please check for existence of save/default.txt",
                        "File not found",
                        JOptionPane.ERROR_MESSAGE);
                e1.printStackTrace();
            }
        }
    }

    class LoadButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                server.loadGame("save/default.txt");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    class ExitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            exit();
        }
    }

    class CloseWindowListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent windowEvent) {
            exit();
        }
    }

    private void exit() {
        disconnect();
        System.exit(0);
    }
}
