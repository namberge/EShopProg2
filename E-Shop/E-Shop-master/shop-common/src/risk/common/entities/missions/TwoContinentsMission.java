package risk.common.entities.missions;

import risk.common.entities.world.Continent;
import risk.common.entities.world.Province;

/**
 * mission: Own all provinces in two set continents:
 * Asien und Afrika
 * Asien und Südamerika
 * Nordamerika und Australien
 * Nordamerika und Afrika
 */
public class TwoContinentsMission extends Mission {

	private Continent continent1 = null;
	private Continent continent2 = null;
	
	public TwoContinentsMission(Continent continent1, Continent continent2) {
		this.continent1 = continent1;
		this.continent2 = continent2;
		this.description =  "Conquer continents " + continent1.getName() + " and " + continent2.getName() + "!";
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
