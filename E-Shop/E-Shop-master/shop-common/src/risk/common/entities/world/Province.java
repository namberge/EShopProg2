package risk.common.entities.world;

import risk.common.entities.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Province implements Serializable {
	private String name;
	private String code;
	private Continent continent;
	private List<Point> points;
	private Set<Province> neighbors = new HashSet<>();
	private Player owner;
	private int unitsCount;
	
	public Province(String name, String code, Continent continent, double... points) {
		this.name = name;
		this.code = code;
		this.continent = continent;
		this.points = new ArrayList<>(points.length / 2);
		for (int i = 0; i < points.length; i += 2) {
			this.points.add(new Point(points[i], points[i + 1]));
		}
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * @return the continent
	 */
	public Continent getContinent() {
		return continent;
	}

	/**
	 * @return the points
     */
	public List<Point> getPoints() {
		return points;
	}

	/**
	 * @return the neighbors
	 */
	public Set<Province> getNeighbors() {
		return neighbors;
	}

	/**
	 * @return the owner
	 */
	public Player getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(Player owner) {
		// if there already is an owner
		if (this.owner != null) {
			// remove this province from its list
			this.owner.getProvinces().remove(this);
		}

		// set the new owner
		this.owner = owner;

		// if there is a new owner
		if (owner != null) {
			// add this province to its list
			owner.getProvinces().add(this);
		}
	}

	/**
	 * @return the unitsCount
	 */
	public int getUnitsCount() {
		return unitsCount;
	}

	/**
	 * @param unitsCount the unitsCount to set
	 */
	public void setUnitsCount(int unitsCount) {
		this.unitsCount = unitsCount;
	}

	/**
	 * Tells whether the province is a neighbor of the given province.
	 *
	 * @param province The second province.
	 * @return True if the provinces are neighbors, false otherwise.
	 */
	public boolean isNeighborOf(Province province) {
		return neighbors.contains(province);
	}

	/**
	 * Tells whether the province is owned by the given player.
	 *
	 * @param player The player.
	 * @return True if this province is owned by the given, false otherwise.
	 */
    public boolean isOwnedBy(Player player) {
        return this.owner == player;
    }
}
