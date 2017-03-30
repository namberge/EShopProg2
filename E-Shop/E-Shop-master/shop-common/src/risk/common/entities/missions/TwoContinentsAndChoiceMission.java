package risk.common.entities.missions;

import risk.common.entities.world.Continent;
import risk.common.entities.world.Province;

/**
 * mission: Own all provinces in two set continents and one of your choice
 * Europa, Australien und Choice
 * Europa, Südamerika und Choice
 * Europa, Afrika und Choice
 */
public class TwoContinentsAndChoiceMission extends Mission {

    private Continent continent1 = null;
    private Continent continent2 = null;

    public TwoContinentsAndChoiceMission(Continent continent1, Continent continent2){
        this.continent1 = continent1;
        this.continent2 = continent2;
        this.description = "Conquer continents " + continent1.getName() + " and " + continent2.getName() + " and a third of your choice!";
    }

    @Override
    public boolean isComplete() {
        for (Province p : continent1.getProvinces()) {
            if (!p.isOwnedBy(player)) {
                return false;
            }
        }
        for (Province p : continent2.getProvinces()) {
            if (!p.isOwnedBy(player)) {
                return false;
            }
        }
        return true;
    }
}
