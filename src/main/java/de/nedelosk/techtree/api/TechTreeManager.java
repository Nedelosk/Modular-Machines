package de.nedelosk.techtree.api;

import net.minecraft.entity.player.EntityPlayer;

public class TechTreeManager {

	public static void completeEntry(EntityPlayer player, String key) {
		if (player.getExtendedProperties("TECHTREE") != null) {
			((TechTreePlayerData) player.getExtendedProperties("TECHTREE")).techEntrys.add(key);
		} else {
			player.registerExtendedProperties("TECHTREE", new TechTreePlayerData(player, key));
		}
	}

	public static boolean isEntryComplete(EntityPlayer player, String key) {
		if (player.getExtendedProperties("TECHTREE") != null) {
			return ((TechTreePlayerData) player.getExtendedProperties("TECHTREE")).techEntrys.contains(key);
		}
		return false;
	}

	public static boolean isEntryComplete(EntityPlayer player, String[] keys) {
		if (player.getExtendedProperties("TECHTREE") != null) {
			for ( String key : keys ) {
				if (!((TechTreePlayerData) player.getExtendedProperties("TECHTREE")).techEntrys.contains(key)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public static void addTechPoints(EntityPlayer player, int points, TechPointTypes type) {
		if (player.getExtendedProperties("TECHTREE") != null) {
			((TechTreePlayerData) player.getExtendedProperties("TECHTREE")).techPoints[type.ordinal()] += points;
		} else {
			player.registerExtendedProperties("TECHTREE", new TechTreePlayerData(player, points, type));
		}
	}

	public static void setTechPoints(EntityPlayer player, int points, TechPointTypes type) {
		if (player.getExtendedProperties("TECHTREE") != null) {
			((TechTreePlayerData) player.getExtendedProperties("TECHTREE")).techPoints[type.ordinal()] = points;
		} else {
			player.registerExtendedProperties("TECHTREE", new TechTreePlayerData(player, points, type));
		}
	}

	public static void setTechPoints(EntityPlayer player, int[] points) {
		if (player.getExtendedProperties("TECHTREE") != null) {
			((TechTreePlayerData) player.getExtendedProperties("TECHTREE")).techPoints = points;
		} else {
			player.registerExtendedProperties("TECHTREE", new TechTreePlayerData(player));
			((TechTreePlayerData) player.getExtendedProperties("TECHTREE")).techPoints = points;
		}
	}

	public static int[] getTechPoints(EntityPlayer player) {
		if (player.getExtendedProperties("TECHTREE") != null) {
			return ((TechTreePlayerData) player.getExtendedProperties("TECHTREE")).techPoints;
		} else {
			player.registerExtendedProperties("TECHTREE", new TechTreePlayerData(player));
			return null;
		}
	}
}
