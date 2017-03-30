package risk.common.entities.world;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Continent implements Serializable {
	private String name;
	private Set<Province> provinces;
	
	public Continent(String name) {
		this.name = name;
		this.provinces = new HashSet<>();
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the provinces
	 */
	public Set<Province> getProvinces() {
		return provinces;
	}
}
