package risk.common.entities.missions;

import risk.common.entities.world.Province;

/**
 * mission: Own 18 provinces with at least two units in each one
 */
public class EighteenProvincesMission extends Mission {
    public EighteenProvincesMission() {
        this.description = "Conquer at least 18 provinces with at least two units stationed in each!";
    }

    @Override
    public boolean isComplete() {
        if (player.getProvinces().size() < 18) {
            return false;
        }
        for (Province p : player.getProvinces()) {
            //to do
        }
        return false;
    }
}