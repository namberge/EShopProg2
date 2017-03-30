package risk.client.ui.gui.order.phase;

import layout.SpringUtilities;
import risk.common.network.Server;
import risk.common.entities.orders.DeployOrder;
import risk.common.entities.orders.Order;
import risk.common.entities.world.Province;

import javax.swing.*;

public class DeployPhasePanel extends PhasePanel {
    private JLabel targetLabel = new JLabel("Target: ", JLabel.TRAILING);
    private ProvinceTextField targetTextField = new ProvinceTextField();

    private JLabel unitsCountLabel = new JLabel("Units count:", JLabel.TRAILING);
    private JSpinner unitsCountSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 1, 1));

    public DeployPhasePanel(Server server) {
        super(server, "Deploy", false);

        add(targetLabel);
        add(targetTextField);

        add(unitsCountLabel);
        add(unitsCountSpinner);

        SpringUtilities.makeCompactGrid(this,
                1 + 2, 2,
                6, 6,
                6, 6);
    }

    @Override
    public void refresh() {
        if (server.getTurn() != null) {
            int unitsToDeploy = server.getTurn().getUnitsToDeploy();
            unitsCountSpinner.setModel(
                    new SpinnerNumberModel(unitsToDeploy, 1, unitsToDeploy, 1)); // default, min, max, step
        }
    }

    @Override
    public void selectProvince(Province province) {
        if (targetTextField.hasFocus()) {
            targetTextField.setProvince(province);
        }
    }

    @Override
    protected Order getOrder() {
        return new DeployOrder(
                server.getTurn(),
                targetTextField.getProvince(),
                ((Integer)unitsCountSpinner.getValue()).intValue());
    }
}
