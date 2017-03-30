package risk.common.entities.world;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class World implements Serializable {
    private double width;
    private double height;
    private Set<Continent> continents = new HashSet<>(6);
    private Set<Province> provinces = new HashSet<>(42);

    public World(double width, double height) {
        this.width = width;
        this.height = height;
    }

    /**
     * @return the width
     */
    public double getWidth() {
        return width;
    }

    /**
     * @return the height
     */
    public double getHeight() {
        return height;
    }

    /**
     * @return the continents
     */
    public Set<Continent> getContinents() {
        return continents;
    }

    /**
     * @return the provinces
     */
    public Set<Province> getProvinces() {
        return provinces;
    }

    /**
     * @return the first continent found, null if none found
     */
    public Continent getContinentByName(String name) {
        for (Continent continent : continents) {
            if (continent.getName().equals(name)) {
                return continent;
            }
        }
        return null;
    }

    /**
     * @return the first province found, null if none found
     */
    public Province getProvinceByCode(String code) {
        for (Province province : provinces) {
            if (province.getCode().equals(code)) {
                return province;
            }
        }
        return null;
    }
}
