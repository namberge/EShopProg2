package risk.client.ui.gui.order.phase;

import risk.common.entities.world.Province;

import javax.swing.*;

public class ProvinceTextField extends JTextField {
    private Province province;

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
        this.setText(province.getCode());
    }
}
