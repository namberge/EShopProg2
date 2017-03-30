package risk.client.ui.gui.status;

import risk.common.network.Server;
import risk.common.entities.Player;
import risk.common.entities.Turn;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class StatusPanel extends JPanel {
    private Server server;
    private JPanel phasesPanel = new JPanel(new GridLayout(5, 1));
    private JPanel playersPanel = new JPanel(new GridLayout(5, 1));

    public StatusPanel(Server server) {
        // TODO: setLayout via super()
        this.server = server;

        setPreferredSize(new Dimension(250, 150)); // TODO
        setMinimumSize(new Dimension(50, 100)); // TODO

        add(phasesPanel);
        add(playersPanel);

        refresh();
    }

    /**
     * Refreshes the panel.
     */
    public void refresh() {
        // phasesPanel
        phasesPanel.removeAll();
        phasesPanel.add(new JLabel("Current phase:"));
        for (Turn.Phase phase : Turn.Phase.values()) {
            JLabel label = new JLabel(phase.toString());
            label.setOpaque(true);
            if (server.getTurn() != null && phase == server.getTurn().getPhase()) {
                label.setBackground(Color.GREEN);
            }
            phasesPanel.add(label);
        }

        // playersPanel
        playersPanel.removeAll();
        playersPanel.add(new JLabel("Current player:"));
        for (Player player : server.getPlayers()) {
            boolean isCurrentPlayer = server.getTurn() != null &&
                    player.getName().equals(server.getTurn().getPlayer().getName());
            String labelText = player.getName();
            JLabel label = new JLabel(player.getName());
            label.setOpaque(true);
            if (isCurrentPlayer) {
                label.setForeground(Color.WHITE);
                label.setBackground(player.getColor().getAwtColor());
            } else {
                label.setForeground(player.getColor().getAwtColor());
                label.setBackground(Color.WHITE);
            }
            playersPanel.add(label);
        }
    }
}
