package risk.server.domain;

import java.util.Arrays;

import risk.common.entities.world.Continent;
import risk.common.entities.world.Province;
import risk.common.entities.world.World;

public class WorldManager {
	private World world;
	
	public WorldManager() {
		createWorld();
	}

	/**
	 * @return the world
	 */
	public World getWorld() {
		return world;
	}

    /**
     * Resets the world, i.e. set owner/unitsCount of every province to null/0.
     */
    public void reset() {
        for (Province province : world.getProvinces()) {
            province.setOwner(null);
            province.setUnitsCount(0);
        }
    }

    /**
     * Creates the world.
     */
	private void createWorld(){
        world = new World(1.0, 0.66); // 3:2 aspect ratio

		//region Continents
		Continent northAmerica = addContinent("North America");
		Continent southAmerica = addContinent("South America");
		Continent europe = addContinent("Europe");
		Continent africa = addContinent("Africa");
		Continent asia = addContinent("Asia");
		Continent australia = addContinent("Australia");
		//endregion

        //region Provinces in North America
		Province alaska = addProvince(northAmerica, "Alaska", "ALK",
                0.07, 0.16,
                0.16, 0.16,
                0.16, 0.20,
                0.06, 0.20);
		Province northwestTerritory = addProvince(northAmerica, "Northwest Territory", "NWT",
                0.16, 0.16,
                0.32, 0.15,
                0.32, 0.20,
                0.16, 0.20);
		Province alberta = addProvince(northAmerica, "Alberta", "ALB",
                0.13, 0.20,
                0.24, 0.20,
                0.22, 0.25,
                0.13, 0.25);
		Province ontario = addProvince(northAmerica, "Ontario", "ONT",
                0.24, 0.20,
                0.34, 0.20,
                0.32, 0.25,
                0.22, 0.25);
		Province quebec = addProvince(northAmerica, "Quebec", "QBC",
                0.34, 0.20,
                0.41, 0.20,
                0.41, 0.25,
                0.32, 0.25);
		Province westUnitedStates = addProvince(northAmerica, "Western United States", "WUS",
                0.13, 0.25,
                0.24, 0.25,
                0.20, 0.33,
                0.12, 0.33);
		Province eastUnitedStates = addProvince(northAmerica, "Eastern United States", "EUS",
                0.24, 0.25,
                0.41, 0.25,
                0.33, 0.33,
                0.20, 0.33);
		Province centralAmerica = addProvince(northAmerica, "Central America", "CAM",
                0.12, 0.33,
                0.33, 0.33,
                0.30, 0.37,
                0.13, 0.37);
		Province greenland = addProvince(northAmerica, "Greenland", "GRL",
                0.41, 0.10,
                0.51, 0.10,
                0.51, 0.14,
                0.41, 0.14);
		//endregion

        //region Provinces in South America
		Province venezuela = addProvince(southAmerica, "Venezuela", "VZA",
				0.23, 0.41,
				0.32, 0.41,
				0.35, 0.46,
				0.20, 0.46);
		Province peru = addProvince(southAmerica, "Peru", "PER",
				0.20, 0.46,
				0.28, 0.46,
				0.28, 0.54,
				0.21, 0.54,
				0.18, 0.50);
		Province brazil = addProvince(southAmerica, "Brazil", "BRZ",
				0.28, 0.46,
				0.35, 0.46,
				0.38, 0.52,
				0.35, 0.54,
				0.28, 0.54);
		Province argentina = addProvince(southAmerica, "Argentina", "ARG",
				0.21, 0.54,
				0.35, 0.54,
				0.33, 0.58,
				0.23, 0.58);
		//endregion

        //region Provinces in Europe
		Province iceland = addProvince(europe, "Iceland", "ICL",
				0.46, 0.16,
				0.51, 0.16,
				0.51, 0.18,
				0.47, 0.18);
		Province greatBritain = addProvince(europe, "Great Britain", "GRB",
				0.50, 0.20,
				0.54, 0.20,
				0.54, 0.24,
				0.49, 0.23);
		Province westEurope = addProvince(europe, "Western Europe", "WEU",
				0.48, 0.25,
				0.55, 0.25,
				0.55, 0.30,
				0.49, 0.30);
		Province northEurope = addProvince(europe, "Northern Europe", "NEU",
				0.55, 0.25,
				0.56, 0.22,
				0.62, 0.23,
				0.61, 0.27,
				0.55, 0.27);
		Province southEurope = addProvince(europe, "Southern Europe", "SEU",
				0.55, 0.27,
				0.61, 0.27,
				0.61, 0.32,
				0.55, 0.32);
		Province ukraine = addProvince(europe, "Ukraine", "UKR",
				0.65, 0.13,
				0.72, 0.15,
				0.72, 0.22,
				0.71, 0.24,
				0.61, 0.27,
				0.62, 0.23,
				0.65, 0.18);
		Province scandinavia = addProvince(europe, "Scandinavia", "SCV",
				0.58, 0.13,
				0.65, 0.13,
				0.65, 0.18,
				0.57, 0.19);
		//endregion

        //region Provinces in Africa
		Province northAfrica = addProvince(africa, "North Africa", "NAF",
                0.44, 0.33,
                0.51, 0.34,
                0.53, 0.37,
                0.53, 0.40,
                0.50, 0.40,
                0.46, 0.43,
                0.40, 0.40);
		Province eastAfrica = addProvince(africa, "East Africa", "EAF",
				0.53, 0.37,
				0.60, 0.37,
				0.61, 0.46,
				0.55, 0.46,
				0.54, 0.40,
				0.53, 0.40);
		Province southAfrica = addProvince(africa, "South Africa", "SAF",
				0.46, 0.46,
				0.55, 0.46,
				0.61, 0.46,
				0.57, 0.53,
				0.49, 0.53);
		Province congo = addProvince(africa, "Congo", "CON",
				0.46, 0.43,
				0.50, 0.40,
				0.54, 0.40,
				0.55, 0.46,
				0.46, 0.46);
		Province egypt = addProvince(africa, "Egypt", "EGY",
				0.51, 0.34,
				0.58, 0.35,
				0.60, 0.37,
				0.53, 0.37);
		Province madagascar = addProvince(africa, "Madagascar", "MDG",
				0.63, 0.43,
				0.65, 0.43,
				0.65, 0.47,
				0.63, 0.47);
		//endregion

        //region Provinces in Asia
		Province middleEast = addProvince(asia, "Middle East", "MEA",
				0.61, 0.27,
				0.71, 0.24,
				0.70, 0.29,
				0.69, 0.37,
				0.63, 0.37,
				0.58, 0.32,
				0.61, 0.32);
		Province india = addProvince(asia, "India", "INA",
				0.70, 0.29,
				0.79, 0.29,
				0.79, 0.34,
				0.72, 0.41,
				0.69, 0.37);
		Province afghanistan = addProvince(asia, "Afghanistan", "AFG",
				0.72, 0.22,
				0.77, 0.22,
				0.76, 0.29,
				0.70, 0.29,
				0.71, 0.24);
		Province ural = addProvince(asia, "Ural", "URA",
				0.72, 0.15,
				0.77, 0.13,
				0.77, 0.22,
				0.72, 0.22);
		Province siberia = addProvince(asia, "Siberia", "SIB",
				0.77, 0.13,
				0.80, 0.13,
				0.80, 0.22,
				0.77, 0.22);
		Province china = addProvince(asia, "China", "CHI",
				0.77, 0.22,
				0.85, 0.22,
				0.85, 0.29,
				0.76, 0.29);
		Province siam = addProvince(asia, "Siam", "SIA",
				0.79, 0.29,
				0.85, 0.29,
				0.85, 0.34,
				0.79, 0.34);
		Province yakutsk = addProvince(asia, "Yakutsk", "YKT",
				0.80, 0.13,
				0.86, 0.13,
				0.86, 0.16,
				0.80, 0.16);
		Province irkutsk = addProvince(asia, "Irkutsk", "IKT",
				0.80, 0.16,
				0.86, 0.16,
				0.86, 0.19,
				0.80, 0.19);
		Province kamchatka = addProvince(asia, "Kamchatka", "KTK",
				0.86, 0.13,
				0.91, 0.11,
				0.90, 0.19,
				0.86, 0.19);
		Province mongolia = addProvince(asia, "Mongolia", "MNG",
				0.80, 0.19,
				0.88, 0.19,
				0.88, 0.22,
				0.80, 0.22);
		Province japan = addProvince(asia, "Japan", "JPN",
				0.90, 0.23,
				0.92, 0.24,
				0.91, 0.29,
				0.89, 0.28);
		//endregion

        //region Provinces in Australia
		Province indonesia = addProvince(australia, "Indonesia", "INO",
				0.81, 0.38,
				0.88, 0.38,
				0.88, 0.41,
				0.81, 0.41);
		Province newGuinea = addProvince(australia, "New Guinea", "NGU",
				0.87, 0.42,
				0.94, 0.42,
				0.94, 0.46,
				0.87, 0.46);
		Province westAustralia = addProvince(australia, "Western Australia", "WAU",
				0.78, 0.48,
				0.85, 0.48,
				0.85, 0.56,
				0.78, 0.56);
		Province eastAustralia = addProvince(australia, "Eastern Australia", "EAU",
				0.85, 0.48,
				0.94, 0.48,
				0.94, 0.56,
				0.85, 0.56);
		//endregion

		//region Neighbors in North America
		addNeighbors(alaska, northwestTerritory, alberta, kamchatka);
		addNeighbors(northwestTerritory, alaska, greenland, alberta, ontario);
		addNeighbors(alberta, alaska, northwestTerritory, ontario, westUnitedStates);
		addNeighbors(ontario, alberta, northwestTerritory, greenland, quebec, eastUnitedStates, westUnitedStates);
		addNeighbors(quebec, ontario, greenland, eastUnitedStates);
		addNeighbors(westUnitedStates, alberta, ontario, eastUnitedStates, centralAmerica);
		addNeighbors(eastUnitedStates, quebec, ontario, westUnitedStates, centralAmerica);
		addNeighbors(centralAmerica, westUnitedStates, eastUnitedStates, venezuela);
		addNeighbors(greenland, northwestTerritory, ontario, quebec, iceland);
		//endregion

        //region Neighbors in South America
		addNeighbors(venezuela, centralAmerica, peru, brazil);
		addNeighbors(peru, venezuela, brazil, argentina);
		addNeighbors(brazil, venezuela, peru, argentina, northAfrica);
		addNeighbors(argentina, peru, brazil);
		//endregion

        //region Neighbors in Europe
		addNeighbors(iceland, greenland, greatBritain, scandinavia);
		addNeighbors(greatBritain, iceland, scandinavia, northEurope, westEurope);
		addNeighbors(westEurope, greatBritain, northAfrica, northEurope, southEurope);
		addNeighbors(northEurope, greatBritain, westEurope, southEurope, scandinavia, ukraine);
		addNeighbors(southEurope, westEurope, northEurope, northAfrica, egypt, middleEast, ukraine);
		addNeighbors(ukraine, scandinavia, northEurope, southEurope, middleEast, afghanistan, ural);
		addNeighbors(scandinavia, iceland, greatBritain, northEurope, ukraine);
		//endregion

        //region Neighbors in Africa
		addNeighbors(northAfrica, westEurope, southEurope, brazil, egypt, eastAfrica, congo);
		addNeighbors(eastAfrica, egypt, middleEast, madagascar, congo, northAfrica);
		addNeighbors(southAfrica, congo, madagascar, eastAfrica);
		addNeighbors(congo, northAfrica, eastAfrica, southAfrica);
		addNeighbors(egypt, southEurope, middleEast, eastAfrica, northAfrica);
		addNeighbors(madagascar, eastAfrica, southAfrica);
		//endregion

        //region Neighbors in Asia
		addNeighbors(middleEast, southEurope, ukraine, afghanistan, india, eastAfrica, egypt);
		addNeighbors(india, middleEast, afghanistan, china, siam);
		addNeighbors(afghanistan, ural, middleEast, china, india, ukraine);
		addNeighbors(ural, ukraine, siberia, china, afghanistan);
		addNeighbors(siberia, ural, yakutsk, irkutsk, mongolia, china);
		addNeighbors(china, ural, siberia, mongolia, afghanistan, india, siam);
		addNeighbors(siam, china, india, indonesia);
		addNeighbors(yakutsk, siberia, irkutsk, kamchatka);
		addNeighbors(irkutsk, siberia, yakutsk, kamchatka, mongolia);
		addNeighbors(kamchatka, yakutsk, irkutsk, mongolia, japan, alaska);
		addNeighbors(mongolia, siberia, irkutsk, kamchatka, japan, china);
		addNeighbors(japan, mongolia, kamchatka);
		//endregion

        //region Neighbors in Australia
		addNeighbors(indonesia, siam, newGuinea, westAustralia);
		addNeighbors(newGuinea, indonesia, westAustralia, eastAustralia);
		addNeighbors(westAustralia, indonesia, newGuinea, eastAustralia);
		addNeighbors(eastAustralia, westAustralia, newGuinea);
		//endregion
	}

    /**
     * Creates a continent and adds it to the list of continents.
     *
     * @param name The name of the continent.
     * @return The continent.
     */
    private Continent addContinent(String name) {
        Continent continent = new Continent(name);
        world.getContinents().add(continent);
        return continent;
    }

    /**
     * Creates a province and adds it to the list of provinces.
     *
     * @param continent The continent of the province.
     * @param name The name of the province.
     * @param code The code of the province.
     * @return The province.
     */
    private Province addProvince(Continent continent, String name, String code, double... points) {
        Province province = new Province(name, code, continent, points);
        continent.getProvinces().add(province);
        world.getProvinces().add(province);
        return province;
    }

    /**
     * Adds neighbors to a province.
     *
     * @param province The province.
     * @param neighbors The neighbors to add.
     */
    private void addNeighbors(Province province, Province... neighbors) {
        province.getNeighbors().addAll(Arrays.asList(neighbors));
    }
}
