package risk.client.ui.gui.order;

import risk.common.network.Server;
import risk.common.entities.Turn;
import risk.common.entities.world.Province;
import risk.client.ui.gui.order.phase.*;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class OrderPanel extends JPanel {
    private Server server;
    private Map<Turn.Phase, PhasePanel> phasesPanels = new HashMap<>(Turn.Phase.values().length);

    public OrderPanel(Server server) {
        this.server = server;

        phasesPanels.put(Turn.Phase.DEPLOY, (PhasePanel)add(new DeployPhasePanel(server)));
        phasesPanels.put(Turn.Phase.ATTACK, (PhasePanel)add(new AttackPhasePanel(server)));
        phasesPanels.put(Turn.Phase.FORTIFY, (PhasePanel)add(new FortifyPhasePanel(server)));

        refresh();
    }

    /**
     * Refreshes the panel.
     */
    public void refresh() {
        // reset: hide all panel
        for (PhasePanel p : phasesPanels.values()) {
            p.setVisible(false);
        }

        // update: show current phase panel
        PhasePanel currentPanel = getCurrentPhasePanel();
        if (currentPanel != null) {
            currentPanel.setVisible(true);
            currentPanel.refresh();
        }
    }

    public void addOrderIssueListener(OrderIssueListener l) {
        for (PhasePanel p : phasesPanels.values()) {
            p.addOrderIssueListener(l);
        }
    }

    public void selectProvince(Province province) {
        PhasePanel currentPanel = getCurrentPhasePanel();
        if (currentPanel != null) {
            currentPanel.selectProvince(province);
        }
    }

    private PhasePanel getCurrentPhasePanel() {
        Turn turn = server.getTurn();
        if (turn == null) {
            return null;
        }
        Turn.Phase phase = turn.getPhase();
        if (phase == null) {
            return null;
        }
        return phasesPanels.get(phase);
    }
}
