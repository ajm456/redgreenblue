package game.entities;

import java.awt.Color;

public enum Element {
	RED, GREEN, BLUE;
	
	public Element getNext() {
		return values()[(ordinal() + 1) % values().length];
	}
	
	public Color toColor() {
		switch(this) {
			case RED:
				return Color.RED;
			case BLUE:
				return Color.BLUE;
			case GREEN:
				return Color.GREEN;
			default:
				return null;
		}
	}
}
