package de.nedelosk.techtree.api;

public enum TechPointTypes {
	VERY_EASY, EASY, NORMAL, HARD, VERY_HARD;

	public TechPointTypes nextType() {
		if (ordinal() == values().length - 1) {
			return VERY_EASY;
		}
		return values()[ordinal() + 1];
	}
}
