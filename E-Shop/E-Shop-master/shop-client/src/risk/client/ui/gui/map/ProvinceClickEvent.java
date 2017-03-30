package risk.client.ui.gui.map;

import risk.common.entities.world.Province;

public class ProvinceClickEvent {
    private Province province;

    public ProvinceClickEvent(Province province) {
        this.province = province;
    }

    /**
     * @return the province
     */
    public Province getProvince() {
        return province;
    }
}
