package risk.client.ui.gui.order.phase;

import risk.common.network.Server;
import risk.common.entities.orders.Order;
import risk.common.entities.world.Province;
import risk.client.ui.gui.order.OrderIssueEvent;
import risk.client.ui.gui.order.OrderIssueListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public abstract class PhasePanel extends JPanel {
    protected Server server;
    private List<OrderIssueListener> orderIssueListeners = new ArrayList<>();
    private JButton nextButton = new JButton("Next");
    private JButton issueButton = new JButton();

    protected PhasePanel(Server server, String issueButtonText, boolean nextButtonEnabled) {
        //super(new GridLayout(3, 2)); // TODO: use SpringLayout
        super(new SpringLayout());

        this.server = server;

        setPreferredSize(new Dimension(250, 150)); // TODO
        setMinimumSize(new Dimension(50, 100)); // TODO

        nextButton.setEnabled(nextButtonEnabled);
        nextButton.addActionListener(new NextButtonListener());
        add(nextButton);

        issueButton.setText(issueButtonText);
        issueButton.addActionListener(new IssueButtonListener());
        add(issueButton);

        refresh();
    }

    /**
     * Refreshes the panel.
     */
    public abstract void refresh();

    //region order input

    public abstract void selectProvince(Province province);

    //endregion

    //region order issuing

    public void addOrderIssueListener(OrderIssueListener l) {
        orderIssueListeners.add(l);
    }

    protected abstract Order getOrder();

    private class NextButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (OrderIssueListener l : orderIssueListeners) {
                l.orderIssued(new OrderIssueEvent(null));
            }
        }
    }

    private class IssueButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (OrderIssueListener l : orderIssueListeners) {
                l.orderIssued(new OrderIssueEvent(getOrder()));
            }
        }
    }

    //endregion
}
