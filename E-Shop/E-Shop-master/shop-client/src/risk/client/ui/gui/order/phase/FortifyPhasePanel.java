package risk.client.ui.gui.order.phase;

import layout.SpringUtilities;
import risk.common.entities.orders.FortifyOrder;
import risk.common.network.Server;
import risk.common.entities.orders.Order;
import risk.common.entities.world.Province;

import javax.swing.*;

public class FortifyPhasePanel extends PhasePanel {
    private JLabel sourceLabel = new JLabel("Source:");
    private ProvinceTextField sourceTextField = new ProvinceTextField();

    private JLabel targetLabel = new JLabel("Target:");
    private ProvinceTextField targetTextField = new ProvinceTextField();

    private JLabel unitsCountLabel = new JLabel("Units count:");
    private JSpinner unitsCountSpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));

    public FortifyPhasePanel(Server server) {
        super(server, "Fortify", true);

        add(sourceLabel);
        add(sourceTextField);

        add(targetLabel);
        add(targetTextField);

        add(unitsCountLabel);
        add(unitsCountSpinner);

        SpringUtilities.makeCompactGrid(this,
                1 + 3, 2,
                6, 6,
                6, 6);
    }

    @Override
    public void refresh() {

    }

    @Override
    protected Order getOrder() {
        return new FortifyOrder(
                server.getTurn(),
                sourceTextField.getProvince(),
                targetTextField.getProvince(),
                ((Integer)unitsCountSpinner.getValue()).intValue());
    }

    @Override
    public void selectProvince(Province province) {
        if (sourceTextField.hasFocus()) {
            sourceTextField.setProvince(province);
        }
        if (targetTextField.hasFocus()) {
            targetTextField.setProvince(province);
        }
    }
}
