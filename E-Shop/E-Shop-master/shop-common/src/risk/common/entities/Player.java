package risk.common.entities;

import risk.common.entities.missions.Mission;
import risk.common.entities.world.Province;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable {
	private String name;
	private Color color;
	private Player nextPlayer;
	private List<Province> provinces;
	private Mission mission;

	public Player(String name, Color color) {
		this.name = name;
		this.color = color;

		this.nextPlayer = null;
		this.provinces = new ArrayList<>();
	}
	
	public enum Color {
		PURPLE, BLUE, RED, GREEN, YELLOW, BROWN;

		/**
		 * Returns the corresponding Java AWT color.
		 * @return The AWT color.
         */
		public java.awt.Color getAwtColor() {
			switch (this) {
				case PURPLE:
					return new java.awt.Color(153, 51, 153);
				case BLUE:
					return new java.awt.Color(0, 51, 204);
				case RED:
					return new java.awt.Color(204, 0, 0);
				case GREEN:
					return new java.awt.Color(0, 153, 51);
				case YELLOW:
					return new java.awt.Color(203, 203, 0);
				case BROWN:
					return new java.awt.Color(102, 51, 0);
				default:
					return null;
			}
		}
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @return the nextPlayer
	 */
	public Player getNextPlayer() {
		return nextPlayer;
	}
	
	/**
	 * @param nextPlayer the nextPlayer to set
	 */
	public void setNextPlayer(Player nextPlayer) {
		this.nextPlayer = nextPlayer;
	}
	
	/**
	 * @return the provinces
	 */
	public List<Province> getProvinces() {
		return provinces;
	}

	/**
	 * @return the mission
     */
	public Mission getMission() {
		return mission;
	}

	/**
	 * @param mission the mission to set
     */
	public void setMission(Mission mission) {
		this.mission = mission;
	}
}
