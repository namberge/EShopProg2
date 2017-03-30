package risk.client.ui.gui.mission;

import risk.common.network.Server;
import risk.common.entities.missions.Mission;

import javax.swing.*;
import java.awt.*;

public class MissionPanel extends JPanel {
    private Server server;
    private JTextArea descriptionTextArea = new JTextArea();
    private JLabel statusLabel = new JLabel();

    public MissionPanel(Server server) {
        super(new GridLayout(2, 1));

        this.server = server;

        setPreferredSize(new Dimension(250, 150)); // TODO
        setMinimumSize(new Dimension(50, 100)); // TODO

        descriptionTextArea.setWrapStyleWord(true);
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setEditable(false);

        add(descriptionTextArea);
        add(statusLabel);

        refresh();
    }

    /**
     * Refreshes the panel.
     */
    public void refresh() {
        // reset
        descriptionTextArea.setText("");
        statusLabel.setText("");

        // update
        if (server.getTurn() != null) {
            Mission mission = server.getTurn().getPlayer().getMission();

            descriptionTextArea.setText(mission.getDescription());
            statusLabel.setText(mission.isComplete() ? "COMPLETE" : "NOT COMPLETE"); // TODO: "COMPLETE" : ""
        }
    }
}
