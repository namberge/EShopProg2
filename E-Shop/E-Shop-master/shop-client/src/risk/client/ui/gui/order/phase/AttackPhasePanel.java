package risk.client.ui.gui.order.phase;

import layout.SpringUtilities;
import risk.common.entities.orders.AttackOrder;
import risk.common.network.Server;
import risk.common.entities.orders.Order;
import risk.common.entities.world.Province;

import javax.swing.*;

public class AttackPhasePanel extends PhasePanel {
    private JLabel attackerLabel = new JLabel("Attacker: ", JLabel.TRAILING);
    private ProvinceTextField attackerTextField = new ProvinceTextField();

    private JLabel attackerUnitsCountLabel = new JLabel("Units count:", JLabel.TRAILING);
    private JSpinner attackerUnitsCountSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 3, 1));

    private JLabel defenderLabel = new JLabel("Defender: ", JLabel.TRAILING);
    private ProvinceTextField defenderTextField = new ProvinceTextField();

    private JLabel defenderUnitsCountLabel = new JLabel("Units count:", JLabel.TRAILING);
    private JSpinner defenderUnitsCountSpinner = new JSpinner(new SpinnerNumberModel(2, 1, 2, 1));

    public AttackPhasePanel(Server server) {
        super(server, "Attack", true);

        add(attackerLabel);
        add(attackerTextField);

        add(attackerUnitsCountLabel);
        add(attackerUnitsCountSpinner);

        add(defenderLabel);
        add(defenderTextField);

        add(defenderUnitsCountLabel);
        add(defenderUnitsCountSpinner);

        SpringUtilities.makeCompactGrid(this,
                1 + 4, 2,
                6, 6,
                6, 6);
    }

    @Override
    public void refresh() {

    }

    @Override
    protected Order getOrder() {
        return new AttackOrder(
                server.getTurn(),
                attackerTextField.getProvince(),
                ((Integer)attackerUnitsCountSpinner.getValue()).intValue(),
                defenderTextField.getProvince(),
                ((Integer)defenderUnitsCountSpinner.getValue()).intValue());
    }

    @Override
    public void selectProvince(Province province) {
        if (attackerTextField.hasFocus()) {
            attackerTextField.setProvince(province);
        }
        if (defenderTextField.hasFocus()) {
            defenderTextField.setProvince(province);
        }
    }
}
